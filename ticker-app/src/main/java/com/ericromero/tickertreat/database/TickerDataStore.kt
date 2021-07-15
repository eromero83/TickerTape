package com.ericromero.tickertreat.database

import com.ericromero.tickertreat.data.model.TickerSymbol

/**
 * Data storage for ticker data
 */
interface TickerDataStore {

    /**
     * Fetches symbols from data storage
     *
     * @return List of symbols
     */
    fun fetchSymbols(): List<TickerSymbol>

    /**
     * Saves symbol to data storage
     *
     * @param symbols List of symbols to save in data storage
     */
    fun saveSymbols(symbols: List<TickerSymbol>)
}