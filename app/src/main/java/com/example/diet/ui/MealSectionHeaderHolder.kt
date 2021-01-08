package com.example.diet.ui

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.diet.R

class MealSectionHeaderHolder(itemView: View) :
    RecyclerView.ViewHolder(itemView) {
    private val mealType: TextView =
        itemView.findViewById<TextView>(R.id.mealTypeTextView)
    private val mealMenu: TextView =
        itemView.findViewById<TextView>(R.id.mealMenuTextView)
    private val mealCalorie: TextView =
        itemView.findViewById<TextView>(R.id.mealCalorieTextView)

    fun bind() {
        mealType.text = "Meal\nType"
        mealMenu.text = "Menu"
        mealCalorie.text = "Calorie"
    }
}
