package com.ericromero.tickertreat.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ericromero.tickertreat.R
import com.ericromero.tickertreat.databinding.SymbolListLayoutBinding

/**
 * Displays list of ticker prices
 *
 * @author eromero
 */
class TickerListFragment : Fragment() {

    companion object {
        fun newInstance() = TickerListFragment()
    }

    private lateinit var viewModel: TickerListViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        viewModel = ViewModelProvider(this).get(TickerListViewModel::class.java)

        // Get the data binding
        val binding = DataBindingUtil.inflate<SymbolListLayoutBinding>(
            inflater,
            R.layout.symbol_list_layout,
            container,
            false
        )
        binding.lifecycleOwner = this

        // Setup list adapter
        binding.symbolList.layoutManager =
            LinearLayoutManager(requireContext(), RecyclerView.VERTICAL, false)
        binding.symbolList.adapter = TickerListAdapter()

        // Observe changes in list of prices to update adapter
        viewModel.prices.observe(viewLifecycleOwner) {
            val adapter = binding.symbolList.adapter as TickerListAdapter
            adapter.updateList(it)
            binding.swipeRefreshLayout.isRefreshing = false
        }

        // Add swipe refresh listener to refresh prices
        binding.swipeRefreshLayout.setOnRefreshListener {
            viewModel.loadPrices()
        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        // Begin loading data
        viewModel.loadSymbols()
    }
}