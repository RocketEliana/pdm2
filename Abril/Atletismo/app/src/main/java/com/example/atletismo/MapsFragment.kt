package com.example.atletismo

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
        val categoriaId = arguments?.getLong("categoria", -1L)
        val competicionId = arguments?.getLong("competicion", -1L)
        if (categoriaId != -1L && competicionId != -1L) {
            val cate=viewModel.getCategoriaId(categoriaId!!)
            val compe=viewModel.getCompeticionPorId(competicionId!!)
            val cateCoor = LatLng(cate?.latitud!!, cate?.longitud!!)
            val compeCoor = LatLng(compe?.latitud!!,compe?.longitud!!)



            mMap.addMarker(
                MarkerOptions()
                    .position(cateCoor)
                    .title("Zorrilla")
                    .snippet(cate.nombre)
                    .icon(
                        BitmapDescriptorFactory.defaultMarker(
                            BitmapDescriptorFactory.HUE_BLUE
                        )
                    )
            )
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(cateCoor, 10f))
            mMap.uiSettings.isZoomControlsEnabled = true
            // Habilita los gestos de zoom (incluyendo la rueda del ratón en el emulador)
            mMap.uiSettings.isZoomGesturesEnabled = true
            mMap.addMarker(
                MarkerOptions().position(compeCoor).icon(
                    BitmapDescriptorFactory.defaultMarker(
                        BitmapDescriptorFactory.HUE_GREEN
                    )
                )
            )
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(compeCoor, 10f))
        }
    }
}