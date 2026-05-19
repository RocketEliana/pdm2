package com.example.atletismo


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.activityViewModels
import com.example.atletismo.databinding.FragmentSuperiorBinding
import viewModel.AppViewModel


class SuperiorFragment : Fragment() {
    private var _binding: FragmentSuperiorBinding? = null
    private val binding get() = _binding!!
    private lateinit var adapterC: AdapterCategoria
    private var prueba:String=""
    private var nombre:String=""
    private var id_cate:Long=-1L
    private var dorsal: Int=0;
    private val viewModel: AppViewModel by activityViewModels ()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSuperiorBinding.inflate(inflater, container, false)
        return binding.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val opciones = listOf("100m", "400m", "500m", "maraton")
        val adapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, opciones)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.prueba.adapter = adapter
        adapterC = AdapterCategoria(requireContext(), mutableListOf())
        binding.categoria.adapter = adapterC
        viewModel.getAllCategoria.observe(viewLifecycleOwner) { lista ->

            val listaFiltrada = lista.filter { categoria ->

                val inscritos = viewModel.atletasCategoria(categoria.id)

                inscritos < 8
            }

            adapterC.actualizarLista(listaFiltrada)
        }

        binding.nombre.doAfterTextChanged {
            nombre = binding.nombre.text.toString()
            (requireActivity() as MainActivity2).nuevoAtleta(nombre, dorsal, id_cate, prueba)
        }

        binding.numero.doAfterTextChanged {
            dorsal = binding.numero.text.toString().toIntOrNull() ?: 0
            (requireActivity() as MainActivity2).nuevoAtleta(nombre, dorsal, id_cate, prueba)
        }

        binding.prueba.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View?, pos: Int, id: Long) {
                prueba = opciones[pos]
                (requireActivity() as MainActivity2).nuevoAtleta(nombre, dorsal, id_cate, prueba)
            }
            override fun onNothingSelected(parent: AdapterView<*>) {}
        }

        binding.categoria.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                val cate = adapterC.getItem(position)
                cate?.let {
                    id_cate = it.id
                    (requireActivity() as MainActivity2).nuevoAtleta(nombre, dorsal, id_cate, prueba)
                    (requireActivity() as MainActivity2).categoriaMaps( id_cate)

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