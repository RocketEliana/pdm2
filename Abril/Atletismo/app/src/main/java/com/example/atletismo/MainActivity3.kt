package com.example.atletismo

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.atletismo.databinding.ActivityMain3Binding
import viewModel.AppViewModel

class MainActivity3 : AppCompatActivity() {
    private lateinit var binding: ActivityMain3Binding

    private val viewModel: AppViewModel by viewModels ()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityMain3Binding.inflate(layoutInflater)
        setContentView(binding.root)




    }
}