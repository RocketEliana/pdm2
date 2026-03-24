package com.example.rutas

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import com.example.rutas.databinding.FragmentListaBinding
import viewModel.RutaViewModel


class ListaFragment : Fragment()  {
    private var _binding: FragmentListaBinding? = null
    private lateinit var adapter: AdapterLista
    private val viewModel: RutaViewModel by activityViewModels  ()

private val binding get() = _binding!!
override fun onCreateView(
    inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
): View {
    _binding = FragmentListaBinding.inflate(inflater, container, false)
    return binding.root
}
override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)


viewModel.listado.observe (viewLifecycleOwner){
    rutaLista->
    adapter= AdapterLista(requireContext(),rutaLista)
    binding.lista.adapter=adapter
}

    binding.lista.setOnItemClickListener { _, _, pos, _ ->
        viewModel.listado.value?.getOrNull(pos)?.let { rutaSeleccionada ->
            (requireActivity() as? MainActivity)?.idSeleccionado(rutaSeleccionada.id)
        }
    }
    binding.addBtn.setOnClickListener {
        val fragmentNuevo= NuevoFragment()
        parentFragmentManager.beginTransaction().replace(R.id.listaFragment,fragmentNuevo).addToBackStack(null).commit()
    }
}
    /*binding.lista.setOnItemClickListener{_,_,pos,_->
        val rutaSeleccionada=viewModel.listado.value?.get(pos)
        rutaSeleccionada.let {
     }*/








override fun onDestroyView() {
    super.onDestroyView()
    _binding = null
}

}