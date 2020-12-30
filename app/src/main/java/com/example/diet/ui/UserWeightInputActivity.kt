package com.example.diet.ui
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.diet.R
import kotlinx.android.synthetic.main.activity_user_weight_input.*
import java.text.DateFormat.getDateInstance
import java.text.SimpleDateFormat
import java.util.*

class UserWeightInputActivity : AppCompatActivity(){
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_weight_input)
        val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
        val currentDate = dateFormat.format(Date())
        dateTextView.setText(currentDate.toString())
        weightInfoButton.isEnabled = false
    }
}