package com.example.homework23

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.lifecycle.ViewModelProvider

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val label: TextView = findViewById(R.id.label)
        val button: Button = findViewById(R.id.testB)

        val viewModel = ViewModelProvider(this).get(MyViewModel::class.java)
        button.setOnClickListener { viewModel.getData() }

        viewModel.uiState.observe(this) {
            when (it) {
                is MyViewModel.UIState.NoData -> label.text = "Empty"
                is MyViewModel.UIState.UpdatedData -> label.text = it.type
                is MyViewModel.UIState.Processing -> label.text = "Processing..."
                is MyViewModel.UIState.Error -> label.text = "Error"
            }
        }
    }
}