package com.example.otra

import android.icu.text.SimpleDateFormat
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import com.example.otra.databinding.FragmentDetalleBinding
import viewModelApp.AppViewModel
import java.util.Date
import java.util.Locale


class DetalleFragment : Fragment() {

    private var _binding: FragmentDetalleBinding? = null
    private val viewModel: AppViewModel by activityViewModels ()
    private val binding get() = _binding!!
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDetalleBinding.inflate(inflater, container, false)
        return binding.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val id = arguments?.getLong("id") ?: -1L
        if (id != -1L) {
            val espectaculo = viewModel.getEspectaculoById(id)
            espectaculo?.let {
                binding.nombreDetalle.text = it.nombre
                binding.imagenDetalle.setImageResource(it.icono)


    }
            }
        }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}