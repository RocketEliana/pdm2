package com.example.tapasparquesol

import android.app.AlertDialog
import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.view.MenuItem
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.tapasparquesol.databinding.ActivityMainBinding
import com.example.tapasparquesol.model.Bar
import com.example.tapasparquesol.viewModelTapas.ViewModelBar

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
   private lateinit var preferencias: SharedPreferences
   private val viewModel: ViewModelBar by viewModels()
    private var idSeleccionado: Int = -1
    companion object{
        const val PREFERENCES="preferencias"
        const val SELECCIONADO="seleccionado"
        const val INICIADO="iniciado"

    }
    private val CANAL_ID = "borrado_canal"


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.toolbar)
        preferencias=getSharedPreferences(PREFERENCES, Context.MODE_PRIVATE)
        val iniciado=preferencias.getBoolean(INICIADO,false)

        crearCanal()
        pedirPermiso()




        if(!iniciado){
            val bar1 = Bar(
                nombre = "Bar El Rincón de Parquesol",
                direccion = "Calle Hernando de Acuña, 12",
                calificacion = 4,
                longitud = -4.7645,
                latitud = 41.6382,
                web = "https://elrincondeparquesol.es"
            )

            val bar2 = Bar(
                nombre = "Cervecería La Plaza",
                direccion = "Plaza Marcos Fernández, 5",
                calificacion = 5,
                longitud = -4.7662,
                latitud = 41.6391,
                web = "https://cervecerialaplaza.com"
            )

            val bar3 = Bar(
                nombre = "Bar Tapas ParqueSol",
                direccion = "Calle Ciudad de La Habana, 8",
                calificacion = 3,
                longitud = -4.7638,
                latitud = 41.6375,
                web = "https://tapaspqsol.com"
            )
            val listaInicial=listOf<Bar>(bar1,bar2,bar3)
            for(bar in listaInicial){viewModel.inserta(bar)}
            preferencias.edit().putBoolean(INICIADO,true).apply()
        }

        val f1=ListaFragment()
        supportFragmentManager.beginTransaction().add(R.id.superior,f1).commit()
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }
    fun recibeIdSpinner(id: Int) {

        idSeleccionado = id



        val f2 = DetalleFragment()
        val bundle = Bundle()
        bundle.putInt("id", id)
        f2.arguments = bundle
        supportFragmentManager.beginTransaction()
            .replace(R.id.inferior, f2)
            .commit()

    }
    private fun crearCanal() {
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {

            val canal = android.app.NotificationChannel(
                CANAL_ID,
                "Canal de borrado",
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
                    .setPositiveButton("volver") { dialog, which ->
                        // Do something.
                    }

                val dialog: AlertDialog = builder.create()
                dialog.show()
                true
            }

            else ->  super.onOptionsItemSelected(item)//"Que se encargue Android"
        }
    }

}