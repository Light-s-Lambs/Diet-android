package com.example.diet.ui

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.diet.R
import kotlinx.android.synthetic.main.activity_main.*

class TestMainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        mainEntryButton.setOnClickListener {
            startActivity(Intent(this, UserProfileInputActivity::class.java))
        }
        userLifeStyleInputEntryButton.setOnClickListener {
            startActivity(Intent(this, UserLifeStyleInputActivity::class.java))
        }
        lifeStyleEntryButton.setOnClickListener {
            startActivity(Intent(this, UserLifeStyleActivity::class.java))
        }
        mealEntryButton.setOnClickListener {
            startActivity(Intent(this, UserMealActivity::class.java))
        }
    }
}
