package com.ericromero.tickertreat.data.model

/**
 * Data model representing the ticker details of a single symbol
 */
data class TickerPrice(
    val symbol: String,
    val displaySymbol: String,
    var price: String,
    val volume: String
)