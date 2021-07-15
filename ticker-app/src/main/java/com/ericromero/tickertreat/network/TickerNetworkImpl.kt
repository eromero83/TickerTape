package com.ericromero.tickertreat.network

import com.ericromero.tickertreat.data.model.TickerPrice
import com.ericromero.tickertreat.data.model.TickerSymbol
import com.ericromero.tickertreat.util.TickerUtils
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import io.reactivex.Single
import retrofit2.Retrofit
import java.net.HttpURLConnection
import java.util.Optional
import javax.inject.Inject

class TickerNetworkImpl @Inject constructor() : TickerNetwork {

    companion object {
        const val BASE_URL = "https://api.gemini.com/"

        // For manually parsing json response
        const val LAST = "last"
        const val VOLUME = "volume"
        const val QUOTE_CURRENCY = "quote_currency"
    }

    private val api: GeminiService
    private val mapper = jacksonObjectMapper()

    init {
        val retrofit = Retrofit.Builder().baseUrl(BASE_URL).client(UnsafeOkHttpClient.get()).build()
        api = retrofit.create(GeminiService::class.java)
    }

    override fun fetchSymbols(): Single<List<TickerSymbol>> {
        return Single.create { emitter ->
            // Execute network call
            val response = api.getSymbols().execute()
            if (response.code() != HttpURLConnection.HTTP_OK) {
                emitter.onError(NetworkError(response.errorBody()?.string()))
            }

            // Success!
            val body = response.body()?.string()
            body?.let {
                // Parse list of symbols
                val symbols: List<String> = mapper.readValue(body)

                emitter.onSuccess(symbols.filter {
                    // Make additional call to filter only TickerUtils.CURRENCY pairs
                    val details = api.getSymbolDetails(it).execute()
                    when {
                        details.code() != HttpURLConnection.HTTP_OK -> false
                        details.body() == null -> false
                        else -> {
                            val responseNode = mapper.readTree(details.body()?.string())
                            responseNode.get(QUOTE_CURRENCY).asText() == TickerUtils.CURRENCY
                        }
                    }
                }.map { symbol ->
                    TickerSymbol().apply {
                        this.symbol = symbol
                    }
                })
            } ?: emitter.onError(NetworkError("Could not parse network response for symbols"))
        }
    }

    override fun fetchPrice(symbol: String): Single<Optional<TickerPrice>> {
        return Single.create { emitter ->
            // Execute network call
            val response = api.getTicker(symbol).execute()
            if (response.code() != HttpURLConnection.HTTP_OK) {
                emitter.onError(NetworkError(response.errorBody()?.string()))
            }

            // Success!
            val body = response.body()?.string()
            body?.let {
                // Get "last" and nested "volume" values from response
                val responseNode = mapper.readTree(it)
                val price = responseNode.get(LAST)
                val volume = responseNode.get(VOLUME).get(TickerUtils.CURRENCY)

                // If price or volume not found, return Optional.empty()
                val tickerPrice = if (price != null && volume != null) {
                    val formattedPrice = TickerUtils.formattedCurrency(price.asDouble())
                    val displaySymbol = TickerUtils.getBaseCurrency(symbol)
                    Optional.of(
                        TickerPrice(
                            symbol,
                            displaySymbol,
                            formattedPrice,
                            volume.asText()
                        )
                    )
                } else {
                    Optional.empty()
                }
                emitter.onSuccess(tickerPrice)
            }
                ?: emitter.onError(NetworkError("Could not parse network response for price of $symbol"))

        }
    }
}