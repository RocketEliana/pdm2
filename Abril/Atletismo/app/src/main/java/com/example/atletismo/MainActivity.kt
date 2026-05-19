package com.example.atletismo

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.helper.widget.Carousel
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.atletismo.databinding.ActivityMainBinding
import model.Categoria
import model.Competicion
import viewModel.AppViewModel

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private  lateinit var preferencias: SharedPreferences
    companion object {
        const val PREFERENCES = "preferences"
        const val ID_S = "idSeleccionado"
        const val PRIMERAEJECUCION = "primera_ejecucion"

    }
    private lateinit var adapter: AdapterLista

    private val viewModel: AppViewModel by viewModels ()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        preferencias=getSharedPreferences(PREFERENCES, Context.MODE_PRIVATE)
        val iniciado=preferencias.getBoolean(PRIMERAEJECUCION,false)

        if(!iniciado){
            val listaSitios = listOf(
                Competicion(
                    nombre="Museo del Prado",
                    correo = "info@museodelprado.es",
                    imagen = R.drawable.c1,
                    latitud = 40.413780,
                    longitud = -3.692127
                ),
                Competicion(
                    nombre = "Sagrada Familia",
                    correo = "info@sagradafamilia.org",
                    imagen = R.drawable.c2,
                    latitud = 41.403629,
                    longitud = 2.174356
                ),
                Competicion(
                    nombre = "Torre Eiffel",
                  correo =   "contact@toureiffel.paris",
                    imagen = R.drawable.c3,
                    latitud = 48.858370,
                    longitud = 2.294481
                )
            )
            for(c in listaSitios){viewModel.insertarCompeticion(c)}
            val categoria1 = Categoria(
                nombre = "Absoluta",
                latitud = 40.416775,
                longitud = -3.703790 // Madrid
            )

            val categoria2 = Categoria(
                nombre = "Sub23",
                latitud = 41.385064,
                longitud = 2.173404 // Barcelona
            )

            val categoria3 = Categoria(
                nombre = "Cadete",
                latitud = 39.469907,
                longitud = -0.376288 // Valencia
            )

            val categoria4 = Categoria(
                nombre = "Juvenil",
                latitud = 37.389092,
                longitud = -5.984459 // Sevilla
            )
            viewModel.insertarCategoria(categoria1 )
            viewModel.insertarCategoria(categoria2)
            viewModel.insertarCategoria(categoria3)
            viewModel.insertarCategoria(categoria4)

            preferencias.edit().putBoolean(PRIMERAEJECUCION,true).apply()
        }
        var id_favorito=preferencias.getLong(ID_S,-1L)
        if(id_favorito!=-1L){
            val competicion=viewModel.getCompeticionPorId(id_favorito)
            competicion?.let {  binding.imgItem.setImageResource(it.imagen) }

        }
        adapter= AdapterLista(this,mutableListOf())
        binding.listaOrigen.adapter=adapter
        viewModel.getAllCompeticion.observe(this){
            lis->adapter.actualizarLista(lis)
        }
        binding.listaOrigen.setOnItemClickListener { parent, view, position, id ->
            val competicion = adapter.getItem(position)
            competicion?.let{
                val id=it.id
                preferencias.edit().putLong(ID_S,id).apply()
                binding.imgItem.setImageResource(it.imagen)
            }
        }
        binding.imgItem.setOnClickListener {
            id_favorito=preferencias.getLong(ID_S,-1L)
            if(id_favorito!=-1L){
                val intent= Intent(this, MainActivity2::class.java)
                intent.putExtra("id",id_favorito)
                startActivity(intent)
            }else{
                return@setOnClickListener
            }

        }


    }
}