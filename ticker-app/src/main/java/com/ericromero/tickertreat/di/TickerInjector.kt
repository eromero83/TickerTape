package com.ericromero.tickertreat.di

import android.content.Context

/**
 * Primary registry for injected dependencies
 */
class TickerInjector(val context: Context) {
    companion object {
        lateinit var component: TickerComponent
    }

    init {
        component = DaggerTickerComponent.builder().tickerModule(TickerModule(context)).build()
    }
}