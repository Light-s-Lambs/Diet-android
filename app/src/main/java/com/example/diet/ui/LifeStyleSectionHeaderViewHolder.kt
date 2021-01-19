package com.example.diet.ui

import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.diet.R
import kotlinx.android.synthetic.main.user_life_style_section_header.view.*

class LifeStyleSectionHeaderViewHolder(
    itemView: LifeStyleSectionHeaderView
) : RecyclerView.ViewHolder(itemView) {
    fun bind() {
        itemView.lifeStyleSectionHeaderNameTextView.text =
            itemView.context.getString(R.string.userLifeStyleSectionHeaderNameLabel)
        itemView.lifeStyleSectionHeaderTimeTextView.text =
            itemView.context.getString(R.string.userLifeStyleSectionHeaderTimeLabel)
        itemView.lifeStyleSectionHeaderBurnedCalorieTextView.text =
            itemView.context.getString(R.string.userLifeStyleSectionHeaderBurnedCalorieLabel)
    }
}
