package com.example.mislugares

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.mislugares.databinding.ActivityDetalleBinding
import com.example.mislugares.viewModel.AppViewModel

class Detalle : AppCompatActivity() {
    private lateinit var binding: ActivityDetalleBinding
    private val viewModel: AppViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityDetalleBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val idRecibido = intent.getLongExtra("id", -1)
        if (idRecibido != -1L) {
            val bundlea = Bundle()
            bundlea.putLong("id", idRecibido)
            val fragmentA = DetalleFragment()
            fragmentA.arguments = bundlea
            val transactiona = supportFragmentManager.beginTransaction()
            transactiona.replace(R.id.fragmentoSuperior,fragmentA).commit()
            val bundleb = Bundle()
            bundleb.putLong("id", idRecibido)
            val fragmentB = MapsFragment()
            fragmentB.arguments = bundleb
            val transactionb = supportFragmentManager.beginTransaction()
            transactionb.replace(R.id.fragmentoInferior,fragmentB).commit()

        }




        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }
}