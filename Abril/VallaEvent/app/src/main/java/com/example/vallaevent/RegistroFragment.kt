package com.example.vallaevent

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import com.example.vallaevent.databinding.FragmentRegistroBinding
import model.User
import viewModel.AppViewModel


class RegistroFragment : Fragment() {
    private var _binding: FragmentRegistroBinding? = null
    private val viewModel: AppViewModel by activityViewModels ()
    private lateinit var preferencias: SharedPreferences
    private val binding get() = _binding!!
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentRegistroBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        preferencias =
            requireContext().getSharedPreferences(MainActivity.PREFERENCES, Context.MODE_PRIVATE)
        binding.acceder.setOnClickListener {
            val nombreL = binding.nombreLog.text.toString()
            val ciudadL = binding.ciudadLog.text.toString()
            if (!nombreL.isEmpty() && !ciudadL.isEmpty()) {
                val user = User(nombre = nombreL, ciudad = ciudadL)
                val id=viewModel.insertaUser(user)
                if(id != -1L){
                    Toast.makeText(requireContext(),"Bienvenido", Toast.LENGTH_LONG).show()
                    preferencias.edit().putBoolean(MainActivity.LOGGEADO,true).apply()
                    parentFragmentManager.beginTransaction().replace(R.id.contenedorPincipal,
                        ListadoFragment()).commit()

                }else{
                    Toast.makeText(requireContext(),"Problema de insercion de usuario", Toast.LENGTH_LONG).show()
                }
            }else{
                Toast.makeText(requireContext(),"No puede haber campos vacios", Toast.LENGTH_LONG).show()
            }
        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null

    }
}