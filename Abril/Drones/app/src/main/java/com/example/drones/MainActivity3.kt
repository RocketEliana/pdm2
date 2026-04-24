package com.example.drones

import android.Manifest
import android.R
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.widget.ListView
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.annotation.RequiresPermission
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.core.content.ContextCompat
import com.example.drones.databinding.ActivityMain3Binding
import model.Viaje
import viewModel.AppViewModel

class MainActivity3 : AppCompatActivity() {
    private lateinit var binding: ActivityMain3Binding
    private val viewModel: AppViewModel by viewModels()
    private var viaje: Viaje? = null
    private lateinit var adapter: AdapterListaViajes
    private val CANAL_ID = "mi_canal"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityMain3Binding.inflate(layoutInflater)
        setContentView(binding.root)
        crearCanal()
        pedirPermiso()
        adapter = AdapterListaViajes(this, mutableListOf())
        binding.listaViaje.adapter = adapter
        viewModel.listaViaje.observe(this) { lista ->
            adapter.actualizarLista(lista)
        }
        binding.listaViaje.choiceMode = ListView.CHOICE_MODE_SINGLE
        binding.listaViaje.setOnItemClickListener { parent, view, position, id ->
            binding.listaViaje.setItemChecked(position, true)
            viaje = adapter.getItem(position)


        }
        binding.borrar.setOnClickListener {
            if (viaje != null) {

                viewModel.eliminaViaje(viaje!!)
                if (ContextCompat.checkSelfPermission(
                        this,
                        Manifest.permission.POST_NOTIFICATIONS
                    ) == PackageManager.PERMISSION_GRANTED
                ) {
                    mostrarNotificacion()
                }
            } else {
                return@setOnClickListener
            }
        }
        binding.modidicar.setOnClickListener {
            if (viaje != null) {
                var idM = viaje?.id
                var intent = Intent(this, MainActivity2::class.java)
                intent.putExtra("idModificar", idM)
                startActivity(intent)
            } else {
                return@setOnClickListener
            }
        }


    }

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
                != PackageManager.PERMISSION_GRANTED
            ) {

                requestPermissions(
                    arrayOf(Manifest.permission.POST_NOTIFICATIONS),
                    1
                )
            }
        }
    }

    // 🔹 Mostrar notificación
    @RequiresPermission(Manifest.permission.POST_NOTIFICATIONS)
    private fun mostrarNotificacion() {
        val builder = NotificationCompat.Builder(this, CANAL_ID)
            .setSmallIcon(R.drawable.ic_dialog_info)
            .setContentTitle("Cambios en drones!")
            .setContentText("Viaje borrado")
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)

        NotificationManagerCompat.from(this).notify(1, builder.build())
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
    }
}