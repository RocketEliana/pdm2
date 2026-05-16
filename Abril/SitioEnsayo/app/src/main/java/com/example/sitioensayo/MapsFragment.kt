package com.example.sitioensayo

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
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import viewModel.AppViewModel

class MapsFragment : Fragment(), OnMapReadyCallback {

    private lateinit var mMap: GoogleMap
    private val viewModel: AppViewModel by activityViewModels ()
    private  lateinit var preferencias: SharedPreferences

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
        val contexto = context ?: return //contexto seguro para que no haga crash en el update

        preferencias =
            contexto.getSharedPreferences(MainActivity.PREFERENCES, Context.MODE_PRIVATE)
      //  preferencias =

        //    requireContext().getSharedPreferences(MainActivity.PREFERENCES, Context.MODE_PRIVATE)
        val idR = preferencias.getLong(MainActivity.ID_S, -1L)
        if (idR != -1L) {
            val sitio = viewModel.getPOrId(idR)
            sitio?.let {
                val sitioMaps = LatLng(it.latitud, it.longitud)

                mMap.addMarker(
                    MarkerOptions().position(sitioMaps).icon(
                        BitmapDescriptorFactory.defaultMarker(
                            BitmapDescriptorFactory.HUE_AZURE
                        )
                    )
                )
                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(sitioMaps, 10f))
                mMap.uiSettings.isZoomControlsEnabled = true
                // Habilita los gestos de zoom (incluyendo la rueda del ratón en el emulador)

        }
    }
            }

        }



