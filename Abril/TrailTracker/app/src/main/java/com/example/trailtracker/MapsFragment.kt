package com.example.trailtracker

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
import view.AppViewModel
import kotlin.getValue

class MapsFragment : Fragment() , OnMapReadyCallback {
    private val viewModel: AppViewModel by activityViewModels ()
    private lateinit var preferencias: SharedPreferences
    private val tipo:String?=null

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
        preferencias =requireContext().getSharedPreferences(MainActivity.PREFERENCES, Context.MODE_PRIVATE) // ← añade esto
        val idSenda = preferencias.getLong(MainActivity.ID_SELECCION,-1L)
        val tipo=arguments?.getString("tipo")
        val senda=viewModel.getSendaId(idSenda)
        val sendaO= LatLng(senda?.latitud!!,senda?.longitud!!)
        val actividad=when(tipo){
            "senderismo"-> LatLng(senda.actSenderismo.latA,senda.actSenderismo.longA)
            "escalada"-> LatLng(senda.actEscalada.latA,senda.actEscalada.longA)
            "montaña"-> LatLng(senda.actMontana.latA,senda.actMontana.longA)
                else-> LatLng(0.0,0.0)

        }
            mMap.addMarker(
                MarkerOptions().position(sendaO).icon(
                    BitmapDescriptorFactory.defaultMarker(
                        BitmapDescriptorFactory.HUE_AZURE
                    )
                )
            )
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(sendaO, 10f))
            mMap.uiSettings.isZoomControlsEnabled = true
            // Habilita los gestos de zoom (incluyendo la rueda del ratón en el emulador)
            mMap.uiSettings.isZoomGesturesEnabled = true
            mMap.addMarker(
                MarkerOptions().position(actividad).icon(
                    BitmapDescriptorFactory.defaultMarker(
                        BitmapDescriptorFactory.HUE_GREEN
                    )
                )
            )
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(actividad, 10f))
        }
    }
