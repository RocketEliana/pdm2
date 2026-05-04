package com.example.vallaevent

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.example.vallaevent.databinding.FragmentListadoBinding
import model.Evento
import viewModel.AdapterSpinnerEvento
import viewModel.AppViewModel


class ListadoFragment : Fragment() {
    private var _binding: FragmentListadoBinding? = null
    private val binding get() = _binding!!
    private val viewModel: AppViewModel by activityViewModels()
    private lateinit var preferencias: SharedPreferences
    private lateinit var adapter: AdapterSpinnerEvento
    private var primeraEjecucion = true //OJO
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentListadoBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        preferencias =
            requireContext().getSharedPreferences(MainActivity.PREFERENCES, Context.MODE_PRIVATE)
        var idSeleccion = preferencias.getLong(MainActivity.ID_SELECCIONADO, -1)
        val iniciado=preferencias.getBoolean(MainActivity.INICIADO,false)
        if(!iniciado){
            val e1=Evento(
                nombre = "Concierto de Rock",
                fecha = "2026-06-10",
                tipo = "Música",
                icono = R.drawable.music,
                latitud = 40.4168,
                longitud = -3.7038,
                valoracion = 4.5f
            )
            val e2=Evento(
                nombre = "Partido de Fútbol",
                fecha = "2026-06-12",
                tipo = "Deporte",
                icono = R.drawable.dep,
                latitud = 41.3874,
                longitud = 2.1686,
                valoracion = 4.2f
            )
            val e3=Evento(
                nombre = "Cata de Té",
                fecha = "2026-06-15",
                tipo = "Gastronomía",
                icono = R.drawable.tea,
                latitud = 39.4699,
                longitud = -0.3763,
                valoracion = 4.8f
            )
            val e4= Evento(
                nombre = "Festival Indie",
                fecha = "2026-07-01",
                tipo = "Música",
                icono = R.drawable.music,
                latitud = 37.3891,
                longitud = -5.9845,
                valoracion = 4.6f
            )
            val e5=Evento(
                nombre = "Maratón Ciudad",
                fecha = "2026-07-05",
                tipo = "Deporte",
                icono = R.drawable.dep,
                latitud = 43.2630,
                longitud = -2.9350,
                valoracion = 4.3f
            )

            viewModel.insertaEvento(e1)
            viewModel.insertaEvento(e2)
            viewModel.insertaEvento(e3)
            viewModel.insertaEvento(e4)
            viewModel.insertaEvento(e5)
            preferencias.edit().putBoolean(MainActivity.INICIADO,true).apply()
        }
        if (idSeleccion == -1L) {
            binding.imagenInicio.setImageResource(R.drawable.ic_launcher_background)
        }
        adapter = AdapterSpinnerEvento(requireContext(), mutableListOf())
        binding.spinnerEventos.adapter = adapter
        viewModel.listaEvento.observe(viewLifecycleOwner) { lista ->
            adapter.actualizarLista(lista)
            if (idSeleccion != -1L) {
                val posicion =
                    lista.indexOfFirst { it.id == idSeleccion }
                //"búscame en la lista el evento cuyo id coincide con el que yo tenía guardado"
                //Si lo encuentra → te da su posición (0, 1, 2…)
                //Si no → devuelve -1

                if (posicion != -1) {
                    binding.spinnerEventos.setSelection(posicion)
                    binding.imagenInicio.setImageResource(lista[posicion].icono)
                }
            }

        }
        binding.spinnerEventos.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                if (primeraEjecucion) {
                    primeraEjecucion = false
                    return
                }

                val evento = adapter.getItem(position)
                evento?.let {
                    binding.imagenInicio.setImageResource(it.icono)
                    preferencias.edit().putLong(MainActivity.ID_SELECCIONADO, it.id).apply()
                }
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {}
        }
        binding.floatingActionButton2.setOnClickListener {
            parentFragmentManager.beginTransaction().
            replace(R.id.contenedorPincipal, NuevoFragment()).
            addToBackStack(null).commit()

        }


        binding.imagenInicio.setOnClickListener {
            idSeleccion=preferencias.getLong(MainActivity.ID_SELECCIONADO,-1L)
            if(idSeleccion !=-1L){
                parentFragmentManager.beginTransaction().replace(R.id.contenedorPincipal,
                    DetalleFragment()).addToBackStack(null).commit()
            }

            }


        }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}