package com.example.loginpersonalizado

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.loginpersonalizado.databinding.ActivityMain3Binding

class MainActivity3 : AppCompatActivity() {
    private lateinit var binding: ActivityMain3Binding
    private lateinit var preferences: SharedPreferences
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityMain3Binding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.toolbar)
        preferences=getSharedPreferences(MainActivity.PREF_USER, Context.MODE_PRIVATE)
        val nombre=intent.getStringExtra("Entrenador")
        binding.toolbar.title=nombre.toString()


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
        else ->  super.onOptionsItemSelected(item)//"Que se encargue Android"
    }
}

private fun cerrarSesion(): Boolean {
    preferences.edit().putBoolean(MainActivity.REGISTRADO,false).apply()
    val intent= Intent(this, MainActivity::class.java)
    startActivity(intent)
    finish()
    return true
}
}

