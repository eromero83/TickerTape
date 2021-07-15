package com.ericromero.tickertreat.database

import android.content.Context
import com.ericromero.tickertreat.BuildConfig
import com.ericromero.tickertreat.data.model.Models
import com.ericromero.tickertreat.data.model.TickerSymbol
import io.requery.Persistable
import io.requery.android.sqlite.DatabaseSource
import io.requery.reactivex.ReactiveEntityStore
import io.requery.reactivex.ReactiveSupport
import io.requery.sql.Configuration
import io.requery.sql.EntityDataStore
import io.requery.sql.TableCreationMode

/**
 * Requery based data storage implementation
 *
 * @author eromero
 */
class TickerDataStoreImpl(context: Context) :
    TickerDataStore {

    companion object {
        const val DATABASE_VERSION = 1
    }

    private val dataStore: ReactiveEntityStore<Persistable>

    init {
        // Configure reactive data store
        val source = DatabaseSource(context, Models.DEFAULT, DATABASE_VERSION)
        if (BuildConfig.DEBUG) {
            source.setTableCreationMode(TableCreationMode.DROP_CREATE)
        }
        val configuration: Configuration = source.configuration
        dataStore = ReactiveSupport.toReactiveStore(EntityDataStore(configuration))
    }

    override fun fetchSymbols(): List<TickerSymbol> {
        return dataStore.select(TickerSymbol::class.java).get().toList()
    }

    override fun saveSymbols(symbols: List<TickerSymbol>) {
        dataStore.upsert(symbols).subscribe()
    }
}