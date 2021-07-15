package com.ericromero.tickertreat.util

import java.text.NumberFormat
import java.util.Currency

/**
 * Basic utility methods for ticker data
 *
 * @author eromero
 */
object TickerUtils {

    /**
     * Global quote currency used within the app
     */
    const val CURRENCY = "USD"

    /**
     * Formats a number with a decimal for the given currency
     * ex: 32820.88 -> $32,820.88
     *
     * @param amount Double representing decimal number
     *
     * @return Formatted string representing the amount in the given currency
     */
    fun formattedCurrency(amount: Double): String {
        val format: NumberFormat = NumberFormat.getCurrencyInstance()
        format.maximumFractionDigits = 4
        format.currency = Currency.getInstance(CURRENCY)

        return format.format(amount)
    }

    /**
     * Determines the base currency for the given trading pair based on quote currency
     *
     * @param tradingPair String representing the trading pair. ex: btcusd
     *
     * @return Base currency based on the given trading pair
     */
    fun getBaseCurrency(tradingPair: String): String {
        return tradingPair.substringBeforeLast(CURRENCY.lowercase()).uppercase()
    }
}