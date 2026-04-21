package com.example.pokemongo

import android.Manifest
import android.R
import android.content.ContentValues
import android.graphics.Bitmap
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ListView
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.activityViewModels
import com.example.pokemongo.databinding.FragmentNuevoPokemonBinding
import com.example.pokemongo.model.Pokemon
import com.example.pokemongo.viewModel.AppViewModel

class NuevoPokemonFragment : Fragment() {
    private var _binding: FragmentNuevoPokemonBinding? = null
    private val viewModel: AppViewModel by activityViewModels ()
    private var bitmapFoto: Bitmap? = null
    private var uri: Uri? = null
    private var rutaImagen: String? = null
    private  var  nivel:Int=0
    private var tipo:String=""

    private val launcherTomarFoto = registerForActivityResult(
        ActivityResultContracts.TakePicturePreview()
    ) { bitmap ->
        if (bitmap != null) {
            binding.foto.setImageBitmap(bitmap)
            bitmapFoto = bitmap

            // AQUÍ LLAMAS A TU FUNCIÓN PARA CONSEGUIR LA URI
            val uriGuardada = guardarFotoEnGaleria(bitmap)
            if (uriGuardada != null) {
                uri = uriGuardada // Ya tienes el valor de la URI
                rutaImagen = uriGuardada.toString()  // Aquí tienes la ruta como String
                Toast.makeText(requireContext(), "Foto guardada en: $rutaImagen", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private val launcherPermisoCamara = registerForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { concedido ->
        if (concedido) launcherTomarFoto.launch(null)
        else Toast.makeText(requireContext(), "Permiso necesario", Toast.LENGTH_SHORT).show()
    }

    private val binding get() = _binding!!
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentNuevoPokemonBinding.inflate(inflater, container, false)
        return binding.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.slider.valueFrom = 0f
        binding.slider.valueTo = 100f
        binding.slider.stepSize = 1f//solo enteros
        val tipoList=listOf("agua","fuego","planta")
        val adapter = ArrayAdapter(requireContext(), android.R.layout.simple_list_item_activated_1, tipoList)
        binding.listatipo.adapter = adapter
        // Importante: Dile al ListView que solo se puede elegir uno a la vez
        binding.listatipo.choiceMode = ListView.CHOICE_MODE_SINGLE
        binding.listatipo.setOnItemClickListener { parent, view, position, id ->
            binding.listatipo.setItemChecked(position, true)
            tipo= tipoList[position]
        }


        binding.slider.addOnChangeListener { slider, value, fromUser ->
             nivel = binding.slider.value.toInt()
        }
        binding.foto
            .setOnClickListener {
                launcherPermisoCamara.launch(Manifest.permission.CAMERA)
            }
        binding.registro.setOnClickListener {
            val pokemon= Pokemon(tipo=tipo, nivel = nivel,foto=rutaImagen!!)
            val id=viewModel.insertarPoquemon(pokemon)
            if(id !=null){
                Toast.makeText(requireContext(),"Insertado con exito",Toast.LENGTH_SHORT).show()
            }else{
                Toast.makeText(requireContext(),"Problema de insercion",Toast.LENGTH_SHORT).show()

            }

        }

    }
    private fun guardarFotoEnGaleria(bitmap: Bitmap): Uri? {
        val fileName = "pokemon_${System.currentTimeMillis()}.jpg"
        val resolver = requireContext().contentResolver//ojo que es un fragment
        val contentValues = ContentValues().apply {
            put(MediaStore.Images.Media.DISPLAY_NAME, fileName)
            put(MediaStore.Images.Media.MIME_TYPE, "image/jpeg")
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                put(MediaStore.Images.Media.RELATIVE_PATH, "Pictures/MisEspectaculos")
            }
        }

        val imageUri = resolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, contentValues)

        imageUri?.let { uri ->
            try {
                resolver.openOutputStream(uri).use { outputStream ->
                    if (outputStream != null) {
                        bitmap.compress(Bitmap.CompressFormat.JPEG, 90, outputStream)
                        return uri
                    }
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
        return null
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}