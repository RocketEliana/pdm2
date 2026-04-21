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

class RegistroFragment : Fragment() {
    private var _binding: FragmentRegistroBinding? = null
    private val viewModel  : AppViewModel by activityViewModels ()
    private val binding get() = _binding!!
    private lateinit var preferences: SharedPreferences
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentRegistroBinding.inflate(inflater, container, false)
        return binding.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        preferences=requireContext().getSharedPreferences("preferencias",Context.MODE_PRIVATE)
        binding.btnRegistro
            .setOnClickListener {

            val nombreR = binding.nombreregistro.text.toString();
            val passwordR = binding.passRegistro.text.toString();
            if (!nombreR.isEmpty() && !passwordR.isEmpty()) {
                val user=User(nombre=nombreR, password = passwordR)
                val idUser=viewModel.insertarUser(user)
                if (idUser != null) {
                    preferences.edit().putLong("idSeleccionado",idUser).apply()
                    val intent = Intent(requireContext(), Principal::class.java)
                    intent.putExtra("idSeleccionado", idUser)
                    startActivity(intent)
                } else {
                    Toast.makeText(requireContext(), "No se ha podido insertar", Toast.LENGTH_LONG)
                        .show()
                    return@setOnClickListener
                }
            }
        }

        binding.limpia.setOnClickListener {
            binding.nombreregistro.text.clear()
            binding.passRegistro.text.clear()

        }
    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}