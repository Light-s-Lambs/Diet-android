package com.example.diet.ui

import android.os.Bundle
import android.view.animation.AnimationUtils
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.ConcatAdapter
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.diet.R
import kotlinx.android.synthetic.main.activity_user_lifestyle_list.*
import java.text.SimpleDateFormat
import java.util.*

class UserLifeStyleListActivity : AppCompatActivity() {

    private var lifeStyleList = arrayListOf<LifeStyle>(
        LifeStyle("Sleeping", "22 hr", "348 kcal"),
        LifeStyle("Running", "2 hr", "1510 kcal"))

    private var isOpen = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_lifestyle_list)
        val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
        val currentDate = dateFormat.format(Date())
        dateTextView.setText(currentDate.toString())

        val lifeStyleListHeaderAdapter = LifeStyleListHeaderAdapter(this)
        val lifeStyleListBodyAdapter = LifeStyleListBodyAdapter(this, lifeStyleList)
        lifeStyleListRecyclerView.adapter = ConcatAdapter(lifeStyleListHeaderAdapter, lifeStyleListBodyAdapter)

        val lifeStyleListRecyclerLayoutManager = LinearLayoutManager(this)
        lifeStyleListRecyclerView.layoutManager = lifeStyleListRecyclerLayoutManager
        lifeStyleListRecyclerView.setHasFixedSize(true)

        val fabOpen = AnimationUtils.loadAnimation(this, R.anim.fab_open)
        val fabClose = AnimationUtils.loadAnimation(this, R.anim.fab_close)

        lifeStyleMenuListFloatingActionButton.setOnClickListener {
            if (isOpen) {
                addLifeStyleFloatingActionButton.startAnimation(fabClose)
                addLifeStyleFloatingActionButton.isClickable = false
                isOpen = !isOpen
            } else {
                addLifeStyleFloatingActionButton.startAnimation(fabOpen)
                addLifeStyleFloatingActionButton.isClickable = true
                isOpen = !isOpen
            }
        }
    }
}