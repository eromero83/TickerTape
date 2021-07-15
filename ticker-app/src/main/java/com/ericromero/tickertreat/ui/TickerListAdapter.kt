package com.ericromero.tickertreat.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.ericromero.tickertreat.data.model.TickerPrice
import com.ericromero.tickertreat.databinding.SymbolListItemBinding

/**
 * Adapter for TickerPrice list, binds the data to the view
 *
 * @author eromero
 */
class TickerListAdapter : RecyclerView.Adapter<TickerListAdapter.TickerViewHolder>() {

    inner class TickerViewHolder(val binding: SymbolListItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(tickerPrice: TickerPrice) {
            binding.tickerPrice = tickerPrice
            // TODO: Add clickListener to navigate to ticker detail
        }
    }

    private val items: MutableList<TickerPrice> = mutableListOf()

    /**
     * Re-populates list of ticker prices
     */
    fun updateList(newItems: List<TickerPrice>) {
        items.clear()
        items.addAll(newItems)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TickerViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = SymbolListItemBinding.inflate(inflater)
        return TickerViewHolder(binding)
    }

    override fun onBindViewHolder(holder: TickerViewHolder, position: Int) {
        holder.bind(items[position])
    }

    override fun getItemCount(): Int = items.size
}