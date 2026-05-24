package com.example.lwes

import android.content.SharedPreferences
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import com.example.lwes.databinding.FragmentListaBinding
import view.AppViewModel


class ListaFragment : Fragment() {
    private var _binding: FragmentListaBinding? = null
    private val binding get() = _binding!!
    private lateinit var adapter: AdapterLis
    private val viewModel: AppViewModel by activityViewModels ()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentListaBinding.inflate(inflater, container, false)
        return binding.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        adapter= AdapterLis(requireContext(),mutableListOf())
        binding.lista.adapter=adapter

    val cate=arguments?.getString("cate",null)
        if(cate==null){
            viewModel.listaLugar.observe(viewLifecycleOwner){
                lis->adapter.actualizarLista(lis)
            }
        }else{
            viewModel.getporcategoria(cate).observe(viewLifecycleOwner){
                lis->adapter.actualizarLista(lis)
            }
        }
        binding.addBtn.setOnClickListener {
            parentFragmentManager.beginTransaction().replace(R.id.contenedorPokemon, NuevoFragment()).addToBackStack(null).commit()
        }
    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}