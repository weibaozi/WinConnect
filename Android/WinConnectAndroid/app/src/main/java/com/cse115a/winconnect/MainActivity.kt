package com.cse115a.winconnect

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView

class MainActivity : AppCompatActivity() {
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_main)
    val message = findViewById<TextView>(R.id.message)
    val button = findViewById<Button>(R.id.button)
    val input = findViewById<TextView>(R.id.input)
    button.setOnClickListener {
      message.text = input.text
    }
  }
}