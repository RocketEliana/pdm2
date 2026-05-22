package com.example.ecorutas

import android.Manifest
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
import com.example.ecorutas.databinding.ActivityMain3Binding
import model.Viaje
import viewModel.AppViewModel



class MainActivity3 : AppCompatActivity() {
    private lateinit var binding: ActivityMain3Binding
    private lateinit var adapter: AdapterLista
    private var viaje: Viaje? = null
    private val CANAL_ID = "mi_canal"


    private val viewModel: AppViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityMain3Binding.inflate(layoutInflater)
        setContentView(binding.root)
        crearCanal()
        pedirPermiso()
        adapter = AdapterLista(this, mutableListOf())
        binding.lista.adapter = adapter
        viewModel.listaViaje.observe(this) { lis ->
            adapter.actualizarLista(lis)
        }
        binding.lista.setOnItemClickListener { parent, view, position, id ->
            viaje = adapter.getItem(position)

        }
        binding.lista.choiceMode = ListView.CHOICE_MODE_SINGLE
        binding.volver.setOnClickListener {
            //aqui deberias meter preferencias para recuperar el origen
            val intent = Intent(this, MainActivity2::class.java)
            startActivity(intent)
        }
        binding.borrar.setOnClickListener {
            if (viaje == null) {
                return@setOnClickListener
            }
            viewModel.eliminaViaje(viaje!!)
            if (ContextCompat.checkSelfPermission(
                    this,
                    Manifest.permission.POST_NOTIFICATIONS
                ) == PackageManager.PERMISSION_GRANTED
            ) {
             mostrarNotificacion()
            }


            }
            /* if(viewModel.insertar(bar) != -1L){
                if (ContextCompat.checkSelfPermission(
                        requireContext(),
                        Manifest.permission.POST_NOTIFICATIONS
                    ) == PackageManager.PERMISSION_GRANTED
                ) {
                    (requireActivity() as MainActivity).mostrarNotificacion()
                }*/


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

    @RequiresPermission(android.Manifest.permission.POST_NOTIFICATIONS)
    fun mostrarNotificacion() {
        val builder = NotificationCompat.Builder(this, CANAL_ID)
            .setSmallIcon(android.R.drawable.ic_dialog_info)
            .setContentTitle("Título")
            .setContentText("Viaje eliminado")
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)

        NotificationManagerCompat.from(this).notify(1, builder.build())
    }

}

