package com.example.rutas

import android.app.AlertDialog
import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.rutas.databinding.ActivityMainBinding
import model.Ruta
import viewModel.RutaViewModel

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val viewModel: RutaViewModel by viewModels()
    private lateinit var preferencias: SharedPreferences
    private val CANAL_ID = "aniadir_canal"
    companion object{
        const val PREFERENCIAS="preferencias"
        const val SELECCIONADO="seleccionado"
        const val IDETALLE="iDetalle"
        const val INICIADO="iniciado"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
  preferencias=getSharedPreferences(PREFERENCIAS, Context.MODE_PRIVATE)
val iniciado=preferencias.getBoolean(INICIADO,false)
        crearCanal()
        pedirPermiso()
        if(!iniciado){
            val rutaCares = Ruta(
                imagen = "cares",
                nombre = "Ruta del Cares",
                provincia = "León",
                dificultad = 3,
                latitud = 43.2640,
                longitud = -4.8260,
                web = "https://www.rutacares.org/"
            )
            val rutaSendaOso = Ruta(
                imagen = "oso",
                nombre = "Senda del Oso",
                provincia = "Asturias",
                dificultad = 2,
                latitud = 43.2500,
                longitud = -6.0000,
                web = "https://www.sendadeloso.org/"
            )

            val rutaLagosCovadonga = Ruta(
                imagen = "covadonga",
                nombre = "Lagos de Covadonga",
                provincia = "Asturias",
                dificultad = 2,
                latitud = 43.2733,
                longitud = -4.9869,
                web = "https://parquenacionalpicoseuropa.es/"
            )

            val rutaHocesDuraton = Ruta(
                imagen = "oces",
                nombre = "Hoces del Río Duratón",
                provincia = "Segovia",
                dificultad = 1,
                latitud = 41.3156,
                longitud = -3.8722,
                web = "https://patrimonionatural.org/"
            )
            val listaInicial=listOf<Ruta>(rutaCares,rutaHocesDuraton,rutaLagosCovadonga,rutaSendaOso)
            for(ruta in listaInicial){viewModel.insertarRuta(ruta)}
            preferencias.edit().putBoolean(INICIADO,true).apply()

        }
        val fragmentA= ListaFragment()
        supportFragmentManager.beginTransaction().replace(R.id.superior,fragmentA).commit()

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }
    fun idSeleccionado(id:Int){
        val fragmentB= DetalleFragment()
        val bundle=Bundle()
        bundle.putInt(IDETALLE,id)
        fragmentB.arguments=bundle
        supportFragmentManager.beginTransaction().replace(R.id.inferior,fragmentB).commit()

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.item_tool, menu)
        return true
    }
    private fun crearCanal() {
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {

            val canal = android.app.NotificationChannel(
                CANAL_ID,
                "aniadir_canal",
                android.app.NotificationManager.IMPORTANCE_DEFAULT
            )

            val manager = getSystemService(Context.NOTIFICATION_SERVICE) as android.app.NotificationManager
            manager.createNotificationChannel(canal)
        }
    }
    private fun pedirPermiso() {
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.TIRAMISU) {
            requestPermissions(arrayOf(android.Manifest.permission.POST_NOTIFICATIONS), 1)
        }
    }


    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {

            R.id.acercade -> {
                val builder: AlertDialog.Builder = AlertDialog.Builder(this)
                builder
                    .setMessage("Estas conociendo al desarrollador de la app")
                    .setTitle("The bug")
                    .setIcon(R.drawable.thebug)
                    .setPositiveButton("volver") { dialog, which ->
                        // Do something.
                    }

                val dialog: AlertDialog = builder.create()
                dialog.show()
                true}

            else ->  super.onOptionsItemSelected(item)//"Que se encargue Android"
        }
    }


}