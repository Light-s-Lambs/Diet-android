package com.example.diet.ui

import android.content.Context
import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.diet.R

class LifeStyleListHeaderHolder(private val context: Context, itemView: View) :
    RecyclerView.ViewHolder(itemView) {
    private val lifeStyleName: TextView =
        itemView.findViewById<TextView>(R.id.lifeStyleNameTextView)
    private val lifeStyleTime: TextView =
        itemView.findViewById<TextView>(R.id.lifeStyleTimeTextView)
    private val lifeStyleBurnedCalorie: TextView =
        itemView.findViewById<TextView>(R.id.lifeStyleBurnedCalorieTextView)

    fun bind() {
        lifeStyleName.maxLines = 2
        lifeStyleName.text = context.getString(R.string.userLifeStyleListLifeStyleNameLabel)
        lifeStyleTime.text = context.getString(R.string.userLifeStyleListLifeStyleTimeLabel)
        lifeStyleBurnedCalorie.maxLines = 2
        lifeStyleBurnedCalorie.text =
            context.getString(R.string.userLifeStyleListBurnedCalorieLabel)
    }
}