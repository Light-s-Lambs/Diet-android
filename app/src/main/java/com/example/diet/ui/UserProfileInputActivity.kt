package com.example.diet.ui

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.diet.R
import kotlinx.android.synthetic.main.activity_user_profile_input.*

class UserProfileInputActivity : AppCompatActivity(){
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_profile_input)
        completeButton.setOnClickListener {
            val userName : String = nameEditView.getText().toString()
            val userHeight : Int = heightEditView.getText().toString().toInt()
            val userAge : Int = ageEditView.getText().toString().toInt()
            Toast.makeText(applicationContext, "$userName : $userHeight : $userAge", Toast.LENGTH_LONG).show()
            startActivity(Intent(this, UserWeightInputActivity::class.java))
        }
    }
}