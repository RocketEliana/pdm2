package com.example.rutas

import android.content.Context
import android.content.SharedPreferences
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.activityViewModels
import com.example.rutas.databinding.FragmentDetalleBinding
import viewModel.RutaViewModel


class DetalleFragment : Fragment() {
    private var _binding: FragmentDetalleBinding? = null
    private lateinit var preferencias: SharedPreferences
    private val viewModel: RutaViewModel by activityViewModels ()
private val binding get() = _binding!!
override fun onCreateView(
    inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
): View {
    _binding = FragmentDetalleBinding.inflate(inflater, container, false)
    return binding.root
}
override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)
    //preferencias=requireContext().getSharedPreferences(MainActivity.PREFERENCIAS, Context.MODE_PRIVATE)
    val idRecibido=arguments?.getInt(MainActivity.IDETALLE,-1) ?: -1
    if(idRecibido != -1) {
        val ruta = viewModel.rutaId(idRecibido)
        binding.nombre.hint = ruta?.nombre ?: ""
        binding.provincia.hint = ruta?.provincia ?: ""
        binding.web.hint=ruta?.web ?: ""

        binding.ratingBar.rating = ruta?.dificultad?.toFloat() ?: 1f
        val rutaImagen = ruta?.imagen ?: ""
        if (rutaImagen.startsWith("content") || rutaImagen.startsWith("/")) {
            binding.foto.setImageURI(android.net.Uri.parse(rutaImagen))
        } else {
            val resId = requireContext().resources.getIdentifier(
                ruta?.imagen,
                "drawable",
                requireContext().packageName
            )
            binding.foto.setImageResource(resId)
        }

        val latitud=ruta?.latitud ?: 0.0
        val longitud=ruta?.longitud ?: 0.0
        val fragmentoHijo = MapsFragment()
        val bundle=Bundle()
        bundle.putDouble("latitud",latitud)
        bundle.putDouble("longitud",longitud)
        fragmentoHijo.arguments=bundle
        childFragmentManager.beginTransaction().replace(R.id.mapa, fragmentoHijo).commit()

    }


}
override fun onDestroyView() {
    super.onDestroyView()
    _binding = null
}

}