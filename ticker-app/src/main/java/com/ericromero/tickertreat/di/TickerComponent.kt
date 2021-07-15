package com.ericromero.tickertreat.di

import com.ericromero.tickertreat.data.TickerDataRepository
import com.ericromero.tickertreat.ui.TickerListViewModel
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [TickerModule::class])
interface TickerComponent {

    fun repo(): TickerDataRepository

    fun inject(injected: TickerListViewModel)
}