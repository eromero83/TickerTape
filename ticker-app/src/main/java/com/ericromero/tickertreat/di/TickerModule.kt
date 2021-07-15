package com.ericromero.tickertreat.di

import android.content.Context
import com.ericromero.tickertreat.data.TickerDataRepository
import com.ericromero.tickertreat.data.TickerDataRepositoryImpl
import com.ericromero.tickertreat.database.TickerDataStore
import com.ericromero.tickertreat.database.TickerDataStoreImpl
import com.ericromero.tickertreat.network.TickerNetwork
import com.ericromero.tickertreat.network.TickerNetworkImpl
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
open class TickerModule(private val context: Context) {

    @Provides
    @Singleton
    open fun providesTickerNetwork(): TickerNetwork {
        return TickerNetworkImpl()
    }

    @Provides
    @Singleton
    open fun providesTickerDataStore(): TickerDataStore {
        return TickerDataStoreImpl(context)
    }

    @Provides
    @Singleton
    open fun providesTickerDataRepository(
        network: TickerNetwork,
        dataStore: TickerDataStore
    ): TickerDataRepository {
        return TickerDataRepositoryImpl(network, dataStore)
    }
}