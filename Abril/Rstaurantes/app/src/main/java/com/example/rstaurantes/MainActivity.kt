
package com.example.rstaurantes

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
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.rstaurantes.databinding.ActivityMainBinding
import viewModel.BarViewModel
import viewModel.DetalleBar
import viewModel.ListaBar

class MainActivity : AppCompatActivity() {
    private lateinit var preferencias: SharedPreferences
    companion object{
        const val PREFERENCIAS="preferencias"
        const val INICIADO="iniciado"
        const val ID_FAVORITO="idfAVORITO"
        private val CANAL_ID = "mi_canal"
    }
    private val viewModel: BarViewModel by viewModels ()
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        crearCanal()
        pedirPermiso()
        setSupportActionBar(binding.toolbar)
        preferencias=getSharedPreferences(PREFERENCIAS, Context.MODE_PRIVATE)
        val iniciado=preferencias.getBoolean(INICIADO,false)
        if(iniciado){cargarIdLista(preferencias.getLong(ID_FAVORITO,-1L))}

        supportFragmentManager.beginTransaction().replace(R.id.containerA, ListaBar()).commit()

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }
    fun cargarIdLista(id:Long){
        if(id != -1L) {
            val fragmento = DetalleBar()
            val bundle = Bundle()
            bundle.putLong("idLista", id)
            fragmento.arguments = bundle
            supportFragmentManager.beginTransaction().replace(R.id.containerB, fragmento).commit()


        }
    }
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.item_toolbar, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {

            R.id.acercade -> {
                Toast.makeText(this, "TheBug,2026", Toast.LENGTH_LONG).show()
                true}

            else ->  super.onOptionsItemSelected(item)//"Que se encargue Android"
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
            .setSmallIcon(R.drawable.res)
            .setContentTitle("Título")
            .setContentText("Bar insertado")
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)

        NotificationManagerCompat.from(this).notify(1, builder.build())
    }


}
