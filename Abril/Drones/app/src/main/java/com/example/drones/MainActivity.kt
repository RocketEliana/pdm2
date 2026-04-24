package com.example.drones

import android.Manifest
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.drones.databinding.ActivityMainBinding
import model.Instituto
import viewModel.AppViewModel

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var preferencias: SharedPreferences
    private var instituto: Instituto?=null
    companion object{
        const val PREFERENCIAS="preferencias"
        const val INICIADO="iniciado"
        const val ID_SELECCIONADO="id_seleccionado"
    }
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

    // Comprueba permiso y llama, o pide permiso guardando el número
    private fun comprobarPermisoYLlamar(telefono: String) {
        val permiso = Manifest.permission.CALL_PHONE
        if (ContextCompat.checkSelfPermission(this, permiso) == PackageManager.PERMISSION_GRANTED) {
            llamar(telefono)
        } else {
            telefonoPendiente = telefono   // lo guardamos para usarlo tras el permiso
            launcherLlamada.launch(permiso)
        }
    }
    private val viewModel: AppViewModel by viewModels ()
    private lateinit var adapter: AdapterOrigen
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        preferencias = getSharedPreferences(PREFERENCIAS, Context.MODE_PRIVATE)
        val iniciado = preferencias.getBoolean(INICIADO, false)
        var idSeleccionado = preferencias.getLong(ID_SELECCIONADO, -1)
        if (!iniciado) {
            val intent = Intent(this, Video::class.java)
            startActivity(intent)
            val i1 = Instituto(
                nombre = "Valladolid",
                telefono = "623338696",
                icono = R.drawable.va,
                longitud = -4.7235,
                latitud = 41.6554
            )
            val i2 = Instituto(
                nombre = "Zamora",
                telefono = "623338696",
                icono = R.drawable.za,
                longitud = -5.74456,
                latitud = 41.50633
            )
            val i3 = Instituto(
                nombre = "Soria",
                telefono = "623338696",
                icono = R.drawable.so,
                longitud = -2.46472,  // coordenadas reales de Soria
                latitud = 41.76442
            )

            viewModel.insertaInsti(i1)
            viewModel.insertaInsti(i2)
            viewModel.insertaInsti(i3)
            preferencias.edit().putBoolean(INICIADO, true).apply()

        }
        adapter = AdapterOrigen(this, mutableListOf()) { telefono ->
            comprobarPermisoYLlamar(telefono)
        }
            binding.listaOrigen.adapter = adapter
            viewModel.listaInsti.observe(this) { lista ->
                adapter.actualizarLista(lista)

            }
            if (idSeleccionado != -1L) {
                instituto = viewModel.instiPorId(idSeleccionado)
                instituto?.let {
                    val imagen = it.icono
                    binding.imgItem.setImageResource(imagen)
                }


            }
            binding.listaOrigen
                .setOnItemClickListener { parent, view, position, id ->
                    instituto = adapter.getItem(position)
                    instituto?.let {
                        val id = it.id
                        binding.imgItem.setImageResource(it.icono)
                        preferencias.edit().putLong(ID_SELECCIONADO, id).apply()
                    }

                }
            binding.imgItem.setOnClickListener {
                idSeleccionado = preferencias.getLong(ID_SELECCIONADO, -1L)
                if (idSeleccionado != -1L) {
                    val intent = Intent(this, MainActivity2::class.java)
                    startActivity(intent)
                } else {
                    return@setOnClickListener
                }

            }

        }

}
