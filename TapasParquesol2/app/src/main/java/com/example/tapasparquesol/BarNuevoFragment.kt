package com.example.tapasparquesol

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import com.example.tapasparquesol.databinding.FragmentBarNuevoBinding
import com.example.tapasparquesol.model.Bar
import com.example.tapasparquesol.viewModelTapas.ViewModelBar


class BarNuevoFragment : Fragment() {
    private var _binding: FragmentBarNuevoBinding? = null
    private val binding get() = _binding!!
    private val viewModel: ViewModelBar by activityViewModels()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentBarNuevoBinding.inflate(inflater, container, false)
        return binding.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.aniadir.setOnClickListener {
            val nombre=binding.nombre.text.toString()
            val direccion=binding.direccion.text.toString()
            val calificacion=binding.valoracion.rating.toInt()
            val longitud = binding.longitud.text.toString().toDoubleOrNull() ?: 0.0
            val latitud = binding.latitud.text.toString().toDoubleOrNull() ?: 0.0
            val web=binding.web.text.toString()
            val bar= Bar(
                nombre = nombre,
                direccion = direccion,
                calificacion = calificacion,
                longitud = longitud,
                latitud = latitud,
                web = web
            )
            viewModel.inserta(bar)
            parentFragmentManager.popBackStack()

        }

    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}