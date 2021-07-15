package com.ericromero.tickertreat.ui

import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.espresso.matcher.ViewMatchers.withEffectiveVisibility
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.ericromero.tickertreat.data.model.TickerPrice
import com.ericromero.tickertreat.data.model.TickerSymbol
import com.ericromero.tickertreat.di.TickerInjector
import com.ericromero.tickertreat.test.MockServicesRule
import io.reactivex.Single
import io.reactivex.plugins.RxJavaPlugins
import io.reactivex.schedulers.Schedulers
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito
import java.util.Optional

@RunWith(AndroidJUnit4::class)
class TickerListFragmentTest {

    val mockServicesRule = MockServicesRule()
        @Rule get

    @Before
    fun setup() {
        // Set io scheduler to run synchronously
        RxJavaPlugins.setIoSchedulerHandler { _ -> Schedulers.trampoline() }

        // Mock data store
        val mockData = TickerInjector.component.repo()
        Mockito.`when`(mockData.fetchTickerSymbols()).thenReturn(
            Single.just(listOf(TickerSymbol().apply {
                this.symbol = "btcusd"
            }, TickerSymbol().apply {
                this.symbol = "ethusd"
            }, TickerSymbol().apply {
                this.symbol = "xrpusd"
            }))
        )
        Mockito.`when`(mockData.fetchTickerPrice("btcusd")).thenReturn(
            Single.just(Optional.of(TickerPrice("btcusd", "BTC", "$100,000", "")))
        )
        Mockito.`when`(mockData.fetchTickerPrice("ethusd")).thenReturn(
            Single.just(Optional.of(TickerPrice("ethusd", "ETH", "$50,000", "")))
        )
        Mockito.`when`(mockData.fetchTickerPrice("xrpusd")).thenReturn(
            Single.just(Optional.of(TickerPrice("xrpusd", "XRP", "$589", "")))
        )
    }

    @Test
    fun `List of ticker prices are shown normally`() {
        launchFragmentInContainer<TickerListFragment>()

        // Verifiy all 3 symbols appear in the list
        onView(withText("BTC")).check(matches(withEffectiveVisibility(ViewMatchers.Visibility.VISIBLE)))
        onView(withText("ETH")).check(matches(withEffectiveVisibility(ViewMatchers.Visibility.VISIBLE)))
        onView(withText("XRP")).check(matches(withEffectiveVisibility(ViewMatchers.Visibility.VISIBLE)))
    }
}