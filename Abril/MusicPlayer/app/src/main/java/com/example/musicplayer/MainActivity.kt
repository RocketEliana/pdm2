package com.example.musicplayer

import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.musicplayer.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
        private lateinit var binding: ActivityMainBinding
        private lateinit var preferencias: SharedPreferences
        companion object{
            const val PREFERENCIAS="preferencias"
            const val ID_REGISTRADO="id_registrado"
        }

        override fun onCreate(savedInstanceState: Bundle?) {
            super.onCreate(savedInstanceState)
            enableEdgeToEdge()
            binding = ActivityMainBinding.inflate(layoutInflater)
            setContentView(binding.root)
            setSupportActionBar(binding.toolbar)
            preferencias=getSharedPreferences(PREFERENCIAS, Context.MODE_PRIVATE)

            binding.button.setOnClickListener {
                val id_registrado=preferencias.getLong(ID_REGISTRADO,-1L)
                if (id_registrado != -1L) {
                    val intent = Intent(this, Principal::class.java)
                    intent.putExtra("id", id_registrado)
                    startActivity(intent)
                } else {
                    val intentP = Intent(this, MainActivity2::class.java)
                    startActivity(intentP)

                }
            }

        }
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.item_toolbar, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {

            R.id.acercade -> {
                val builder: AlertDialog.Builder = AlertDialog.Builder(this)
                builder
                    .setMessage("Estas conociendo al desarrollador de la app")
                    .setTitle("The bug")
                    .setIcon(R.drawable.mu)
                    .setPositiveButton("Encantada!!!") { dialog, which ->
                        // Do something.
                    }

                val dialog: AlertDialog = builder.create()
                dialog.show()
                true
            }

            else ->  super.onOptionsItemSelected(item)//"Que se encargue Android"
        }
    }



    }