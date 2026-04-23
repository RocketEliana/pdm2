package com.example.mp

import android.Manifest
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.mp.databinding.ActivityMainBinding
import model.Sala
import ui.AdapterListaPrincipal
import viewModel.ViewModelGira

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var preferencias: SharedPreferences
    private lateinit var adapter: AdapterListaPrincipal
    private val viewModel: ViewModelGira by viewModels()
    private var telefonoPendiente: String? = null
    private val launcherLlamada = registerForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { concedido ->
        if (concedido) {
            telefonoPendiente?.let { llamar(it) }
        } else {
            Toast.makeText(this, "Permiso denegado", Toast.LENGTH_SHORT).show()
        }
        telefonoPendiente = null
    }

    // Lanza la llamada directamente (permiso ya garantizado)
    private fun llamar(telefono: String) {
        val intent = Intent(Intent.ACTION_CALL).apply {
            data = Uri.parse("tel:$telefono")
        }
        startActivity(intent)
    }

    private fun comprobarPermisoYLlamar(telefono: String) {
        val permiso = Manifest.permission.CALL_PHONE
        if (ContextCompat.checkSelfPermission(this, permiso) == PackageManager.PERMISSION_GRANTED) {
            llamar(telefono)
        } else {
            telefonoPendiente = telefono   // 👈 lo guardamos para usarlo tras el permiso
            launcherLlamada.launch(permiso)
        }
    }


    // Definimos el ID del canal de notificaciones
    private val ID_CANAL = "canal_notificaciones_01"

    // Constante para el código de solicitud del permiso de notificaciones
    private val REQUEST_CODE_NOTIFICATIONS = 1
    companion object{
        const val PREFERENCES="preferences"
        const val INICIADO="iniciado"
        const val SELECCIONADO="seleccionado"
        const val IDSALA="id"

    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.toolbar)
        crearCanalDeNotificaciones()

        // Verificar y solicitar el permiso POST_NOTIFICATIONS en tiempo de ejecución
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            // Comprueba si el permiso no ha sido concedido
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.POST_NOTIFICATIONS) != PackageManager.PERMISSION_GRANTED) {
                // Solicita el permiso al usuario
                ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.POST_NOTIFICATIONS), REQUEST_CODE_NOTIFICATIONS)
            }
        }
        preferencias = getSharedPreferences(PREFERENCES, Context.MODE_PRIVATE)
        val iniciado = preferencias.getBoolean(INICIADO, false)
        val imagenLogo = preferencias.getInt(SELECCIONADO, R.drawable.vc)




        binding.logo.setImageResource(imagenLogo)


        if (!iniciado) {
            val listaInicialSalas = listOf(
                Sala(
                    nombre = "La Rivera",
                    imagen = R.drawable.larivera,
                    latitud = 40.4129999,
                    longitud = -3.7221514,
                    telefono = "623338696"
                ),
                Sala(
                    nombre = "RazzMatazz",
                    imagen = R.drawable.razz,
                    latitud = 41.3977323,
                    longitud = 2.1911108,
                    telefono = "623338696"
                ),
                Sala(
                    nombre = "Copera",
                    imagen = R.drawable.copera,
                    latitud = 37.1303369,
                    longitud = -3.5837524,
                    telefono = "623338696"
                ),
            )
            for (lista in listaInicialSalas) {
                viewModel.insertarSala(lista)
            }
            preferencias.edit().putBoolean(INICIADO, true).apply()
        }
        viewModel.listaSala.observe(this) { lista ->
            adapter = AdapterListaPrincipal(this, lista) { telefono ->
                comprobarPermisoYLlamar(telefono)   // 👈 el callback conecta adapter con Main
            }
            binding.lista.adapter = adapter
        }
        binding.lista.setOnItemClickListener { _, _, i, _ ->

            val listaActual = viewModel.listaSala.value
            val salaSeleccionada = listaActual?.get(i).let { salaSegura ->
                val imagenLogo = salaSegura?.imagen
                val id = salaSegura?.id
                binding.logo.setImageResource(imagenLogo!!)
                preferencias.edit().putInt(SELECCIONADO, imagenLogo).apply()
                preferencias.edit().putInt(IDSALA, id ?: -1).apply()

            }
        }
        binding.logo.setOnClickListener {

            val idSeleccionado = preferencias.getInt(IDSALA, -1)

            if (idSeleccionado != -1) {
                val intent = Intent(this, MainActivity2::class.java)
                intent.putExtra("idSeleccion", idSeleccionado)
                startActivity(intent)
            } else {
                Toast.makeText(this, "Ha de seleccionar un instituto", Toast.LENGTH_LONG).show()
            }
        }
    }
    private fun crearCanalDeNotificaciones() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            // Definimos el nombre y la descripción del canal
            val nombre = "Canal de Alarmas"
            val descripcion = "Canal para las notificaciones de alarmas"
            val importancia = NotificationManager.IMPORTANCE_HIGH

            // Creamos el canal con el ID, nombre e importancia
            val canal = NotificationChannel(ID_CANAL, nombre, importancia).apply {
                description = descripcion
            }

            // Registramos el canal en el sistema
            val notificationManager: NotificationManager =
                getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(canal)
        }
    }

    /**
     * Método que se llama cuando el usuario responde a la solicitud de permisos
     */
    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == REQUEST_CODE_NOTIFICATIONS) {
            if ((grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED)) {
                // Permiso concedido
            } else {
                // Permiso denegado: informa al usuario que la aplicación no podrá mostrar notificaciones
                Toast.makeText(this, "Permiso de notificaciones denegado. La aplicación no podrá mostrar notificaciones.", Toast.LENGTH_SHORT).show()
            }
        }
    }
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_item, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.cerrar -> {

                true
            }

            R.id.acercade -> {
                Toast.makeText(this, "TheBug,2026", Toast.LENGTH_LONG).show()
                true}

            else ->  super.onOptionsItemSelected(item)//"Que se encargue Android"
        }
    }



}