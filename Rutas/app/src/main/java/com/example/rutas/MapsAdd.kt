package com.example.rutas

import androidx.fragment.app.Fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions


    class MapsAdd : Fragment(), OnMapReadyCallback {

        private lateinit var mMap: GoogleMap
        private var posicionSeleccionada: LatLng? = null

        override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
        ): View? {
            return inflater.inflate(R.layout.fragment_maps_add, container, false)
        }

        override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
            super.onViewCreated(view, savedInstanceState)

            val mapFragment =
                childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment?

            mapFragment?.getMapAsync(this)
        }

        override fun onMapReady(googleMap: GoogleMap) {
            mMap = googleMap

            // Centrar mapa en España
            val inicio = LatLng(42.6, -5.6)
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(inicio, 8f))

            // Permitir seleccionar ubicación tocando el mapa
            mMap.setOnMapClickListener { latLng ->
                mMap.clear()
                mMap.addMarker(
                    MarkerOptions()
                        .position(latLng)
                        .title("Ubicación seleccionada")
                )

                posicionSeleccionada = latLng

                // Enviar coordenadas al formulario
                val result = Bundle().apply {
                    putDouble("latitud", latLng.latitude)
                    putDouble("longitud", latLng.longitude)
                }

                parentFragmentManager.setFragmentResult("ubicacion", result)
                parentFragmentManager.popBackStack()
            }
        }
    }