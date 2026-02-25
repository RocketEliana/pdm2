package com.example.preferenciasplataforma

import android.content.Context
import android.os.Bundle
import android.widget.ArrayAdapter
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.preferenciasplataforma.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    companion object {//para llamar fuera val prefs = getSharedPreferences(MainActivity.PREFS_NAME_SETTINGS, Context.MODE_PRIVATE)
        // Nombre del archivo SharedPreferences (puedo tener más de uno)
        const val PREFS_NAME_PERFIL = "user_profile_prefs"
        const val PREFS_NAME_SETTINGS="app_settings_prefs"
        const val NOTIFICACION_KEY = "notificaciones" // Clave
        const val AHORRO_KEY="ahorro"
        const val TEMA_KEY="tema"
        const val NOMBRE_KEY="nombre"

    }

    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val prefConfiguracion = getSharedPreferences(PREFS_NAME_SETTINGS, Context.MODE_PRIVATE)
        val prefsPerfil = getSharedPreferences(PREFS_NAME_PERFIL, Context.MODE_PRIVATE)

        // --- 1. CARGAR DATOS Y MOSTRARLOS EN LA UI ---
        // Si no haces esto, los campos siempre aparecerán vacíos al abrir
        binding.nombre.setText(prefsPerfil.getString(NOMBRE_KEY, ""))
        binding.notificaciones.isChecked = prefConfiguracion.getBoolean(NOTIFICACION_KEY, false)
        binding.ahorro.isChecked = prefConfiguracion.getBoolean(AHORRO_KEY, false)

        val listaTemas = listOf("Oscuro", "Claro", "Sistema")
        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, listaTemas)
        binding.tema.adapter = adapter

        val temaGuardado = prefConfiguracion.getString(TEMA_KEY, "Claro")
        binding.tema.setSelection(listaTemas.indexOf(temaGuardado))

        // --- 2. BOTÓN GUARDAR ---
        binding.guardar.setOnClickListener {
            val nombre = binding.nombre.text.toString()
            val tema = binding.tema.selectedItem.toString()

            prefsPerfil.edit().putString(NOMBRE_KEY, nombre).apply()

            // Puedes encadenar edits para que sea más eficiente
            prefConfiguracion.edit()
                .putBoolean(NOTIFICACION_KEY, binding.notificaciones.isChecked)
                .putBoolean(AHORRO_KEY, binding.ahorro.isChecked)
                .putString(TEMA_KEY, tema)
                .apply()
        }

        // --- 3. BOTÓN RESTABLECER ---
        binding.restablecer.setOnClickListener {
            // Borramos los datos
            prefsPerfil.edit().clear().apply()
            prefConfiguracion.edit().clear().apply()

            // ¡IMPORTANTE! Actualizamos la interfaz para que el usuario lo vea
            binding.nombre.setText("")
            binding.notificaciones.isChecked = false
            binding.ahorro.isChecked = false
            binding.tema.setSelection(1) // Selecciona "Claro"
        }
    }
}