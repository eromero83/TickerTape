package com.ericromero.tickertreat

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.ericromero.tickertreat.di.TickerInjector
import com.ericromero.tickertreat.ui.TickerListFragment

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.ticker_activity)

        // Init dependencies
        TickerInjector(this)

        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.container, TickerListFragment.newInstance())
                .commitNow()
        }
    }
}