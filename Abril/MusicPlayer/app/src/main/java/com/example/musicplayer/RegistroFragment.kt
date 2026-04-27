package com.example.musicplayer

import View.AppViewModel
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
import com.example.musicplayer.databinding.FragmentRegistroBinding
import model.User
import kotlin.getValue


class RegistroFragment : Fragment() {
    private var _binding: FragmentRegistroBinding? = null
    private val binding get() = _binding!!
    private val viewModel: AppViewModel by activityViewModels ()

    private lateinit var preferencias: SharedPreferences
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentRegistroBinding.inflate(inflater, container, false)
        return binding.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        preferencias=requireContext().getSharedPreferences(MainActivity.PREFERENCIAS, Context.MODE_PRIVATE)

        binding.registro
            .setOnClickListener{
            val nombreR=binding.nombreLogR.text.toString()
            val passR=binding.passLogP.text.toString()
                val correoR=binding.correoLogR.text.toString()
                val u= User(nombre=nombreR, correo = correoR, contrasenia = passR)
            val id=viewModel.insertaUser(u)
            if(id != -1L){
                preferencias.edit().putLong(MainActivity.ID_REGISTRADO,id).apply()
                val intent= Intent(requireContext(), Principal::class.java)
                startActivity(intent)
            }else{
                Toast.makeText(requireContext(),"error al registrarse",Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }


        }

    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}