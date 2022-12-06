package com.weather.feature.home_page.adapter

import androidx.core.content.ContextCompat
import androidx.core.view.updatePadding
import androidx.recyclerview.widget.RecyclerView
import com.weather.feature.home_page.databinding.ItemDayBinding
import com.weather.feature.home_page.model.DayForecast
import com.weather.foundation.resources.R

class DayViewHolder(
    private val binding: ItemDayBinding,
    private val onItemClick: (Long) -> Unit,
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(model: DayForecast) = with(binding) {
        tvDayName.text = model.dayName
        tvDayNightTemperature.text = model.dayNightTemperature
        if (null != model.id) {
            setAsButton { onItemClick(model.id) }
        } else {
            setAsItem()
        }
    }

    private fun ItemDayBinding.setAsButton(action: () -> Unit) {
        val chevronRightIcon = ContextCompat.getDrawable(root.context, com.weather.feature.home_page.R.drawable.ic_chevron_right)
        tvDayNightTemperature.setCompoundDrawablesWithIntrinsicBounds(
            null,
            null,
            chevronRightIcon,
            null
        )

        val iconPadding = root.resources.getDimensionPixelOffset(R.dimen.size_10_dp)
        tvDayNightTemperature.compoundDrawablePadding = iconPadding

        root.setOnClickListener { action() }
        root.isEnabled = true
    }

    private fun ItemDayBinding.setAsItem() = with(tvDayNightTemperature) {
        compoundDrawablePadding = 0
        setCompoundDrawables(null, null, null, null)
        val chevronRightWidthAndPadding = context.resources.getDimensionPixelOffset(R.dimen.size_19_dp)
        updatePadding(right = paddingRight + chevronRightWidthAndPadding)

        root.setOnClickListener(null)
        root.isEnabled = false
    }
}
