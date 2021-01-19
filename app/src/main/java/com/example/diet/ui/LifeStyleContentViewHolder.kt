package com.example.diet.ui

import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.user_life_style_list_item.view.*

class LifeStyleContentViewHolder(
    itemView: LifeStyleContentView
) : RecyclerView.ViewHolder(itemView) {
    fun bind(lifeStyle: LifeStyle) {
        itemView.lifeStyleNameTextView.text = lifeStyle.name
        itemView.lifeStyleTimeTextView.text = lifeStyle.time
        itemView.lifeStyleBurnedCalorieTextView.text = lifeStyle.burnedCalorie
    }
}
