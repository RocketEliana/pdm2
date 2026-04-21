package com.example.pokemongo

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import com.example.pokemongo.databinding.FragmentAccesoBinding
import com.example.pokemongo.databinding.FragmentRegistroBinding
import com.example.pokemongo.model.User
import com.example.pokemongo.viewModel.AppViewModel
import kotlin.getValue

class AccesoFragment : Fragment() {
    private var _binding: FragmentAccesoBinding? = null
    private val viewModel  : AppViewModel by activityViewModels ()
    private val binding get() = _binding!!
    private lateinit var preferences: SharedPreferences
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAccesoBinding.inflate(inflater, container, false)
        return binding.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        preferences= requireContext().getSharedPreferences("preferencias",Context.MODE_PRIVATE)
        binding.acceder.setOnClickListener {

            val nombreR = binding.nombreLog.text.toString();
            val passwordR = binding.passLog.text.toString();
            if (!nombreR.isEmpty() && !passwordR.isEmpty()) {
                val idUser = viewModel.getIdUser(nombreR, passwordR)
                if (idUser != null) {
                    preferences.edit().putLong("idSeleccionado",idUser).apply()
                    val intent = Intent(requireContext(), Principal::class.java)
                    intent.putExtra("idSeleccionado", idUser)
                    startActivity(intent)
                } else {
                    Toast.makeText(requireContext(), "El usuario no existe", Toast.LENGTH_LONG)
                        .show()
                    return@setOnClickListener
                }
            }
        }

        binding.clean.setOnClickListener {
            binding.nombreLog.text.clear()
            binding.passLog.text.clear()

        }
    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}