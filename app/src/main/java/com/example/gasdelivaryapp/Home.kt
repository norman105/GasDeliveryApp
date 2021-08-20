package com.example.gasdelivaryapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageButton

class Home : AppCompatActivity() {
    lateinit var kgas: ImageButton
    lateinit var has: ImageButton
    lateinit var blue: ImageButton
    lateinit var total: ImageButton
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        kgas = findViewById(R.id.kgas)
        kgas.setOnClickListener {
            val intent = Intent(this@Home, Upload::class.java)
            intent.putExtra("image",R.drawable.kgas)
            startActivity(intent)
        }
        has = findViewById(R.id.has)
        has.setOnClickListener {
            val intent = Intent(this@Home, Upload::class.java)
            intent.putExtra("image",R.drawable.afrigas)
            startActivity(intent)
        }
        blue = findViewById(R.id.blue)
        blue.setOnClickListener {
            val intent = Intent(this@Home, Upload::class.java)
            intent.putExtra("image",R.drawable.blue)
            startActivity(intent)
        }
        total = findViewById(R.id.total)
        total.setOnClickListener {
            val intent = Intent(this@Home, Upload::class.java)
            intent.putExtra("image",R.drawable.ttotal)
            startActivity(intent)
        }
    }
}