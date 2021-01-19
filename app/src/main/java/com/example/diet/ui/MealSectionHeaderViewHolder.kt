package com.example.diet.ui

import android.content.Context
import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.diet.R

class MealSectionHeaderViewHolder(
    itemView: View
) : RecyclerView.ViewHolder(itemView) {
    private val mealType: TextView =
        itemView.findViewById<TextView>(R.id.mealSectionHeaderTypeTextView)
    private val mealMenu: TextView =
        itemView.findViewById<TextView>(R.id.mealSectionHeaderMenuTextView)
    private val mealCalorie: TextView =
        itemView.findViewById<TextView>(R.id.mealSectionHeaderCalorieTextView)

    fun bind() {
        mealType.text = itemView.context.getText(R.string.userMealSectionHeaderTypeLabel)
        mealMenu.text = itemView.context.getText(R.string.userMealSectionHeaderMenuLabel)
        mealCalorie.text = itemView.context.getText(R.string.userMealSectionHeaderCalorieLabel)
    }
}
