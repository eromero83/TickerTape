package com.ericromero.tickertreat.test

import androidx.test.platform.app.InstrumentationRegistry
import com.ericromero.tickertreat.di.DaggerTickerComponent
import com.ericromero.tickertreat.di.TickerInjector
import org.junit.rules.ExternalResource

class MockServicesRule : ExternalResource() {

    override fun before() {
        super.before()
        TickerInjector.component = DaggerTickerComponent.builder().tickerModule(
            TestTickerModule(InstrumentationRegistry.getInstrumentation().context)
        ).build()
    }
}