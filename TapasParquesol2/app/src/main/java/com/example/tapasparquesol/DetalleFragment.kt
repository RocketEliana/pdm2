package com.example.tapasparquesol

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import com.example.tapasparquesol.databinding.FragmentDetalleBinding
import com.example.tapasparquesol.model.Bar
import com.example.tapasparquesol.viewModelTapas.ViewModelBar


class DetalleFragment : Fragment() {
    private var _binding: FragmentDetalleBinding? = null
    private val binding get() = _binding!!
    private val viewModel: ViewModelBar by activityViewModels ()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDetalleBinding.inflate(inflater, container, false)
        return binding.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val idRecibido = arguments?.getInt("id") ?: -1

        if (idRecibido != -1) {
            val barRecibido = viewModel.barId(idRecibido)

            binding.nombre.hint = barRecibido?.nombre ?: ""
            binding.direccion.hint=barRecibido?.direccion ?: ""
            binding.valoracion.rating = (barRecibido?.calificacion ?: 1).toFloat() //rating-->float
            binding.longitud.hint=(barRecibido?.longitud ?: 0.0).toString()
            binding.latitud.hint=(barRecibido?.latitud ?: 0.0).toString()
            binding.web.hint=barRecibido?.web ?: ""
            binding.maps.setOnClickListener {
                val fragment= MapsFragment()
                val bundle=Bundle()
                bundle.putInt("id",idRecibido)
                fragment.arguments=bundle
                parentFragmentManager.beginTransaction().replace(R.id.detalle,fragment).addToBackStack(null).commit()
            }
            binding.borrar.setOnClickListener {if(barRecibido !=null){viewModel.borrar(barRecibido)}  } }
        binding.modificar.setOnClickListener {
            val nombre=binding.nombre.text.toString()
            val direccion=binding.direccion.text.toString()
            val valoracion=binding.valoracion.rating.toString().toInt()
            val latitud=binding.latitud.toString().toDouble()
            val longitud=binding.longitud.toString().toDouble()
            val web=binding.web.text.toString()
            val modificado= Bar(nombre=nombre, direccion = direccion, calificacion = valoracion, longitud = longitud,latitud = latitud,  web = web)
            viewModel.actualiza(modificado)
        }

        }





    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}