package com.ericromero.tickertreat.data

import com.ericromero.tickertreat.data.model.TickerPrice
import com.ericromero.tickertreat.data.model.TickerSymbol
import io.reactivex.Single
import java.util.Optional

/**
 * Repository for ticker data
 */
interface TickerDataRepository {

    /**
     * Returns just the ticker symbols
     *
     * @return Single that emits a list of symbols
     */
    fun fetchTickerSymbols(): Single<List<TickerSymbol>>

    /**
     * Returns ticker price for the given symbol
     *
     * @return Single that emits ticker price
     */
    fun fetchTickerPrice(symbol: String): Single<Optional<TickerPrice>>
}