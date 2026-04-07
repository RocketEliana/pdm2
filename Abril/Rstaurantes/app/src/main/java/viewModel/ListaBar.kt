package viewModel

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import com.example.rstaurantes.AdapterLista
import com.example.rstaurantes.MainActivity
import com.example.rstaurantes.NuevoBar
import com.example.rstaurantes.R
import com.example.rstaurantes.databinding.FragmentListaBarBinding


class ListaBar : Fragment() {
    private var _binding: FragmentListaBarBinding? = null
    private lateinit var adapter: AdapterLista
    private val viewModel: BarViewModel by activityViewModels ()
    private lateinit var preferencias: SharedPreferences
    private val binding get() = _binding!!
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentListaBarBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        preferencias=requireContext().getSharedPreferences(MainActivity.PREFERENCIAS, Context.MODE_PRIVATE)
        // Crear adapter una sola vez
        adapter = AdapterLista(requireContext(), mutableListOf())
        binding.lista.adapter = adapter

        // Observar datos
        viewModel.listaBar.observe(viewLifecycleOwner) { lista ->
            adapter.actualizarLista(lista)
        }
        binding.lista.setOnItemClickListener { parent, view, position, id ->

            val listabar=viewModel.listaBar.value
            val bar=listabar?.get(position)
            val id=bar?.let { it.id }
            preferencias.edit().putBoolean(MainActivity.INICIADO,true).apply()
            preferencias.edit().putLong(MainActivity.ID_FAVORITO,id ?: -1).apply()

            (requireActivity() as MainActivity).cargarIdLista(id ?: -1)
        }
        binding.addBtn.setOnClickListener { parentFragmentManager.beginTransaction().replace(R.id.containerA,
            NuevoBar()).addToBackStack(null).commit() }
    }



    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}