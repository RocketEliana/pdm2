package com.example.sitioensayo

import android.content.Context
import android.content.SharedPreferences
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.example.sitioensayo.databinding.FragmentDetalleBinding
import viewModel.AppViewModel


class DetalleFragment : Fragment() {
    private var _binding: FragmentDetalleBinding? = null
    private val binding get() = _binding!!

    private val viewModel: AppViewModel by activityViewModels()
    private lateinit var preferncias: SharedPreferences
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDetalleBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        preferncias =
            requireContext().getSharedPreferences(MainActivity.PREFERENCES, Context.MODE_PRIVATE)
        val id = arguments?.getLong("id", -1L) ?: -1L

        if (id != -1L) {
            val sitio = viewModel.getPOrId(id)
            sitio?.let {
                binding.nombre.text = it.nombre
                binding.direccion.text = it.direccion
                binding.web.text = it.web
                binding.telefon.text = it.telefono
                binding.descripcion.text = it.telefono
                binding.calificacion.rating = it.calificacion
                binding.imgtipo.setImageResource(it.icono)
                binding.imgFoto.setImageURI(Uri.parse(it.foto))
                binding.fecha.text = it.fecha


                val fragmment = MapsFragment()
                val bundle = Bundle()
                bundle.putLong("id", id)
                fragmment.arguments = bundle
                childFragmentManager.beginTransaction().replace(R.id.mapa, fragmment).commit()

                binding.borrar.setOnClickListener {
                    viewModel.eliminarSitio(sitio)
                irLista()
                }
            }

            binding.modificar.setOnClickListener {
                val fragment = ModificarFragment()
                val bundle = Bundle()
                bundle.putLong("id", id)
                fragment.arguments = bundle
                parentFragmentManager.beginTransaction().replace(R.id.contenedor, fragment)
                    .addToBackStack(null).commit()

            }

        }
        binding.volver.setOnClickListener {
            irLista()

        }


    }

    fun irLista() {
        preferncias.edit()
            .remove(MainActivity.ID_S)
            .apply()

        parentFragmentManager.beginTransaction()
            .replace(R.id.contenedor, ListaFragment())
            .commit()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}