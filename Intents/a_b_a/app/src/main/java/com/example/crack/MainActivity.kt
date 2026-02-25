package com.example.crack

import android.content.Intent
import android.graphics.Bitmap
import android.os.Bundle
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.example.crack.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    // LA ADUANA: Aquí es donde aterrizan los datos al hacer finish() en la otra
    private val miLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { res ->
        if (res.resultCode == RESULT_OK) {
            val nombreRecibido = res.data?.getStringExtra("nombre")
            val apellidosRecibido = res.data?.getStringExtra("apellidos")

            // Para la foto, como es un Bitmap, lo sacamos como Parcelable
            val fotoRecibida = res.data?.getParcelableExtra<Bitmap>("foto")

            // USAMOS LOS DATOS (Pintarlos en la interfaz)
            binding.nombre.text = "$nombreRecibido $apellidosRecibido"
            if (fotoRecibida != null) {
                binding.imageView2.setImageBitmap(fotoRecibida)
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.button.setOnClickListener {
            // IMPORTANTE: Para que el launcher funcione, lanzamos con miLauncher
            val intent = Intent(this, MainActivity2::class.java)
            miLauncher.launch(intent)
        }
    }
}