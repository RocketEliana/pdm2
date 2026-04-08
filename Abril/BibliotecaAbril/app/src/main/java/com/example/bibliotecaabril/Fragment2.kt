package com.example.bibliotecaabril

import androidx.fragment.app.Fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import com.example.bibliotecaabril.ViewModel.AppViewModel

import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions

class Fragment2 : Fragment(), OnMapReadyCallback {

    private lateinit var mMap: GoogleMap
    private  val viewModel: AppViewModel by activityViewModels ()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflamos el layout del fragmento
        return inflater.inflate(R.layout.fragment_2, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Buscamos el fragmento de mapa dentro de nuestro fragmento
        val mapFragment = childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment?
        mapFragment?.getMapAsync(this)
    }

    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap
        val idDestino=arguments?.getLong("idDestino") ?: -1L
        val idOrigen=arguments?.getLong("idOrigen") ?: -1L
        if(idDestino != -1L && idOrigen != -1L){
            val destino=viewModel.bibliotecaById(idDestino)
            val latDestino=destino?.latitud ?: 0.0
            val lonDestino=destino?.longitud ?: 0.0
            val origen=viewModel.bibliotecaById(idOrigen)
            val latOrigen=origen?.latitud ?: 0.0
            val lonOrigen=origen?.longitud ?: 0.0
            val origenLatLon = LatLng(latOrigen, lonOrigen)
            val destinoLatLon = LatLng(latDestino, lonDestino)
            mMap.addMarker(MarkerOptions().position(origenLatLon).icon(
                BitmapDescriptorFactory.defaultMarker(
                BitmapDescriptorFactory.HUE_AZURE)))
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(origenLatLon,10f))
            mMap.uiSettings.isZoomControlsEnabled=true
            // Habilita los gestos de zoom (incluyendo la rueda del ratón en el emulador)
            mMap.uiSettings.isZoomGesturesEnabled = true
            mMap.addMarker(MarkerOptions().position(destinoLatLon).icon(BitmapDescriptorFactory.defaultMarker(
                BitmapDescriptorFactory.HUE_GREEN)))
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(destinoLatLon,10f))

        }

    }
}