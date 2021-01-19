package com.example.diet.ui

import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.user_meal_list_item.view.*

class MealContentsViewHolder(
    itemView: MealContentsView
) : RecyclerView.ViewHolder(itemView) {
    fun bind(meal: Meal) {
        itemView.mealTypeTextView.text = meal.mealType
        itemView.mealMenuTextView.text = meal.menu
        itemView.mealCalorieTextView.text = meal.calorie
    }
}
