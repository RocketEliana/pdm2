package com.example.tareaprogramada

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.icu.util.Calendar
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import com.example.tareaprogramada.databinding.FragmentUpdateBinding
import com.example.tareaprogramada.model.Tarea
import com.example.tareaprogramada.viewModel.TareaViewModel


class UpdateFragment : Fragment() {
    private var _binding: FragmentUpdateBinding? = null
    private val viewModel: TareaViewModel by activityViewModels ()
    private val binding get() = _binding!!
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentUpdateBinding.inflate(inflater, container, false)
        return binding.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        // 1. Obtenemos el ID como un entero opcional (puede ser null)
        val idRecibido: Int? = arguments?.getInt("ID")

// 2. Usamos .let para crear un bloque seguro
        idRecibido?.let { idSeguro ->
            // Aquí dentro, 'idSeguro' ya NO es opcional, es un Int puro
            val tareaRecibida = viewModel.tarId(idSeguro)

            binding.editNombre.hint = tareaRecibida?.nombre
            binding.editFecha.hint = tareaRecibida?.fecha
        }
        binding.editFecha.setOnClickListener { mostrarCalendario() }
        binding.modificar.setOnClickListener {
            val nombreE=binding.editNombre.text.toString()
            val fechaE=binding.editFecha.text.toString()
            val tareaModificada= Tarea(nombre = nombreE, fecha = fechaE)
            viewModel.actualiza(tareaModificada)
            parentFragmentManager.popBackStack()

        }

    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
    private fun mostrarCalendario() {
        val calendario = Calendar.getInstance()
        DatePickerDialog(
            requireContext(),
            { _, año, mes, dia ->
                TimePickerDialog(
                    requireContext(),
                    { _, hora, min ->
                        val fechaHora =
                            String.format("%02d/%02d/%d %02d:%02d", dia, mes + 1, año, hora, min)
                        binding.editFecha
                            .setText(fechaHora)
                    },
                    calendario.get(Calendar.HOUR_OF_DAY),
                    calendario.get(Calendar.MINUTE),
                    true
                ).show()
            },
            calendario.get(Calendar.YEAR),
            calendario.get(Calendar.MONTH),
            calendario.get(Calendar.DAY_OF_MONTH)
        ).show()
    }

}