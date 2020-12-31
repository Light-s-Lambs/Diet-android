package com.example.diet.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.diet.R
import kotlinx.android.synthetic.main.activity_user_lifestyle_list.*
import java.text.SimpleDateFormat
import java.util.*

class UserLifeStyleListActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_lifestyle_list)
        val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
        val currentDate = dateFormat.format(Date())
        dateTextView.setText(currentDate.toString())
    }
}