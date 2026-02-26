package com.example.loginpersonalizado

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
import com.example.loginpersonalizado.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    companion object{
        const val PREF_USER="prefUser"
        const val REGISTRADO="registrado"
        const val ALTA="alta"
    }
    private lateinit var binding: ActivityMainBinding
    private lateinit var pref_user: SharedPreferences
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        pref_user=getSharedPreferences(PREF_USER, Context.MODE_PRIVATE)
        val pref_registrado=pref_user.getBoolean(REGISTRADO,false)
        val pref_alta=pref_user.getBoolean(ALTA,false)
        setSupportActionBar(binding.toolbar)
        binding.acceso.setOnClickListener {
        if(pref_alta && pref_registrado){
            val intentPrincipal= Intent(this, MainActivity3::class.java)
            startActivity(intentPrincipal)
        }else{
            val intentSecundario=Intent(this, MainActivity2::class.java)
            startActivity(intentSecundario)
        }
        }

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_item, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.cerrar -> {
                cerrarSesion()
                true
            }

            R.id.acercade -> {
                Toast.makeText(this, "TheBug,2026", Toast.LENGTH_LONG).show()
            true}

            else ->  super.onOptionsItemSelected(item)//"Que se encargue Android"
        }
    }

    private fun cerrarSesion(): Boolean {
        pref_user.edit().putBoolean(REGISTRADO,false).apply()
        finish()
        return true
    }//<----Se espera un booleano, por lo que tiene que devolver un booleano
}