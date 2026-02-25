package com.example.crack

import android.content.Intent
import android.graphics.Bitmap
import android.os.Bundle
import android.widget.SeekBar
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.example.crack.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.edad.setOnSeekBarChangeListener(object: SeekBar.OnSeekBarChangeListener{
            override fun onProgressChanged(
                p0: SeekBar?,
                p1: Int,
                p2: Boolean
            ) {
                binding.textView.text=p1.toString()
            }

            override fun onStartTrackingTouch(p0: SeekBar?) {

            }

            override fun onStopTrackingTouch(p0: SeekBar?) {
            }

        })
        binding.enviar.setOnClickListener {
            val nombre=binding.nombre.text.toString()
            val edad=binding.textView.text.toString()
            if(nombre.isEmpty() || edad.isEmpty()){
                Toast.makeText(this,"Debe rellenar los dos campos", Toast.LENGTH_LONG).show()
                return@setOnClickListener
            }
            val intent=Intent(this, MainActivity2::class.java).apply {
                putExtra("NOMBRE",nombre)//OJOJOJOJJO!!!!!!!!!SIN intent.
                putExtra("EDAD",edad)
            }
            startActivity(intent)
        }
    }
}