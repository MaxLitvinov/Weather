package com.weather.feature.home_page.adapter

import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.weather.feature.home_page.R
import com.weather.feature.home_page.databinding.ItemDayBinding
import com.weather.feature.home_page.model.DayForecast

class DayViewHolder(
    private val binding: ItemDayBinding,
    private val onItemClick: (Long) -> Unit,
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(model: DayForecast) = with(binding) {
        tvDayName.text = model.dayName
        tvDayNightTemperature.text = model.dayNightTemperature
        model.id?.let { id ->
            setAsButton { onItemClick(id) }
        } ?: {
            setAsItem()
        }
    }

    private fun ItemDayBinding.setAsButton(action: () -> Unit) {
        val chevronRightIcon = ContextCompat.getDrawable(root.context, R.drawable.ic_chevron_right)
        tvDayNightTemperature.setCompoundDrawablesWithIntrinsicBounds(
            null,
            null,
            chevronRightIcon,
            null
        )

        val iconPadding = root.resources.getDimensionPixelOffset(R.dimen.size_10_dp)
        tvDayNightTemperature.compoundDrawablePadding = iconPadding

        root.setOnClickListener { action() }
    }

    private fun ItemDayBinding.setAsItem() {
        tvDayNightTemperature.compoundDrawablePadding = 0
        tvDayNightTemperature.setCompoundDrawables(null, null, null, null)

        root.setOnClickListener(null)
    }
}
