package com.example.gasdelivaryapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        //using handler to delay screen
        Handler().postDelayed({
            val intents =Intent (this,OnboardinScreen::class.java)
            startActivity(intents)
        },2000)//screen delay for 2seconnds
    }
}