package com.example.diet.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.ConcatAdapter
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.diet.R
import kotlinx.android.synthetic.main.activity_user_meal.*
import java.text.SimpleDateFormat
import java.util.*

class UserMealActivity : AppCompatActivity() {
    private var mealList = listOf<Meal>()
    private val mealSectionHeaderAdapter = MealSectionHeaderViewAdapter()
    private val mealContentAdapter = MealContentViewAdapter()
    private val mealListRecyclerViewAdapter =
        ConcatAdapter(mealSectionHeaderAdapter, mealContentAdapter)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_meal)
        val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
        val currentDate = dateFormat.format(Date())
        dateTextView.text = currentDate.toString()

        calorieNeededTextView.text = "0"

        mealContentAdapter.submitList(mealList)

        mealListRecyclerView.adapter = mealListRecyclerViewAdapter
        mealListRecyclerView.layoutManager = LinearLayoutManager(this)
        mealListRecyclerView.setHasFixedSize(true)
    }
}
