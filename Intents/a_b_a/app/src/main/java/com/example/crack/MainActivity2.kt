package com.example.crack

import android.Manifest
import android.content.Intent
import android.graphics.Bitmap
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.example.crack.databinding.ActivityMain2Binding

class MainActivity2 : AppCompatActivity() {
    private lateinit var binding: ActivityMain2Binding
    private var bitmapFoto: Bitmap? = null

    // Launcher para la Cámara
    private val launcherTomarFoto = registerForActivityResult(
        ActivityResultContracts.TakePicturePreview()
    ) { bitmap ->
        if (bitmap != null) {
            binding.imageView3.setImageBitmap(bitmap)
            bitmapFoto = bitmap
            binding.enviar.visibility = View.VISIBLE
        }
    }

    // Launcher para Permisos
    private val launcherPermisoCamara = registerForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { concedido ->
        if (concedido) launcherTomarFoto.launch(null)
        else Toast.makeText(this, "Permiso necesario", Toast.LENGTH_SHORT).show()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMain2Binding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.enviar.visibility = View.GONE

        binding.hacerFoto.setOnClickListener {
            launcherPermisoCamara.launch(Manifest.permission.CAMERA)
        }

        binding.enviar.setOnClickListener {
            val nom = binding.nombre.text.toString()
            val ape = binding.apellidos.text.toString()

            if (nom.isNotEmpty() && ape.isNotEmpty() && bitmapFoto != null) {
                // EL PAQUETE DE VUELTA: Intent vacío
                val resultIntent = Intent()
                resultIntent.putExtra("nombre", nom)
                resultIntent.putExtra("apellidos", ape)
                resultIntent.putExtra("foto", bitmapFoto)

                setResult(RESULT_OK, resultIntent)
                finish() // Cerramos y volvemos
            } else {
                Toast.makeText(this, "Faltan datos", Toast.LENGTH_SHORT).show()
            }
        }
    }
}