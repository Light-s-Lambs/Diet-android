package com.example.diet.ui

import android.content.Context
import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.diet.R

class MealSectionHeaderHolder(
    itemView: View,
    private val context: Context
) : RecyclerView.ViewHolder(itemView) {
    private val mealType: TextView =
        itemView.findViewById<TextView>(R.id.mealSectionHeaderTypeTextView)
    private val mealMenu: TextView =
        itemView.findViewById<TextView>(R.id.mealSectionHeaderMenuTextView)
    private val mealCalorie: TextView =
        itemView.findViewById<TextView>(R.id.mealSectionHeaderCalorieTextView)

    fun bind() {
        mealType.text = context.getText(R.string.userMealSectionHeaderTypeLabel)
        mealMenu.text = context.getText(R.string.userMealSectionHeaderMealLabel)
        mealCalorie.text = context.getText(R.string.userMealSectionHeaderCalorieLabel)
    }
}
