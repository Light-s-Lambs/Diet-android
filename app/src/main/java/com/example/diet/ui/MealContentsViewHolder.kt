package com.example.diet.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.diet.R
import kotlinx.android.synthetic.main.user_meal_list_item.view.*

class MealContentsViewHolder(
    parent: ViewGroup
) : RecyclerView.ViewHolder(
    LayoutInflater.from(parent.context).inflate(R.layout.user_meal_list_item, parent, false)
) {
    fun bind(meal: Meal) {
        itemView.mealTypeTextView.text = meal.mealType
        itemView.mealMenuTextView.text = meal.menu
        itemView.mealCalorieTextView.text = meal.calorie
    }
}
