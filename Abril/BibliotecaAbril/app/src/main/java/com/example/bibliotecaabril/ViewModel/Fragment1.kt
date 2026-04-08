package com.example.bibliotecaabril.ViewModel

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import androidx.fragment.app.activityViewModels
import com.example.bibliotecaabril.MainActivity
import com.example.bibliotecaabril.MainActivity.Companion.ID_FAVORITO
import com.example.bibliotecaabril.R
import com.example.bibliotecaabril.databinding.Fragment1Binding

class Fragment1 : Fragment() {
    private var _binding: Fragment1Binding? = null
    private val viewModel: AppViewModel by activityViewModels ()
    private lateinit var adapter: AdapterOrigenSpinner
    private val binding get() = _binding!!
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = Fragment1Binding.inflate(inflater, container, false)
        return binding.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val idOrigen=arguments?.getLong("idOrigenMain",-1) ?: -1L

        adapter = AdapterOrigenSpinner(requireContext(), mutableListOf())
        binding.spinnerDestino.adapter = adapter

        // Observar datos
        viewModel.listaBiblioteca.observe(viewLifecycleOwner) { lista ->
            val restantes=lista.filter { it.id != idOrigen }
            adapter.actualizarLista(restantes)
        }
        binding.spinnerDestino.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                val biblioteca = adapter.getItem(position)
                val idDestino = biblioteca?.id ?: -1L
                if (idDestino != -1L) {
                    (requireActivity() as MainActivity2).pasoPorId(idDestino)



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