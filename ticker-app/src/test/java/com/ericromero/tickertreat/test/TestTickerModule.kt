package com.ericromero.tickertreat.test

import android.content.Context
import com.ericromero.tickertreat.data.TickerDataRepository
import com.ericromero.tickertreat.database.TickerDataStore
import com.ericromero.tickertreat.di.TickerModule
import com.ericromero.tickertreat.network.TickerNetwork
import dagger.Module
import org.mockito.Mockito

@Module
class TestTickerModule(context: Context) : TickerModule(context) {

    override fun providesTickerNetwork(): TickerNetwork {
        return Mockito.mock(TickerNetwork::class.java)
    }

    override fun providesTickerDataStore(): TickerDataStore {
        return Mockito.mock(TickerDataStore::class.java)
    }

    override fun providesTickerDataRepository(
        network: TickerNetwork,
        dataStore: TickerDataStore
    ): TickerDataRepository {
        return Mockito.mock(TickerDataRepository::class.java)
    }
}