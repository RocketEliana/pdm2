package com.example.loginpersonalizado

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import com.example.loginpersonalizado.databinding.FragmentAltaBinding
import com.example.loginpersonalizado.model.User
import com.example.loginpersonalizado.viewModel.UserViewModel


class AltaFragment : Fragment() {
    private var _binding: FragmentAltaBinding? = null
    private val viewModel: UserViewModel by activityViewModels()
    private val binding get() = _binding!!
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAltaBinding.inflate(inflater, container, false)
        return binding.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val preferences=requireContext().getSharedPreferences(MainActivity.PREF_USER, Context.MODE_PRIVATE)
        val password=binding.pasword.text.toString()
        val nombre=binding.nombre.text.toString()
        binding.alta.setOnClickListener {
            val user= User(nombre = nombre, contrasenia = password)
            viewModel.insertar(user)
            preferences.edit().putBoolean(MainActivity.ALTA,true).apply()
            val intent= Intent(requireActivity(), MainActivity3::class.java)
            startActivity(intent)

            Toast.makeText(requireContext(),"Insertado con exito", Toast.LENGTH_LONG).show()
        }
        binding.limpiar.setOnClickListener {
            binding.nombre.text.clear()
            binding.pasword.text.clear()
        }


    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


}