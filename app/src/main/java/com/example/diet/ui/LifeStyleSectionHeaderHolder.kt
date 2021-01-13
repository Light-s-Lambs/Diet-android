package com.example.diet.ui

import android.content.Context
import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.diet.R

class LifeStyleSectionHeaderHolder(
    itemView: View,
    private val context: Context
) :
    RecyclerView.ViewHolder(itemView) {
    private val lifeStyleName: TextView =
        itemView.findViewById<TextView>(R.id.lifeStyleNameTextView)
    private val lifeStyleTime: TextView =
        itemView.findViewById<TextView>(R.id.lifeStyleTimeTextView)
    private val lifeStyleBurnedCalorie: TextView =
        itemView.findViewById<TextView>(R.id.lifeStyleBurnedCalorieTextView)

    fun bind() {
        lifeStyleName.text = context.getString(R.string.userLifeStyleSectionHeaderNameLabel)
        lifeStyleTime.text = context.getString(R.string.userLifeStyleSectionHeaderTimeLabel)
        lifeStyleBurnedCalorie.text =
            context.getString(R.string.userLifeStyleSectionHeaderBurnedCalorieLabel)
    }
}
