package com.ericromero.tickertreat.ui

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.ericromero.tickertreat.data.TickerDataRepository
import com.ericromero.tickertreat.data.model.TickerPrice
import com.ericromero.tickertreat.di.TickerInjector
import com.ericromero.tickertreat.util.TickerUtils
import io.reactivex.Observable
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

/**
 * ViewModel that manages the list of prices
 *
 * @author eromero
 */
class TickerListViewModel : ViewModel() {

    @Inject
    lateinit var dataSource: TickerDataRepository

    /**
     * Internal list of all ticker prices
     */
    lateinit var allPrices: List<TickerPrice>

    /**
     * LiveData list of displayed ticker prices
     */
    val prices = MutableLiveData<List<TickerPrice>>()

    private val disposables = CompositeDisposable()

    companion object {
        const val TAG = "TickerListViewModel"
    }

    init {
        TickerInjector.component.inject(this)
    }

    fun loadSymbols() {
        val disposable = dataSource.fetchTickerSymbols().subscribeOn(Schedulers.io()).subscribe({
            allPrices = it.map {
                TickerPrice(it.symbol, TickerUtils.getBaseCurrency(it.symbol), "- - -", "")
            }

            // Update displayed list of prices
            prices.postValue(allPrices)

            // Now load the prices in-place
            loadPrices()
        }, {
            Log.e(TAG, "Error fetching symbols data", it)
        })
        disposables.add(disposable)
    }

    /**
     * Load prices from data source
     */
    fun loadPrices() {
        val disposable = Observable.fromIterable(allPrices).flatMapSingle {
            dataSource.fetchTickerPrice(it.symbol)
        }.map { optional ->
            if (optional.isPresent) {
                // Update the internal list with new value for price
                allPrices.first {
                    it.symbol == optional.get().symbol
                }.price = optional.get().price
            }
        }.toList().subscribeOn(Schedulers.io()).subscribe({
            prices.postValue(allPrices)
        }, {
            Log.e(TAG, "Error fetching ticker prices", it)
        })

        disposables.add(disposable)
    }

    override fun onCleared() {
        super.onCleared()
        disposables.dispose()
    }
}