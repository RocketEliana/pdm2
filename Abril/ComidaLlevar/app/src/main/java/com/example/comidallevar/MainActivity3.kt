package com.example.comidallevar

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.comidallevar.databinding.ActivityMain3Binding
import viewModel.AppViewModel

class MainActivity3 : AppCompatActivity() {
    private lateinit var binding: ActivityMain3Binding
    private lateinit var adapter: AdapterListaPedido
    private val viewModel: AppViewModel by viewModels ()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityMain3Binding.inflate(layoutInflater)
        setContentView(binding.root)
     adapter= AdapterListaPedido(this,mutableListOf())
     binding.listaPedido.adapter=adapter
        viewModel.listaPedido.observe(this){
            list->adapter.actualizarLista(list)
        }
        binding.volver.setOnClickListener { val intent= Intent(this, MainActivity2::class.java)
        startActivity(intent)}

    }
}