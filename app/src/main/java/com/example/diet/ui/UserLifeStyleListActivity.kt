package com.example.diet.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.ConcatAdapter
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.diet.R
import kotlinx.android.synthetic.main.activity_user_lifestyle_list.*
import java.text.SimpleDateFormat
import java.util.*

class UserLifeStyleListActivity : AppCompatActivity() {

    private var lifeStyleList = listOf<LifeStyle>()
    private val lifeStyleListHeaderAdapter = LifeStyleListHeaderAdapter(this)
    private val lifeStyleListBodyAdapter = LifeStyleListBodyAdapter(this, lifeStyleList)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_lifestyle_list)
        val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
        val currentDate = dateFormat.format(Date())
        dateTextView.text = currentDate.toString()

        activityMetabolismTextView.text = "0"
        basalMetabolismTextView.text = "0"

        lifeStyleListRecyclerView.adapter =
            ConcatAdapter(lifeStyleListHeaderAdapter, lifeStyleListBodyAdapter)
        lifeStyleListRecyclerView.layoutManager = LinearLayoutManager(this)
        lifeStyleListRecyclerView.setHasFixedSize(true)
    }
}