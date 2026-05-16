package com.example.sitioensayo

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.sitioensayo.databinding.ActivityMainBinding
import model.Sitio
import viewModel.AppViewModel

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private  lateinit var preferencias: SharedPreferences
    companion object {
        const val PREFERENCES = "preferences"
        const val ID_S = "idSeleccionado"
        const val INICIADO="iniciado"

    }

    private val viewModel: AppViewModel by viewModels ()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        preferencias=getSharedPreferences(PREFERENCES, Context.MODE_PRIVATE)
        val iniciado=preferencias.getBoolean(INICIADO,false)
        val sitiosPrueba = listOf(

           Sitio(
                nombre = "Café Central",
                direccion = "Calle Mayor 12",
                icono = 0,
                calificacion = 4.5f,
                telefono = "987123456",
                fecha = "15/05/2026",
                web = "www.cafecentral.com",
                descripcion = "Un café acogedor para desayunos y meriendas.",
                foto = "",
                latitud = 42.4627,
                longitud = -5.7485
            ),

            Sitio(
                nombre = "Parque del Lago",
                direccion = "Avenida del Lago s/n",
                icono = 0,
                calificacion = 4.0f,
                telefono = "987111222",
                fecha = "14/05/2026",
                web = "www.parquelago.com",
                descripcion = "Zona verde ideal para pasear y relajarse.",
                foto = "",
                latitud = 42.4635,
                longitud = -5.7492
            ),

            Sitio(
                nombre = "Pizzería Napoli",
                direccion = "Calle Italia 8",
                icono = 0,
                calificacion = 4.8f,
                telefono = "987333444",
                fecha = "10/05/2026",
                web = "www.napoli.com",
                descripcion = "Pizzas artesanales hechas al horno.",
                foto = "",
                latitud = 42.4601,
                longitud = -5.7460
            ),

            Sitio(
                nombre = "Biblioteca Municipal",
                direccion = "Plaza España 3",
                icono = 0,
                calificacion = 3.9f,
                telefono = "987555666",
                fecha = "01/05/2026",
                web = "www.biblioteca.com",
                descripcion = "Espacio tranquilo para estudiar y leer.",
                foto = "",
                latitud = 42.4619,
                longitud = -5.7478
            ),

            Sitio(
                nombre = "Gym Titan",
                direccion = "Calle Deporte 20",
                icono = 0,
                calificacion = 4.2f,
                telefono = "987888999",
                fecha = "03/05/2026",
                web = "www.gymtitan.com",
                descripcion = "Gimnasio completo con zona de musculación.",
                foto = "",
                latitud = 42.4597,
                longitud = -5.7501
            )
        )
        if(!iniciado){
            for(s in sitiosPrueba){
                viewModel.insertarSitio(s)
            }
            preferencias.edit().putBoolean(INICIADO,true).apply()
        }
        val idPreferido=preferencias.getLong(MainActivity.ID_S,-1L)
        if(idPreferido != -1L){
            val fragmento=DetalleFragment()
            val bundle=Bundle()
            bundle.putLong("id",idPreferido)
            fragmento.arguments=bundle

42
            supportFragmentManager.beginTransaction().replace(R.id.contenedor,fragmento ).commit()
        }else{
            supportFragmentManager.beginTransaction().replace(R.id.contenedor, ListaFragment()).commit()

        }


    }
}