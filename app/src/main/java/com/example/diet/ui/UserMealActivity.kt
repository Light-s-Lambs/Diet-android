package com.example.diet.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.ConcatAdapter
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.diet.R
import com.example.diet.meal.model.Meal
import kotlinx.android.synthetic.main.activity_user_meal.*
import org.joda.time.DateTime
import org.joda.time.format.DateTimeFormat
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
        val currentDate = DateTime.now()
            .toString(DateTimeFormat.forPattern("yyyy-MM-dd").withLocale(Locale.getDefault()))
        dateTextView.text = currentDate.toString()

        calorieNeededTextView.text = "0"

        mealContentAdapter.submitList(mealList)

        mealListRecyclerView.adapter = mealListRecyclerViewAdapter
        mealListRecyclerView.layoutManager = LinearLayoutManager(this)
        mealListRecyclerView.setHasFixedSize(true)
    }
}
