package com.example.musicplayer

import androidx.fragment.app.Fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions

class MapsFragment : Fragment(), OnMapReadyCallback {

    private lateinit var mMap: GoogleMap

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


            val palacioLatLon = LatLng( 40.41542, -3.71407)
            val winLatLon = LatLng(40.4165, -3.70256)
            mMap.addMarker(
                MarkerOptions().position(palacioLatLon).icon(
                    BitmapDescriptorFactory.defaultMarker(
                        BitmapDescriptorFactory.HUE_AZURE
                    )
                )
            )
        mMap.addMarker(
            MarkerOptions()
                .position(palacioLatLon)
                .title("Palacio de los deportes")
                .snippet("Cañaaaaa")
        )
        mMap.addMarker(
            MarkerOptions()
                .position(winLatLon)
                .title("Wizzzinnnnn")
                .snippet("Mas caññññññaaaaa!!!!!!!!!!!!!!!!!!")
        )
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(winLatLon, 10f))
            mMap.uiSettings.isZoomControlsEnabled = true
            // Habilita los gestos de zoom (incluyendo la rueda del ratón en el emulador)
            mMap.uiSettings.isZoomGesturesEnabled = true
            mMap.addMarker(
                MarkerOptions().position(winLatLon).icon(
                    BitmapDescriptorFactory.defaultMarker(
                        BitmapDescriptorFactory.HUE_GREEN
                    )
                )
            )
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(palacioLatLon, 10f))
        }

}

