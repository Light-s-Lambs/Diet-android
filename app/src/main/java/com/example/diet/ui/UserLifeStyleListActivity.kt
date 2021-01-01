package com.example.diet.ui

import android.os.Bundle
import android.view.animation.AnimationUtils
import androidx.appcompat.app.AppCompatActivity
import com.example.diet.R
import kotlinx.android.synthetic.main.activity_user_lifestyle_list.*
import java.text.SimpleDateFormat
import java.util.*

class UserLifeStyleListActivity : AppCompatActivity() {
    private var isOpen = false
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_lifestyle_list)
        val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
        val currentDate = dateFormat.format(Date())
        dateTextView.setText(currentDate.toString())

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