package com.example.diet.ui

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.diet.R
import kotlinx.android.synthetic.main.activity_user_profile_input.*

class UserProfileInputActivity : AppCompatActivity(){
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_profile_input)
        buttonUserProfileInputOk.setOnClickListener {
            val userName : String = editTextUserProfileInputName.getText().toString()
            val userHeight : Int = editTextUserProfileInputHeight.getText().toString().toInt()
            val userAge : Int = editTextUserProfileInputAge.getText().toString().toInt()
            Toast.makeText(applicationContext, "$userName : $userHeight : $userAge", Toast.LENGTH_LONG).show()
        }
    }
}