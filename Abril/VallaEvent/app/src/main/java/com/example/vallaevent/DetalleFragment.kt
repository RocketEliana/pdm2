package com.example.vallaevent

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.SharedPreferences
import android.content.pm.PackageManager
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.activityViewModels
import com.example.vallaevent.databinding.FragmentDetalleBinding
import com.google.android.gms.location.LocationServices
import viewModel.AppViewModel
import kotlin.math.pow


class DetalleFragment : Fragment() {
    private var _binding: FragmentDetalleBinding? = null
    private val viewModel: AppViewModel by activityViewModels()
    private lateinit var preferencias: SharedPreferences
    private val binding get() = _binding!!
    private var long: Double? = null
    private var lat: Double? = null


    private lateinit var fusedLocationClient: com.google.android.gms.location.FusedLocationProviderClient

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDetalleBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        // Buscamos la toolbar por su ID y le decimos qué hacer al pulsar la flecha
        binding.toolbarDetalle.setNavigationOnClickListener {
            // Esto cierra el fragmento actual y vuelve al anterior
            parentFragmentManager.popBackStack()
        }


        fusedLocationClient = LocationServices.getFusedLocationProviderClient(requireContext())
        preferencias = requireContext().getSharedPreferences(MainActivity.PREFERENCES, Context.MODE_PRIVATE)
        val idRecibido = preferencias.getLong(MainActivity.ID_SELECCIONADO, -1L)

        if (idRecibido != -1L) {
            val event = viewModel.getEventoId(idRecibido)
            event?.let {
                lat = it.latitud
                long = it.longitud
                pedirLocalizacion()  // ← llamamos aquí
            }
        }
    }

    // Pide el permiso si no lo tiene, o lanza directamente la localización
    private fun pedirLocalizacion() {
        when {
            ContextCompat.checkSelfPermission(
                requireContext(), Manifest.permission.ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED -> {
                // Ya tenemos permiso → obtener ubicación
                obtenerUbicacion()
            }
            else -> {
                // Pedir permiso
                requestPermissions(
                    arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                    1
                )
            }
        }
    }

    // Resultado de la petición de permiso
    override fun onRequestPermissionsResult(
        requestCode: Int, permissions: Array<out String>, grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == 1 && grantResults.isNotEmpty() &&
            grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            obtenerUbicacion()
        } else {
            binding.distancia.text = "Permiso de ubicación denegado"
        }
    }

    // Obtiene la última ubicación conocida y calcula la distancia
    @SuppressLint("MissingPermission")
    private fun obtenerUbicacion() {
        fusedLocationClient.lastLocation.addOnSuccessListener { location ->
            if (location != null) {
                val distancia = distancia(location.latitude, location.longitude, lat!!, long!!)
                binding.distancia.text = "%.2f km".format(distancia)
            } else {
                binding.distancia.text = "Ubicación no disponible"
            }
        }
    }
    fun distancia(lat1: Double, lon1: Double, lat2: Double, lon2: Double): Double {
        val r = 6371.0
        val dLat = Math.toRadians(lat2 - lat1)
        val dLon = Math.toRadians(lon2 - lon1)
        val a = Math.sin(dLat / 2).pow(2) +
                Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2)) *
                Math.sin(dLon / 2).pow(2)
        val c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a))
        return r * c
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}