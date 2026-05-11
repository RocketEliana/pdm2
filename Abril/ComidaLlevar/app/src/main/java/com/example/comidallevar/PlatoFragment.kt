package com.example.comidallevar


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
import com.example.comidallevar.databinding.FragmentPlatoBinding
import viewModel.AppViewModel


class PlatoFragment : Fragment() {
    private var _binding: FragmentPlatoBinding? = null
    private val binding get() = _binding!!
    private val viewModel: AppViewModel by activityViewModels ()
    private var primeraVez = true
    private lateinit var preferencias: SharedPreferences
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentPlatoBinding.inflate(inflater, container, false)
        return binding.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        preferencias=requireContext().getSharedPreferences(MainActivity.PREFERENCES, Context.MODE_PRIVATE)
        val idGuardado=preferencias.getLong(MainActivity.ID_S,-1L)
        if(idGuardado != -1L){
            val res=viewModel.getById(idGuardado)
            val plato1=res?.plato1?.nombre
            val plato2=res?.plato2?.nombre
            val plato3=res?.plato3?.nombre
            val opciones = listOf(plato1, plato2, plato3)
            val adapter = ArrayAdapter(requireContext(),   android.R.layout.simple_spinner_item, opciones)
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            binding.spinnerPlato.adapter = adapter

            binding.spinnerPlato.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(parent: AdapterView<*>, view: View?, pos: Int, id: Long) {
                    if(primeraVez){
                        primeraVez=false
                        return
                    }
                    val plato = opciones[pos]

                    val direccion=binding.direccion.text.toString()
                    (requireActivity() as MainActivity2).insert(plato!!,direccion)
                }
                override fun onNothingSelected(parent: AdapterView<*>) {}
            }










        }

    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}