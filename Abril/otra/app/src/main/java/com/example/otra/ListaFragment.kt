package com.example.otra

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import androidx.fragment.app.activityViewModels
import com.example.otra.databinding.FragmentListaBinding
import viewModelApp.AppViewModel


class ListaFragment : Fragment() {
    private var _binding: FragmentListaBinding? = null
    private val binding get() = _binding!!
    private val viewModel:AppViewModel by activityViewModels ()
    private lateinit var adapter: AdapterLista
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentListaBinding.inflate(inflater, container, false)
        return binding.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.listaEspectaculo.observe(viewLifecycleOwner){
            lista-> val segura=lista.let {
                adapter= AdapterLista(requireContext(),it)
            binding.lista.adapter=adapter
            }
        }
        // 1. Configurar el botón primero
        binding.addBtn.setOnClickListener {
            // Log de depuración para saber si el click llega
            println("DEBUG: Botón pulsado, intentando cambiar a NuevoFragment")

            parentFragmentManager.beginTransaction()
                .replace(R.id.main, NuevoFragment()) // ASEGÚRATE DE QUE ESTE ID ES EL DE LA ACTIVITY
                .addToBackStack(null)
                .commit()
        }
        binding.lista.onItemClickListener = AdapterView.OnItemClickListener { parent, view, position, id ->

            val espectaculos=viewModel.listaEspectaculo.value
            val espectaculo=espectaculos?.get(position)
            espectaculo?.let {
                it->var id=it.id
                (requireActivity() as Bienvenida).pasarDetalle(id)

            }

        }



    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


}