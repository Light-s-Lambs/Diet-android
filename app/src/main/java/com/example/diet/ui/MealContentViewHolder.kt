package com.example.diet.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.diet.R
import kotlinx.android.synthetic.main.user_meal_list_item.view.*
import com.example.diet.meal.model.Meal

class MealContentViewHolder(
    parent: ViewGroup
) : RecyclerView.ViewHolder(
    LayoutInflater.from(parent.context).inflate(R.layout.user_meal_list_item, parent, false)
) {
    fun bind(meal: Meal) {
        itemView.mealTypeTextView.text = meal.mealType.toString()
        itemView.mealMenuTextView.text = meal.menuName.toString()
        itemView.mealCalorieTextView.text = meal.calorie
    }
}
