package com.example.musicplayer

import View.AppViewModel
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
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.fragment.app.viewModels
import com.example.musicplayer.databinding.FragmentNuevaBinding
import model.Cancion

class NuevaFragment : Fragment() {
    private var _binding: FragmentNuevaBinding? = null
    private val viewModel: AppViewModel by viewModels()
    private val binding get() = _binding!!
    private var bitmapFoto: Bitmap? = null
    private var uri: Uri? = null
    private var rutaImagen: String? = null
    private var genero:String?=""

    private val launcherTomarFoto = registerForActivityResult(
        ActivityResultContracts.TakePicturePreview()
    ) { bitmap ->
        if (bitmap != null) {
            binding.imgItem
                .setImageBitmap(bitmap)
            bitmapFoto = bitmap

            // AQUÍ LLAMAS A TU FUNCIÓN PARA CONSEGUIR LA URI
            val uriGuardada = guardarFotoEnGaleria(bitmap)
            if (uriGuardada != null) {
                uri = uriGuardada // Ya tienes el valor de la URI
                rutaImagen = uriGuardada.toString() // Aquí tienes la ruta como String
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
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentNuevaBinding.inflate(inflater, container, false)
        return binding.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.imgItem
            .setOnClickListener {
                launcherPermisoCamara.launch(Manifest.permission.CAMERA)
            }
        val listGenero = listOf("Rock", "Pop", "Jazz")
        val adapter = ArrayAdapter(requireContext(), R.layout.simple_list_item_1, listGenero)
        binding.genero.adapter = adapter

        binding.registro.setOnClickListener {
            val tituloN = binding.titulo.text.toString()
            val artistaN = binding.artista.text.toString()
            val valoracionN = binding.calificacion.rating.toFloat()
            //val genero

            binding.genero.setOnItemClickListener { parent, view, position, id ->
                genero = listGenero[position].toString()

            }
            val fotoN = rutaImagen
            val c = Cancion(
                titulo = tituloN,
                artista = artistaN,
                genero = genero!!,
                valoracion = valoracionN,
                foto = fotoN!!
            )
            val idNuevo = viewModel.insertaCancion(c)
            if (idNuevo != -1L) {
                Toast.makeText(requireContext(), "Exitos!!", Toast.LENGTH_LONG).show()
                parentFragmentManager.popBackStack()
            }else{
                Toast.makeText(requireContext(), "Problema con la insercion!!", Toast.LENGTH_LONG).show()
                return@setOnClickListener
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