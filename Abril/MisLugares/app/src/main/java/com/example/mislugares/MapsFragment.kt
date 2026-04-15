package com.example.mislugares

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import androidx.fragment.app.Fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import com.example.mislugares.viewModel.AppViewModel

import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions

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
        val id = arguments?.getLong("id") ?: -1L
        if (id != -1L) {
            val lugar = viewModel.getPorId(id)
            Log.d("MapsFragment", "Lugar encontrado: $lugar")
            if (lugar == null) {
                Log.e("MapsFragment", "No se encontró lugar con id $id")
                return
            }
            if (lugar.latitud == 0.0 && lugar.longitud == 0.0) {
                Log.e("MapsFragment", "Coordenadas (0,0), mostrando océano")
                return
            }
            lugar?.let {
                val latitud=it.latitud
                val longitud=it.longitud
                //val imagen=lugar.foto
                val lugarCoor= LatLng(latitud,longitud)
                mMap.addMarker(MarkerOptions().position(lugarCoor).title("Marcador del lugar"))
                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(lugarCoor, 10f))
                //val bitmap = BitmapFactory.decodeFile(imagen)
                //val bitmapEscalado = Bitmap.createScaledBitmap(bitmap, 120, 120, false)
                //val icon = BitmapDescriptorFactory.fromBitmap(bitmapEscalado)

                /*mMap.addMarker(
                    MarkerOptions()
                        .position(lugarCoor)
                        .title("Marcador del lugar")
                        .icon(icon)
                )*/
            }


        }
    }
}