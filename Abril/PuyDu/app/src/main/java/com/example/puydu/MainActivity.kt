package com.example.puydu

import android.content.ComponentName
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.KeyEventDispatcher
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        val boton=findViewById<Button>(R.id.ir)
        val nombre= findViewById<EditText>(R.id.nombre)
        val contrasenia=findViewById<EditText>(R.id.contrasenia)
        boton.setOnClickListener {
            if(!nombre.text.toString().isEmpty() && !contrasenia.text.toString().isEmpty()){
            val intent= Intent()
                intent.component= ComponentName(
                    "com.example.otra",
                    "com.example.otra.MainActivity"
                )
            intent.putExtra("nombre",nombre.text.toString())
            intent.putExtra("contrasenia",contrasenia.text.toString())
            val chooser= Intent.createChooser(intent,"Abrir con..")
            startActivity(chooser)
            }else{
                Toast.makeText(this,"Rellene los campos", Toast.LENGTH_LONG).show()
            }

        }
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }
}