package com.example.rstaurantes

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.activityViewModels
import com.example.rstaurantes.databinding.FragmentNuevoBarBinding
import model.Bar
import viewModel.BarViewModel

class NuevoBar : Fragment() {
    private var _binding: FragmentNuevoBarBinding? = null
    private val viewModel: BarViewModel by activityViewModels()
    private val binding get() = _binding!!
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentNuevoBarBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.registro.setOnClickListener {

            val nombre=binding.nombreN.text.toString()
            val direccion=binding.direccionN.text.toString()
            val valoracion=binding.valoracionN.rating
            val web=binding.webN.text.toString()
            val longitud=binding.longitud.text.toString().toDoubleOrNull()
            val latitud=binding.lat.text.toString().toDoubleOrNull()
            val bar= Bar(nombre=nombre, direccion = direccion, valoracion = valoracion, latitud = latitud!!, longitud = longitud!!, web = web)
            if(viewModel.insertar(bar) != -1L){
                if (ContextCompat.checkSelfPermission(
                        requireContext(),
                        Manifest.permission.POST_NOTIFICATIONS
                    ) == PackageManager.PERMISSION_GRANTED
                ) {
                    (requireActivity() as MainActivity).mostrarNotificacion()
                }


                Toast.makeText(requireContext(),"Insertado", Toast.LENGTH_LONG).show()
            }else{ Toast.makeText(requireContext()," No Insertado", Toast.LENGTH_LONG).show()}
            parentFragmentManager.popBackStack()
        }


    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
