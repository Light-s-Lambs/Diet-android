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
        lifeStyleEntryButton.setOnClickListener {
            startActivity(Intent(this, UserLifeStyleListActivity::class.java))
        }
    }
}