package com.example.tapasparquesol

import android.content.Intent
import android.net.Uri
import androidx.fragment.app.Fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import com.example.tapasparquesol.model.Bar
import com.example.tapasparquesol.viewModelTapas.ViewModelBar

import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions

class MapsFragment : Fragment() , OnMapReadyCallback {

    private lateinit var mMap: GoogleMap

    private val viewModel: ViewModelBar by activityViewModels  ()
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

        val id = arguments?.getInt("id", -1) ?: -1
        val bar = viewModel.barId(id)
        bar.let { barIr ->
            val latitud = barIr?.latitud ?: 0.0
            val longitud = barIr?.longitud ?: 0.0
            val lugar = LatLng(latitud, longitud)
            val web=barIr?.web ?: ""
            mMap.addMarker(MarkerOptions().position(lugar).title("Bar seleccionado: $web").snippet(web))
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(lugar, 15f))
            mMap.setOnInfoWindowClickListener { marker ->
                val web = marker.snippet

                if (!web.isNullOrEmpty()) {
                    val intent = Intent(Intent.ACTION_VIEW, Uri.parse(web))
                    startActivity(intent)
                }
            }

        }
    }
}
