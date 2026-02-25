package com.example.crack

import android.os.Bundle
import androidx.activity.addCallback
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.crack.data.AppDataBase
import com.example.crack.data.PersonAdapter
import com.example.crack.databinding.ActivityMain2Binding
import com.example.crack.databinding.ActivityMain3Binding

class MainActivity3 : AppCompatActivity() {
    private lateinit var binding: ActivityMain3Binding
    private lateinit var appDataBase: AppDataBase
    private lateinit var adapter: PersonAdapter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMain3Binding.inflate(layoutInflater)
        setContentView(binding.root)
        enableEdgeToEdge()
        val appDataBase= AppDataBase.getDatabase(this)
        val lista=appDataBase.personaDao().listaPersonas()
        adapter= PersonAdapter(this, lista)
        binding.lista.adapter=adapter
        onBackPressedDispatcher.addCallback(this) {
            // Aquí pones lo que quieres que pase cuando den atrás
            finish()
        }
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }
}

