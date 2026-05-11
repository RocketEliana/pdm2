package com.example.comidallevar

import android.content.Context
import android.content.SharedPreferences
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
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import kotlinx.coroutines.flow.SharedFlow
import viewModel.AppViewModel

class MapsFragment :Fragment(), OnMapReadyCallback {

    private lateinit var mMap: GoogleMap
    private lateinit var preferencias: SharedPreferences
    private val viewModel: AppViewModel by activityViewModels()

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
        preferencias=requireContext().getSharedPreferences(MainActivity.PREFERENCES, Context.MODE_PRIVATE)
        val id=preferencias.getLong(MainActivity.ID_S,-1L)
        if(id != -1L){
            val res=viewModel.getById(id)
            res?.let {
                val latitud=it.latitud
                val longitud=it.longitud

                val lugar = LatLng(latitud, longitud)
                mMap.addMarker(MarkerOptions().position(lugar).title("Marcador en Madrid"))
                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(lugar, 10f))
            }
            }
        }


}