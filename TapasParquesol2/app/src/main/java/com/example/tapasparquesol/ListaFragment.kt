package com.example.tapasparquesol

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import androidx.fragment.app.activityViewModels
import com.example.tapasparquesol.databinding.FragmentListaBinding
import com.example.tapasparquesol.model.Bar
import com.example.tapasparquesol.viewModelTapas.ViewModelBar

class ListaFragment : Fragment() {
    private var _binding: FragmentListaBinding? = null
    private val binding get() = _binding!!
    private val viewModel: ViewModelBar by activityViewModels()
    private lateinit var adapter: BarAdapterSpinner
    private lateinit var preferencias: SharedPreferences


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentListaBinding.inflate(inflater, container, false)
        return binding.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        preferencias=requireContext().getSharedPreferences(MainActivity.PREFERENCES, Context.MODE_PRIVATE)
        var listaBares=viewModel.listado.observe(viewLifecycleOwner){
            lista->adapter= BarAdapterSpinner(requireContext(),lista)
            binding.barSpinner.adapter=adapter


            val idGuardado = preferencias.getInt(MainActivity.SELECCIONADO, -1)
            if (idGuardado != -1) {
                val posicion = lista.indexOfFirst { it.id == idGuardado }
                if (posicion != -1) {
                    binding.barSpinner.setSelection(posicion)
                }
            }

        }
        binding.barSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                //el objeto que te devuelve adapter.getItem(position) es efectivamente un objeto de tipo Hada,
                val bar = adapter.getItem(position)
                val id=bar?.id ?: -1
                if(id != -1){
                    preferencias.edit().putInt(MainActivity.SELECCIONADO, id).apply()
                    (requireActivity() as MainActivity).recibeIdSpinner(id)


                }

            }


            override fun onNothingSelected(parent: AdapterView<*>?) {}
        }

        binding.add.setOnClickListener {
            val fragment= BarNuevoFragment()
            parentFragmentManager.beginTransaction().replace(R.id.fragmentLista,fragment).addToBackStack(null).commit()
        }





    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}