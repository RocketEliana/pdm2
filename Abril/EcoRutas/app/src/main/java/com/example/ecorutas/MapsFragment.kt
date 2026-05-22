package com.example.ecorutas

import androidx.fragment.app.Fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels

import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import viewModel.AppViewModel

class MapsFragment : Fragment(), OnMapReadyCallback {

    private lateinit var mMap: GoogleMap
    private val viewModel: AppViewModel by activityViewModels()
    private var idOrigen: Long = -1L//IMPORTANTE GUARDAR AQUI ESTO ME ESTABA JODIENDO LA APP,Y LA DE ABAJO TAMBIEN
    private var idDestino: Long = -1L
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflamos el layout del fragmento
        return inflater.inflate(R.layout.fragment_maps, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Buscamos el fragmento de mapa dentro de nuestro fragmento
        val mapFragment = childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment?
        mapFragment?.getMapAsync(this)
    }

    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap
        idDestino = arguments?.getLong("idDestino") ?: -1L
        idOrigen = arguments?.getLong("idOrigen") ?: -1L
        if (idDestino != -1L && idOrigen != -1L) {
            val destino = viewModel.getPorIdEspacio(idDestino)
            val latDestino = destino?.latitud ?: 0.0
            val lonDestino = destino?.longitud ?: 0.0
            val origen = viewModel.getPorIdEspacio(idOrigen)
            val latOrigen = origen?.latitud ?: 0.0
            val lonOrigen = origen?.longitud ?: 0.0
            val origenLatLon = LatLng(latOrigen, lonOrigen)
            val destinoLatLon = LatLng(latDestino, lonDestino)
            mMap.addMarker(
                MarkerOptions().position(origenLatLon).icon(
                    BitmapDescriptorFactory.defaultMarker(
                        BitmapDescriptorFactory.HUE_AZURE
                    )
                )
            )
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(origenLatLon, 10f))
            mMap.uiSettings.isZoomControlsEnabled = true
            // Habilita los gestos de zoom (incluyendo la rueda del ratón en el emulador)
            mMap.uiSettings.isZoomGesturesEnabled = true
            mMap.addMarker(
                MarkerOptions().position(destinoLatLon).icon(
                    BitmapDescriptorFactory.defaultMarker(
                        BitmapDescriptorFactory.HUE_GREEN
                    )
                )
            )
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(destinoLatLon, 10f))
        }
    }
}