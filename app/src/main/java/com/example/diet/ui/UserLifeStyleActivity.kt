package com.example.diet.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.ConcatAdapter
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.diet.R
import domain.lifestyle.LifeStyle
import kotlinx.android.synthetic.main.activity_user_lifestyle.*
import java.text.SimpleDateFormat
import java.util.*

class UserLifeStyleActivity : AppCompatActivity() {

    private var lifeStyleList = listOf<LifeStyle>()
    private val lifeStyleSectionHeaderAdapter = LifeStyleSectionHeaderAdapter(this)
    private val lifeStyleContentAdapter = LifeStyleContentAdapter(this)
    private val lifeStyleListRecyclerViewAdapter =
        ConcatAdapter(lifeStyleSectionHeaderAdapter, lifeStyleContentAdapter)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_lifestyle)
        val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
        val currentDate = dateFormat.format(Date())
        dateTextView.text = currentDate.toString()

        activityMetabolismTextView.text = "0"
        basalMetabolismTextView.text = "0"

        lifeStyleContentAdapter.submitList(lifeStyleList)
        lifeStyleContentAdapter.notifyDataSetChanged()

        lifeStyleListRecyclerView.adapter = lifeStyleListRecyclerViewAdapter
        lifeStyleListRecyclerView.layoutManager = LinearLayoutManager(this)
        lifeStyleListRecyclerView.setHasFixedSize(true)
    }
}
