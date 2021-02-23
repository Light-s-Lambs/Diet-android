package com.example.diet.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.ConcatAdapter
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.diet.R
import kotlinx.android.synthetic.main.activity_user_life_style.*
import org.joda.time.DateTime
import org.joda.time.format.DateTimeFormat
import java.util.*

class UserLifeStyleActivity : AppCompatActivity() {

    private var lifeStyleList = listOf<LifeStyle>()
    private val lifeStyleSectionHeaderAdapter = LifeStyleSectionHeaderViewAdapter()
    private val lifeStyleContentViewAdapter = LifeStyleContentViewAdapter()
    private val lifeStyleListRecyclerViewAdapter =
        ConcatAdapter(lifeStyleSectionHeaderAdapter, lifeStyleContentViewAdapter)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_life_style)
        val currentDate = DateTime.now()
            .toString(DateTimeFormat.forPattern("yyyy-MM-dd").withLocale(Locale.getDefault()))
        dateTextView.text = currentDate.toString()

        activityMetabolismTextView.text = "0"
        basalMetabolismTextView.text = "0"

        lifeStyleContentViewAdapter.submitList(lifeStyleList)

        lifeStyleListRecyclerView.adapter = lifeStyleListRecyclerViewAdapter
        lifeStyleListRecyclerView.layoutManager = LinearLayoutManager(this)
        lifeStyleListRecyclerView.setHasFixedSize(true)
    }
}
