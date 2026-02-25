package com.example.crack

import android.Manifest
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.widget.SeekBar
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.example.crack.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var  numero:String

    private val launcherLlamada = registerForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { isGranted ->
        if (isGranted) {
            Toast.makeText(this, "Permiso de llamadas concedido", Toast.LENGTH_SHORT).show()
            hacerLlamada(numero)
        } else {
            Toast.makeText(this, "Permiso de llamadas denegado", Toast.LENGTH_SHORT).show()
        }
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        numero="623338696"
       binding.llamar.setOnClickListener {

           launcherLlamada.launch(Manifest.permission.CALL_PHONE)
       }

       }
    private fun hacerLlamada(numero: String) {
        val intent = Intent(Intent.ACTION_CALL)
        intent.data = Uri.parse("tel:$numero")
        startActivity(intent)
    }

}