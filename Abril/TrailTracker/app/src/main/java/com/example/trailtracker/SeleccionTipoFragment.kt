package com.example.trailtracker

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.fragment.app.activityViewModels
import com.example.trailtracker.databinding.FragmentSeleccionTipoBinding
import view.AppViewModel


class SeleccionTipoFragment : Fragment() {
    private var _binding: FragmentSeleccionTipoBinding? = null
    private val binding get() = _binding!!
    private val viewModel: AppViewModel by activityViewModels ()
    private lateinit var preferencias: SharedPreferences
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSeleccionTipoBinding.inflate(inflater, container, false)
        return binding.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        preferencias=requireContext().getSharedPreferences(MainActivity.PREFERENCES, Context.MODE_PRIVATE)
        val opciones = listOf("senderismo", "escalada", "montaña")
        val adapter = ArrayAdapter(requireContext(),   android.R.layout.simple_spinner_item, opciones)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.spinnerTipo.adapter = adapter

        binding.spinnerTipo.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View?, pos: Int, id: Long) {
                val valor = opciones[pos]
                (requireActivity() as MainActivity2).irActividad(valor)
            }
            override fun onNothingSelected(parent: AdapterView<*>) {}
        }



    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}