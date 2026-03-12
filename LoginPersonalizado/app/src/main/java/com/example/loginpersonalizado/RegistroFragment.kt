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
import com.example.loginpersonalizado.databinding.FragmentRegistroBinding
import com.example.loginpersonalizado.viewModel.bdViewModel

class RegistroFragment : Fragment() {
    private var _binding: FragmentRegistroBinding? = null
    private val viewModel: bdViewModel by activityViewModels ()
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentRegistroBinding.inflate(inflater, container, false)
        return binding.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val preferences=requireContext().getSharedPreferences(MainActivity.PREF_USER, Context.MODE_PRIVATE)


        binding.registrar.setOnClickListener {
            val nombre=binding.nombre.text.toString()
            val password=binding.pasword.text.toString()
            val id=viewModel.IdUser(nombre,password)
            if(id !=null){
                preferences.edit().putBoolean(MainActivity.REGISTRADO,true).apply()
                val intent= Intent(requireActivity(), MainActivity3::class.java)
                startActivity(intent)



            }else{
                Toast.makeText(requireContext(),"El usuario no esta en la base de datos,debe registrarse", Toast.LENGTH_LONG).show()
                val intent= Intent(requireActivity(), MainActivity2::class.java)
                startActivity(intent)
            }
        }


    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}