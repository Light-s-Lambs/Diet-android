package com.example.diet.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.diet.R
import com.example.diet.lifestyle.model.LifeStyle
import kotlinx.android.synthetic.main.user_life_style_list_item.view.*

class LifeStyleContentViewHolder(
    parent: ViewGroup
) : RecyclerView.ViewHolder(
    LayoutInflater.from(parent.context).inflate(R.layout.user_life_style_list_item, parent, false)
) {
    fun bind(lifeStyle: LifeStyle) {
        itemView.lifeStyleNameTextView.text = lifeStyle.name
        itemView.lifeStyleTimeTextView.text = lifeStyle.time.toString()
        itemView.lifeStyleBurnedCalorieTextView.text = lifeStyle.burnedCalorie.toString()
    }
}
