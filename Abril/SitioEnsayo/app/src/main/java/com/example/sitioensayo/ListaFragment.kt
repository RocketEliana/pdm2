package com.example.sitioensayo

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Adapter
import androidx.fragment.app.activityViewModels
import com.example.sitioensayo.databinding.FragmentListaBinding
import viewModel.AppViewModel

class ListaFragment : Fragment() {
    private var _binding: FragmentListaBinding? = null
    private val binding get() = _binding!!
    private lateinit var adapter: AdapterLista
    private val viewModel: AppViewModel by activityViewModels ()
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
        adapter= AdapterLista(requireContext(),mutableListOf())
        binding.lista.adapter=adapter
        viewModel.listaSitio.observe(viewLifecycleOwner){
            lis->adapter.actualizarLista(lis)
        }
        binding.lista.setOnItemClickListener { parent, view, position, id ->
            val sitio = adapter.getItem(position)
            sitio?.let {
                val id=it.id
                preferencias.edit().putLong(MainActivity.ID_S,id).apply()
                val fragmento=DetalleFragment()
                val bundle=Bundle()
                bundle.putLong("id",id)
                fragmento.arguments=bundle


                parentFragmentManager.beginTransaction().replace(R.id.contenedor,fragmento ).commit()
            }

        }
        binding.addBtn.setOnClickListener {
            parentFragmentManager.beginTransaction().replace(R.id.contenedor, NuevoFragment()).addToBackStack(null).commit()
        }


    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}