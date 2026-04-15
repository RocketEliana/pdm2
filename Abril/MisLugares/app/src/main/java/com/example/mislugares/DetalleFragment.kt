package com.example.mislugares

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.example.mislugares.databinding.FragmentDetalleBinding
import com.example.mislugares.viewModel.AppViewModel

class DetalleFragment : Fragment() {
    private var _binding: FragmentDetalleBinding? = null
    private val viewModel: AppViewModel by activityViewModels()
    private val binding get() = _binding!!

    private val launcherLlamada = registerForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { isGranted ->
        if (isGranted) {
            Toast.makeText(requireContext(), "Permiso de llamadas concedido", Toast.LENGTH_SHORT)
                .show()
            hacerLlamada(binding.telefonoD.text.toString())
        } else {
            Toast.makeText(requireContext(), "Permiso de llamadas denegado", Toast.LENGTH_SHORT)
                .show()
        }
    }

    private fun comprobarPermisoLlamada() {
        val permiso = Manifest.permission.CALL_PHONE
        when {
            ContextCompat.checkSelfPermission(
                requireContext(),
                permiso
            ) == PackageManager.PERMISSION_GRANTED -> {
                hacerLlamada(binding.telefonoD.text.toString())
            }

            else -> {
                launcherLlamada.launch(permiso)
            }
        }
    }

    private fun hacerLlamada(telefono: String) {
        val intent = Intent(Intent.ACTION_CALL).apply {
            data = Uri.parse("tel:$telefono")
        }
        startActivity(intent)
    }


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
            val lugar = viewModel.getPorId(id)
            lugar?.let {
                binding.nombreD.text = it.nombre.toString()
                binding.tipoLugarD.setImageResource(it.tipoIncon)
                binding.direccionD.text = it.direccion.toString()
                binding.telefonoD.text = it.telefono.toString()
                binding.webD.text = it.web.toString()
                binding.descripcionD.text = it.descripcion.toString()
                binding.fechaHoraD.text = it.fecha.toString()
                binding.imagenD.setImageURI(Uri.parse(it.foto))
                binding.icD.setImageResource(it.tipoIncon)
            }
            binding.telefonoD.setOnClickListener {
                comprobarPermisoLlamada()
            }
            binding.webD.setOnClickListener {
                val web = binding.webD.text.toString().trim()
                if (web.isEmpty()) {
                    Toast.makeText(requireContext(), "No hay web disponible", Toast.LENGTH_SHORT).show()
                    return@setOnClickListener
                }
                // Añadir https:// si no lo tiene
                val urlFinal = if (web.startsWith("http")) web else "https://$web"
                val intent = Intent(Intent.ACTION_VIEW, Uri.parse(urlFinal))
                startActivity(intent)
            }
        }
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}