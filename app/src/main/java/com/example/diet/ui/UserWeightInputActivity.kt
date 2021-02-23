package com.example.diet.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.diet.R
import kotlinx.android.synthetic.main.activity_user_weight_input.*
import org.joda.time.DateTime
import org.joda.time.format.DateTimeFormat
import java.util.*

class UserWeightInputActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_weight_input)
        val currentDate = DateTime.now()
            .toString(DateTimeFormat.forPattern("yyyy-MM-dd").withLocale(Locale.getDefault()))
        dateTextView.text = currentDate.toString()
    }
}
