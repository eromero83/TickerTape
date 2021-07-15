package com.ericromero.tickertreat.data

import com.ericromero.tickertreat.data.model.TickerPrice
import com.ericromero.tickertreat.data.model.TickerSymbol
import com.ericromero.tickertreat.database.TickerDataStore
import com.ericromero.tickertreat.network.TickerNetwork
import io.reactivex.Single
import java.util.Optional
import javax.inject.Inject

/**
 * Implementation for ticker data repository that fetches symbols from
 * the cache before checking network
 *
 * @author eromero
 */
class TickerDataRepositoryImpl @Inject constructor(
    private val network: TickerNetwork,
    private val database: TickerDataStore
) :
    TickerDataRepository {

    override fun fetchTickerSymbols(): Single<List<TickerSymbol>> {
        return fetchTickerSymbolsFromDB().flatMap {
            if (it.isEmpty()) {
                // Cache is empty, fetch symbols from network
                fetchTickerSymbolsFromNetwork()
            } else {
                Single.just(it)
            }
        }
    }

    override fun fetchTickerPrice(symbol: String): Single<Optional<TickerPrice>> {
        return network.fetchPrice(symbol)
    }

    private fun fetchTickerSymbolsFromDB(): Single<List<TickerSymbol>> {
        return Single.create {
            it.onSuccess(database.fetchSymbols())
        }
    }

    private fun fetchTickerSymbolsFromNetwork(): Single<List<TickerSymbol>> {
        return network.fetchSymbols().map {
            // Save symbols to cache for offline
            database.saveSymbols(it)
            it
        }
    }
}