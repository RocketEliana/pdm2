package com.example.ecorutas

import android.content.SharedPreferences
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import androidx.fragment.app.activityViewModels
import com.example.ecorutas.MainActivity.Companion.ID_S
import com.example.ecorutas.databinding.FragmentSuperiorBinding
import viewModel.AppViewModel


class SuperiorFragment : Fragment() {
    private var _binding: FragmentSuperiorBinding? = null
    private val binding get() = _binding!!
    private lateinit var adapter: AdapterSpinner
    private val viewModel: AppViewModel by activityViewModels ()
    private var primeraSeleccion: Boolean=true
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSuperiorBinding.inflate(inflater, container, false)
        return binding.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        // bundle.putLong("idOrigen",idOrigen)
        val idOrigen=arguments?.getLong("idOrigen",-1L)
        adapter= AdapterSpinner(requireContext(),mutableListOf())
        binding.spinnerDestino.adapter=adapter
        viewModel.listaEspacio.observe(viewLifecycleOwner){
            lista->val listaFinal=lista.filter { it.id != idOrigen }
            adapter.actualizarLista(listaFinal)
        }
        binding.spinnerDestino.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                if (primeraSeleccion) {
                    primeraSeleccion = false
                    return
                }

                val espacio = adapter.getItem(position)
                espacio?.let {
                    val id=it.id
                    (requireActivity() as MainActivity2).mapa(id)
                    (requireActivity() as MainActivity2).getIdDestino(id)

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