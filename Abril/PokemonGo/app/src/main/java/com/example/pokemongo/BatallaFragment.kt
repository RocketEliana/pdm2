package com.example.pokemongo

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.example.pokemongo.databinding.FragmentBatallaBinding
import com.example.pokemongo.model.Pokemon
import com.example.pokemongo.viewModel.AppViewModel

class BatallaFragment : Fragment() {

    private var _binding: FragmentBatallaBinding? = null
    private val binding get() = _binding!!

    private val viewModel: AppViewModel by activityViewModels()

    private lateinit var adapterA: AdapterSpinnerPokemon
    private lateinit var adapterB: AdapterSpinnerPokemon

    private var idA: Long = -1L
    private var idB: Long = -1L
    private var pokemonA: Pokemon?=null
    private var pokemonB:Pokemon?=null

    private var dialogShown = false

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentBatallaBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        adapterA = AdapterSpinnerPokemon(requireContext(), mutableListOf())
        binding.spinnerA.adapter = adapterA

        viewModel.listaPokemon.observe(viewLifecycleOwner) { lista ->
            adapterA.actualizarLista(lista)
        }

        binding.spinnerA.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                pokemonA = adapterA.getItem(position)
                pokemonA?.let {
                    idA = it.id
                    checkIguales()
                }
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {}
        }


        adapterB = AdapterSpinnerPokemon(requireContext(), mutableListOf())
        binding.spinnerB.adapter = adapterB

        viewModel.listaPokemon.observe(viewLifecycleOwner) { lista ->
            adapterB.actualizarLista(lista)
        }

        binding.spinnerB.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                 pokemonB = adapterB.getItem(position)
                pokemonB?.let {
                    idB = it.id
                    checkIguales()
                }
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {}
        }

        // Botón batalla
        binding.batalla.setOnClickListener {
            //lucha de id
            val secuenciaA=pokemonA?.tipo
            val secuenciaB=pokemonB?.tipo
            val numericoA=ganador(secuenciaA!!)
            val numericoB=ganador(secuenciaB!!)
            if(numericoA>numericoB){
                binding.resultado.text = "Gana {${pokemonA?.tipo}}"
                pokemonA?.nivel?.let { it * 2 }
            }else if(numericoA<numericoB){binding.resultado.text = "Gana {${pokemonB?.tipo}}"
                pokemonB?.nivel?.let { it * 2 }}
            else{
                val nivelA=pokemonA?.nivel
                val nivelB=pokemonB?.nivel
                if(nivelA!!>nivelB!!){
                    binding.resultado.text="Gana {${pokemonA?.tipo}}"
                    pokemonA?.nivel?.let { it * 2 }
                }else{ binding.resultado.text="Gana {${pokemonB?.tipo}}"
                    pokemonB?.nivel?.let { it * 2 }}
            }

        }
    }

    private fun checkIguales() {
        if (idA != -1L && idB != -1L && idA == idB && !dialogShown) {
            dialogShown = true

            AlertDialog.Builder(requireContext())
                .setMessage("Elige otro que no sea el mismo")
                .setPositiveButton("Volver") { _, _ ->
                    dialogShown = false
                }
                .show()
        }
    }
    fun ganador(secuencia: String):Int{
         when(secuencia){
            "planta"-> return 0
            "agua" -> return 1
            "fuego"->return 2
        }
        return -1


    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}