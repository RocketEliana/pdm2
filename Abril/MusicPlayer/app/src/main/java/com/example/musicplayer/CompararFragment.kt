package com.example.musicplayer

import View.AppViewModel
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import androidx.fragment.app.activityViewModels
import com.example.musicplayer.databinding.FragmentCompararBinding
import model.Cancion

class CompararFragment : Fragment() {
    private var _binding: FragmentCompararBinding? = null
    private val binding get() = _binding!!
    private val viewModel: AppViewModel by activityViewModels()
    private lateinit var adapter1: AdapterSpinner
    private lateinit var adapter2: AdapterSpinner
    private var cancion1: Cancion? = null
    private var cancion2: Cancion? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCompararBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        adapter1 = AdapterSpinner(requireContext(), mutableListOf())
        binding.spinner1.adapter = adapter1
        adapter2 = AdapterSpinner(requireContext(), mutableListOf())
        binding.spinner3.adapter = adapter2

        viewModel.listaCancion.observe(viewLifecycleOwner) { lista ->
            adapter1.actualizarLista(lista)
            adapter2.actualizarLista(lista)
        }

        binding.spinner1.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                cancion1 = adapter1.getItem(position)
            }
            override fun onNothingSelected(parent: AdapterView<*>?) {}
        }

        binding.spinner3.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                cancion2 = adapter2.getItem(position)
            }
            override fun onNothingSelected(parent: AdapterView<*>?) {}
        }

        // ← LÓGICA DEL BOTÓN COMPARAR
        binding.comparar
            .setOnClickListener {
            val c1 = cancion1
            val c2 = cancion2

            if (c1 == null || c2 == null) {
                binding.textView2
                    .text = "Selecciona las dos canciones"
                return@setOnClickListener
            }

            if (c1.id == c2.id) {
                binding.textView2
                    .text = "Selecciona dos canciones diferentes"
                return@setOnClickListener
            }

            val puntuacion1 = calcularPuntuacion(c1, c2)
            val puntuacion2 = calcularPuntuacion(c2, c1)

            val resultado = when {
                puntuacion1 > puntuacion2 ->
                    "🏆 Gana: ${c1.titulo}\n" +
                            "Puntuación: $puntuacion1 vs $puntuacion2"

                puntuacion2 > puntuacion1 ->
                    "🏆 Gana: ${c2.titulo}\n" +
                            "Puntuación: $puntuacion2 vs $puntuacion1"

                else ->
                    "🤝 Empate\n" +
                            "Puntuación: $puntuacion1 vs $puntuacion2"
            }

            binding.textView2.text = resultado
        }
    }

    // Calcula la puntuación de 'cancion' enfrentada contra 'rival'
    private fun calcularPuntuacion(cancion: Cancion, rival: Cancion): Double {
        val multiplicador = if (tieneVentaja(cancion.genero, rival.genero)) 2.0 else 1.0
        return cancion.valoracion * multiplicador
    }

    // Rock > Pop > Jazz > Rock  (piedra papel tijera)
    private fun tieneVentaja(genero: String, generoRival: String): Boolean {
        return when (genero.uppercase()) {
            "ROCK" -> generoRival.uppercase() == "POP"
            "POP"  -> generoRival.uppercase() == "JAZZ"
            "JAZZ" -> generoRival.uppercase() == "ROCK"
            else   -> false
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}