package com.example.drones

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import androidx.fragment.app.activityViewModels
import com.example.drones.databinding.FragmentDestinoBinding
import viewModel.AppViewModel


class DestinoFragment : Fragment() {
    private var _binding: FragmentDestinoBinding? = null
    private var idRecibido:Long?=-1L
    private val viewModel: AppViewModel by activityViewModels ()
    private lateinit var adapter: AdapterSpinnerDestino
    private val binding get() = _binding!!
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDestinoBinding.inflate(inflater, container, false)
        return binding.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        idRecibido=arguments?.getLong("id",-1L)
        adapter= AdapterSpinnerDestino(requireContext(),mutableListOf())
        binding.spinnerDestino.adapter=adapter
        if(idRecibido != -1L){
            viewModel.listaInsti.observe(viewLifecycleOwner){
                lista->

                var listaDestino=lista.filter {it.id != idRecibido  }
                adapter.actualizarLista(listaDestino)

            }
            binding.spinnerDestino.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                    var insti = adapter.getItem(position)
                    insti?.let {

                          var idDestino=it.id
                            (requireActivity() as MainActivity2).maps(idDestino)
                        }


                    }


                override fun onNothingSelected(parent: AdapterView<*>?) {}
            }


        }

    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}