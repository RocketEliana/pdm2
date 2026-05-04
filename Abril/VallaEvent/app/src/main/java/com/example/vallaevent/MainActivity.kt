package com.example.vallaevent

import android.Manifest
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.content.SharedPreferences
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.annotation.RequiresPermission
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.example.vallaevent.databinding.ActivityMainBinding
import viewModel.AppViewModel


class MainActivity : AppCompatActivity() {
    private val viewModel: AppViewModel by viewModels ()
    private lateinit var binding: ActivityMainBinding
    private  lateinit var preferencias: SharedPreferences
    companion object{
        const val PREFERENCES="preferencias"
        const val TEMA="tema"
        const val LOGGEADO="loggeado"
        const val ID_SELECCIONADO="SELECCIONADO"
        const val INICIADO="iniciado"
    }
    private val CANAL_ID = "mi_canal"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        crearCanal()
        pedirPermiso()
        setSupportActionBar(binding.toolbar)
        preferencias=getSharedPreferences(PREFERENCES,Context.MODE_PRIVATE)
        val tema=preferencias.getString(TEMA,"system")//por defecto el de sistema ojo :  val mode = when (which) { 0 -> "light"; 1 -> "dark"; else -> "system" }
        applyTheme(tema!!)
        val logger=preferencias.getBoolean(LOGGEADO,false)
        if(!logger){
            supportFragmentManager.beginTransaction().replace(R.id.contenedorPincipal,
                RegistroFragment()).commit()
        }else{

            supportFragmentManager.beginTransaction().replace(R.id.contenedorPincipal,
                ListadoFragment()).commit()
        }




    }
    // 🔹 Crear canal (OBLIGATORIO desde Android 8)
    private fun crearCanal() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val canal = NotificationChannel(
                CANAL_ID,
                "Canal básico",
                NotificationManager.IMPORTANCE_DEFAULT
            )

            val manager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            manager.createNotificationChannel(canal)
        }
    }

    // 🔹 Pedir permiso (Android 13+)
    private fun pedirPermiso() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            if (checkSelfPermission(Manifest.permission.POST_NOTIFICATIONS)
                != PackageManager.PERMISSION_GRANTED) {

                requestPermissions(
                    arrayOf(Manifest.permission.POST_NOTIFICATIONS),
                    1
                )
            }
        }
    }

    // 🔹 Mostrar notificación
    @RequiresPermission(Manifest.permission.POST_NOTIFICATIONS)
     fun mostrarNotificacion() {
        val builder = NotificationCompat.Builder(this, CANAL_ID)
            .setSmallIcon(android.R.drawable.ic_dialog_info)
            .setContentTitle("Título")
            .setContentText("Mensaje de prueba")
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)

        NotificationManagerCompat.from(this).notify(1, builder.build())
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_item, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.modoApp -> {
                AlertDialog.Builder(this)
                    .setTitle("Tema")
                    .setItems(arrayOf("Claro", "Oscuro", "Sistema")) { _, which ->
                        val mode = when (which) { 0 -> "light"; 1 -> "dark"; else -> "system" }
                        applyTheme(mode)
                    }
                    .show()
                true
            }

            R.id.acercade -> {
                Toast.makeText(this, "TheBug,2026", Toast.LENGTH_LONG).show()
                true
            }

            else -> super.onOptionsItemSelected(item)//"Que se encargue Android"
        }
    }

    fun applyTheme(mode: String) {
        val nightMode = when (mode) {
            "light"  -> AppCompatDelegate.MODE_NIGHT_NO
            "dark"   -> AppCompatDelegate.MODE_NIGHT_YES
            else     -> AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM
        }
        AppCompatDelegate.setDefaultNightMode(nightMode)
        // Guardar para la próxima vez que abra la app
     preferencias
            .edit().putString(TEMA, mode).apply()
    }
}