package com.example.diet.ui

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.diet.R
import kotlinx.android.synthetic.main.activity_initial.*

class InitialActivity : AppCompatActivity(){
    private var userName = ""
    private var userHeight : Int = 0
    private var userAge : Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_initial)
        button_init_ok.setOnClickListener {
            readUserInfo()
            val msg : String = userName + " : " + userHeight.toString() + " : " + userAge.toString()
            Toast.makeText(applicationContext, msg, Toast.LENGTH_LONG).show()
        }
    }
    private fun readUserInfo(){
        userName = editText_Name.getText().toString()
        userHeight = editText_Height.getText().toString().toInt()
        userAge = editText_Age.getText().toString().toInt()
    }


}