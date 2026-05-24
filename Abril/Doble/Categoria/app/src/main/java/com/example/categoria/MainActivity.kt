package com.example.categoria

import android.content.ComponentName
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        val boton=findViewById<Button>(R.id.ir)
        val nombre= findViewById<EditText>(R.id.categoria)

        boton.setOnClickListener {

                val intent= Intent()
                intent.component= ComponentName(
                    "com.example.lwes",
                    "com.example.lwes.MainActivity"
                )
                intent.putExtra("cate",nombre.text.toString())
          
                val chooser= Intent.createChooser(intent,"Abrir con..")
                startActivity(chooser)


        }
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }
}