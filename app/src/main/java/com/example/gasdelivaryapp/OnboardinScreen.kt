package com.example.gasdelivaryapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

class OnboardinScreen : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_onboardin_screen)
        supportFragmentManager.beginTransaction().replace(R.id.framelayout,FragmentA()).commit()
    }
}