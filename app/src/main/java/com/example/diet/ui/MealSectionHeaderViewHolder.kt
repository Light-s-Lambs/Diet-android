package com.example.diet.ui

import androidx.recyclerview.widget.RecyclerView
import com.example.diet.R
import kotlinx.android.synthetic.main.user_meal_section_header.view.*

class MealSectionHeaderViewHolder(
    itemView: MealSectionHeaderView
) : RecyclerView.ViewHolder(itemView) {
    fun bind() {
        itemView.mealSectionHeaderTypeTextView.text =
            itemView.context.getText(R.string.userMealSectionHeaderTypeLabel)
        itemView.mealSectionHeaderMenuTextView.text =
            itemView.context.getText(R.string.userMealSectionHeaderMenuLabel)
        itemView.mealSectionHeaderCalorieTextView.text =
            itemView.context.getText(R.string.userMealSectionHeaderCalorieLabel)
    }
}
