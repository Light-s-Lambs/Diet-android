package com.example.diet.ui

import android.content.Context
import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.diet.R

class LifeStyleListBodyHolder(private val context: Context, itemView: View) :
    RecyclerView.ViewHolder(itemView) {
    private val lifeStyleName: TextView =
        itemView.findViewById<TextView>(R.id.lifeStyleNameTextView)
    private val lifeStyleTime: TextView =
        itemView.findViewById<TextView>(R.id.lifeStyleTimeTextView)
    private val lifeStyleBurnedCalorie: TextView =
        itemView.findViewById<TextView>(R.id.lifeStyleBurnedCalorieTextView)

    fun bind(lifeStyle: LifeStyle) {
        lifeStyleName.text = lifeStyle.name
        lifeStyleTime.text = lifeStyle.time
        lifeStyleBurnedCalorie.text = lifeStyle.burnedCalorie
    }
}