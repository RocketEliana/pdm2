package viewModel


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.example.rstaurantes.MapsFragment
import com.example.rstaurantes.R
import com.example.rstaurantes.databinding.FragmentDBinding

class DetalleBar : Fragment() {
    private var _binding: FragmentDBinding? = null
    private val binding get() = _binding!!
    private val viewModel: BarViewModel by activityViewModels()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val id = arguments?.getLong("idLista", -1) ?: -1 //para evitar el let
        if (id != -1L) {
            val bar = viewModel.getById(id)
            binding.nombre.text = bar?.nombre.toString() ?: ""
            binding.direccion.text = bar?.direccion.toString() ?: ""
            binding.web.text = bar?.web.toString() ?: ""
            binding.valoracion.rating=bar?.valoracion ?: 0.0f
            val fragment= MapsFragment()
            val latitud=bar?.latitud ?: 0.0
            val longitud=bar?.longitud ?: 0.0
            val bundle= Bundle()
            bundle.putDouble("latitud",latitud)
            bundle.putDouble("longitud",longitud)
            fragment.arguments=bundle
            childFragmentManager.beginTransaction().replace(R.id.mapa,fragment).commit()
        }


    }



override fun onDestroyView() {
    super.onDestroyView()
    _binding = null
}


}
/* binding.valoracion
                 binding.direccion
                 binding.web*/