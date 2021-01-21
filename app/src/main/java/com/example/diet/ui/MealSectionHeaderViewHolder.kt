package com.example.diet.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.diet.R
import kotlinx.android.synthetic.main.user_meal_section_header.view.*

class MealSectionHeaderViewHolder(
    parent: ViewGroup
) : RecyclerView.ViewHolder(
    LayoutInflater.from(parent.context).inflate(R.layout.user_meal_section_header, parent, false)
) {
    fun bind() {
        itemView.mealSectionHeaderTypeTextView.text =
            itemView.context.getText(R.string.userMealSectionHeaderTypeLabel)
        itemView.mealSectionHeaderMenuTextView.text =
            itemView.context.getText(R.string.userMealSectionHeaderMenuLabel)
        itemView.mealSectionHeaderCalorieTextView.text =
            itemView.context.getText(R.string.userMealSectionHeaderCalorieLabel)
    }
}
