package com.example.lwes

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.example.lwes.databinding.ActivityMainBinding
import view.AppViewModel
import java.util.Locale

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var preferencias: SharedPreferences

    companion object {
        const val PREFERENCES = "preferences"
        const val ID_S = "idSeleccionado"
        const val PRIMERAEJECUCION = "primera_ejecucion"
    }

    private val viewModel: AppViewModel by viewModels()

    override fun attachBaseContext(newBase: Context) {
        val prefs = newBase.getSharedPreferences("settings", Context.MODE_PRIVATE)
        val lang = prefs.getString("language", "es") ?: "es"

        val locale = Locale(lang)
        Locale.setDefault(locale)
        val config = newBase.resources.configuration
        config.setLocale(locale)
        val context = newBase.createConfigurationContext(config)

        super.attachBaseContext(context)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.toolbar) // asegúrate de que tu toolbar se llama así en el XML

        preferencias = getSharedPreferences(PREFERENCES, Context.MODE_PRIVATE)

        val cate = intent.getStringExtra("cate")
        if (cate == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.contenedorPokemon, ListaFragment()).commit()
        } else {
            val fragment = ListaFragment()
            val bundle = Bundle()
            bundle.putString("cate", cate)
            fragment.arguments = bundle
            supportFragmentManager.beginTransaction()
                .replace(R.id.contenedorPokemon, fragment).commit()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.item_toolbar, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.action_language) {
            val prefs = getSharedPreferences("settings", Context.MODE_PRIVATE)
            val currentLang = prefs.getString("language", "es") ?: "es"
            val newLang = if (currentLang == "es") "en" else "es"
            prefs.edit().putString("language", newLang).apply()
            recreate()
            return true
        }
        return super.onOptionsItemSelected(item)
    }
}