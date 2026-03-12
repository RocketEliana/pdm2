package com.example.mp

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import com.example.mp.databinding.Fragment1Binding
import kotlinx.coroutines.launch
import ui.AdapterSpinner
import viewModel.ViewModelGira
import kotlin.math.roundToInt

class Fragment1 : Fragment() {
    private var _binding: Fragment1Binding? = null
    private val binding get() = _binding!!
    private lateinit var adapter: AdapterSpinner
    private val viewModel: ViewModelGira by activityViewModels ()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = Fragment1Binding.inflate(inflater, container, false)
        return binding.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val idRecibido=arguments?.getInt("id")
        val salaRecibida=viewModel.obtenerSalaPorId(idRecibido ?: -1)


        if(idRecibido != null){

            viewModel.listaSala.observe(viewLifecycleOwner){listaSegura->
                val listaSeleccionar=listaSegura.filter { it.id !=idRecibido }
                adapter= AdapterSpinner(requireContext(),listaSeleccionar)
                binding.spinner.adapter=adapter
            }
        }
        binding.spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                //el objeto que te devuelve adapter.getItem(position) es efectivamente un objeto de tipo Hada,
                val sala = adapter.getItem(position)


                sala?.let { it ->
                    val idSeleccion=it.id
                    (requireActivity() as MainActivity2).recibeSeleccionado(idSeleccion)


                    lifecycleScope.launch {
                        val longitud1 = it.longitud
                        val latitud1 = it.latitud
                        val longitud2 = salaRecibida?.longitud ?: 0.0
                        val latitud2 = salaRecibida?.latitud ?: 0.0

                        val distancia =
                            Haversine().calcularDistancia(latitud1, longitud1, latitud2, longitud2)
                        binding.distancia.text = "La distancia entre salas es : ${distancia.roundToInt()} km"
                    }//asegurate siempre de que controlas los nulos
                }
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {}
        }


    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}