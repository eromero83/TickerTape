package com.ericromero.tickertreat.network

import com.ericromero.tickertreat.data.model.TickerPrice
import com.ericromero.tickertreat.data.model.TickerSymbol
import io.reactivex.Single
import java.util.Optional

/**
 * Manages network operations for ticker data
 */
interface TickerNetwork {

    /**
     * Fetches a list of symbols (trading pairs) from network
     *
     * @return Single that emits a list of ticker symbols
     */
    fun fetchSymbols(): Single<List<TickerSymbol>>

    /**
     * Fetches the price of the given ticker symbol from network
     *
     * @param symbol Symbol of price to fetch
     *
     * @return Single that emits optional for ticker price. Optional.empty() is returned
     *      if price cannot be determined
     */
    fun fetchPrice(symbol: String): Single<Optional<TickerPrice>>
}