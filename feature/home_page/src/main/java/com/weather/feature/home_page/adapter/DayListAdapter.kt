package com.weather.feature.home_page.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.weather.feature.home_page.databinding.ItemDayBinding
import com.weather.feature.home_page.model.DayForecast

class DayListAdapter(
    private val onItemClick: (Long) -> Unit
) : RecyclerView.Adapter<DayViewHolder>() {

    private var items: List<DayForecast> = emptyList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = DayViewHolder(
        ItemDayBinding.inflate(LayoutInflater.from(parent.context), parent, false),
        onItemClick
    )

    override fun onBindViewHolder(holder: DayViewHolder, position: Int) {
        holder.bind(items[position])
    }

    override fun getItemCount(): Int = items.size

    @SuppressLint("NotifyDataSetChanged")
    fun setItems(items: List<DayForecast>) {
        this.items = items
        notifyDataSetChanged()
    }
}
