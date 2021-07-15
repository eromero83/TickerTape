package com.ericromero.tickertreat.data.model

import io.requery.Entity
import io.requery.Generated
import io.requery.Key
import io.requery.Persistable

/**
 * Data model representing a single trading pair
 */
@Entity
interface ITickerSymbol : Persistable {
    @get:[Key Generated]
    val rid: Int

    var symbol: String
}
