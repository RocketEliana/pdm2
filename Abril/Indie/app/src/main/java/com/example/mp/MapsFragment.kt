package com.example.mp

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import androidx.fragment.app.Fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import com.example.mp.databinding.FragmentMapsBinding

import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import model.Sala
import viewModel.ViewModelGira

class MapsFragment : Fragment(), OnMapReadyCallback {

    private lateinit var map: GoogleMap
    private var _binding: FragmentMapsBinding? = null
    private val binding get() = _binding!!
    private var salaSeleccion: Sala?=null
    private var salaSpinner:Sala?=null
    private val viewModel: ViewModelGira by activityViewModels ()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMapsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        val mapFragment = childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment?
        mapFragment?.getMapAsync(this)
        val idSalaRecibida=arguments?.getInt("id",-1) ?: -1//es un Int?
        val idSalaSpinner=arguments?.getInt("idSpinner",-1) ?: -1

        if(idSalaRecibida != -1){
            salaSeleccion=viewModel.obtenerSalaPorId(idSalaRecibida)
        }
        if(idSalaSpinner != -1){salaSpinner=viewModel.obtenerSalaPorId(idSalaSpinner)}


    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onMapReady(googleMap: GoogleMap) {//me pide implementarlo ,es el callback
        map=googleMap

        // Tipo de mapa: normal (calles, colores claros)
        map.mapType = GoogleMap.MAP_TYPE_NORMAL
        val latitudA=salaSeleccion?.latitud ?: 0.0
        val longitudA=salaSeleccion?.longitud ?: 0.0


// Conv
        val destinoA= LatLng(latitudA,longitudA)
        map.addMarker(MarkerOptions().position(destinoA).icon(BitmapDescriptorFactory.defaultMarker(
            BitmapDescriptorFactory.HUE_AZURE)))
        map.moveCamera(CameraUpdateFactory.newLatLngZoom(destinoA,5f))
        map.uiSettings.isZoomControlsEnabled=true
        // Habilita los gestos de zoom (incluyendo la rueda del ratón en el emulador)
        map.uiSettings.isZoomGesturesEnabled = true

// Opcional: Habilita el desplazamiento (pan) para mover el mapa
        map.uiSettings.isScrollGesturesEnabled = true

// Opcional: Habilita la inclinación y rotación
        map.uiSettings.isTiltGesturesEnabled = true
        map.uiSettings.isRotateGesturesEnabled = true

        val latitudB=salaSpinner?.latitud ?: 0.0
        val longitudB=salaSpinner?.longitud ?: 0.0


// Conv
        val destinoB= LatLng(latitudB,longitudB)
        map.addMarker(MarkerOptions().position(destinoB).icon(BitmapDescriptorFactory.defaultMarker(
            BitmapDescriptorFactory.HUE_GREEN)))
        map.moveCamera(CameraUpdateFactory.newLatLngZoom(destinoB,5f))




    }
}
