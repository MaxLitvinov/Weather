package com.weather.feature.home_page.adapter

import android.content.Context
import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.RecyclerView
import com.weather.foundation.resources.R

class DayListDecoration(context: Context) : DividerItemDecoration(context, 0) {

    private var topMargin: Int = 0
    private var leftMargin: Int = 0
    private var rightMargin: Int = 0
    private var bottomMargin: Int = 0
    private var lastItemBottomMargin: Int = 0

    private var itemPosition: Int = 0

    init {
        with(context.resources) {
            topMargin = getDimension(R.dimen.size_8_dp).toInt()
            leftMargin = getDimension(R.dimen.size_16_dp).toInt()
            rightMargin = getDimension(R.dimen.size_16_dp).toInt()
            bottomMargin = getDimension(R.dimen.size_8_dp).toInt()
            lastItemBottomMargin = getDimension(R.dimen.size_16_dp).toInt()
        }
    }

    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        super.getItemOffsets(outRect, view, parent, state)
        itemPosition = parent.getChildAdapterPosition(view)

        val lastItemPosition = parent.adapter?.itemCount?.minus(1)

        with(outRect) {
            when (itemPosition) {
                0 -> {
                    top = 0
                    bottom = bottomMargin
                }
                lastItemPosition -> {
                    top = topMargin
                    bottom = lastItemBottomMargin
                }
                else -> {
                    top = topMargin
                    bottom = bottomMargin
                }
            }

            left = leftMargin
            right = rightMargin
        }
    }
}
