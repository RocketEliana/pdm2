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
import com.example.musicplayer.databinding.FragmentAccesoBinding


class AccesoFragment : Fragment() {
    private var _binding: FragmentAccesoBinding? = null
    private val binding get() = _binding!!
    private val viewModel: AppViewModel by activityViewModels ()

    private lateinit var preferencias: SharedPreferences
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAccesoBinding.inflate(inflater, container, false)
        return binding.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        preferencias=requireContext().getSharedPreferences(MainActivity.PREFERENCIAS, Context.MODE_PRIVATE)

        binding.acceder.setOnClickListener{
             val nombre=binding.nombreLog.text.toString()
            val pass=binding.passLog.text.toString()
            val user=viewModel.nombreContraseniaUser(nombre,pass)
            if(user !=null){
            val id=user.id
            preferencias.edit().putLong(MainActivity.ID_REGISTRADO,id).apply()
            val intent= Intent(requireContext(), Principal::class.java)
            startActivity(intent)
            }else{
                Toast.makeText(requireContext(),"debes registrarte, el usuario no existe",Toast.LENGTH_SHORT).show()
             return@setOnClickListener
            }


        }

    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}