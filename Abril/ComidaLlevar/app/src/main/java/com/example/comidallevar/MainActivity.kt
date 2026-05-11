package com.example.comidallevar

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.net.Uri
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.example.comidallevar.databinding.ActivityMainBinding
import model.Plato
import model.Restaurante
import viewModel.AppViewModel


class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var preferencias: SharedPreferences
    private val viewModel: AppViewModel by viewModels()
    private lateinit var adapter: AdapterListaRestaurantea

    companion object {
        const val PREFERENCES = "preferences"
        const val ID_S = "idSeleccionado"
        const val PRIMERAEJECUCION = "primera_ejecucion"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        preferencias = getSharedPreferences(PREFERENCES, Context.MODE_PRIVATE)
        var primeraEjecucion = preferencias.getBoolean(PRIMERAEJECUCION, false)
        var idPref = preferencias.getLong(ID_S, -1)

        if (!primeraEjecucion) {

            val restaurante1 = Restaurante(
                nombre = "Casa Robles",
                plato1 = Plato(nombre = "Gazpacho"),
                plato2 = Plato(nombre = "Pescaíto frito"),
                plato3 = Plato(nombre = "Torrijas"),
                telefono = "954213150",
                foto = R.drawable.sev,
                latitud = 37.3886,
                longitud = -5.9823
            )

            val restaurante2 = Restaurante(
                nombre = "La Pepica",
                plato1 = Plato(nombre = "Paella valenciana"),
                plato2 = Plato(nombre = "Fideuà"),
                plato3 = Plato(nombre = "Horchata con fartons"),
                telefono = "963710366",
                foto = R.drawable.va,
                latitud = 39.4699,
                longitud = -0.3763
            )

            val restaurante3 = Restaurante(
                nombre = "El Caballo de Troya",
                plato1 = Plato(nombre = "Lechazo asado"),
                plato2 = Plato(nombre = "Cecina de León"),
                plato3 = Plato(nombre = "Postre de la abuela"),
                telefono = "983334400",
                foto = R.drawable.vall,
                latitud = 41.6523,
                longitud = -4.7245
            )
            viewModel.insertarR(restaurante1)
            viewModel.insertarR(restaurante2)
            viewModel.insertarR(restaurante3)
            preferencias.edit().putBoolean(PRIMERAEJECUCION, true).apply()
        }
        adapter = AdapterListaRestaurantea(this, mutableListOf()) { telefono ->
            val intent = Intent(Intent.ACTION_DIAL).apply {
                data = Uri.parse("tel:$telefono")

            }
            startActivity(intent)

        }
        if (idPref != -1L) {
            val restaurante = viewModel.getById(idPref)
            restaurante?.let {
                binding.imgItem.setImageResource(it.foto)
            }
        }

        binding.listaOrigen.adapter = adapter
        viewModel.lista.observe(this) { lista ->
            adapter.actualizarLista(lista)

        }
        binding.listaOrigen.setOnItemClickListener { parent, view, position, id ->
            val res = adapter.getItem(position)
            val resId = res?.id ?: -1
            if (resId != -1L) {
                binding.imgItem.setImageResource(res?.foto!!)
                preferencias.edit().putLong(ID_S, resId).apply()
            }
        }

binding.imgItem.setOnClickListener {
    idPref=preferencias.getLong(ID_S,-1L)
    if(idPref != -1L){
        val intent=Intent(this, MainActivity2::class.java)
        startActivity(intent)

    }else{
        return@setOnClickListener
    }

}
    }
}
