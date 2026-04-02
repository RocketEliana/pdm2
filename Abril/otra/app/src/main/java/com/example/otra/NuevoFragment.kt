package com.example.otra

import android.Manifest
import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.ContentValues
import android.graphics.Bitmap
import android.icu.util.Calendar
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.example.otra.databinding.FragmentNuevoBinding
import model.Espectaculo
import viewModelApp.AppViewModel


class NuevoFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var _binding: FragmentNuevoBinding? = null
    private val binding get() = _binding!!
    private val viewModel: AppViewModel by activityViewModels()
    private var bitmapFoto: Bitmap? = null
    private var uri:Uri?=null
    private var icono:Int=0
    private lateinit var adapterSpinner: AdapterSpinner

    private val launcherTomarFoto = registerForActivityResult(
        ActivityResultContracts.TakePicturePreview()
    ) { bitmap ->
        if (bitmap != null) {
            binding.imagen
                .setImageBitmap(bitmap)
            bitmapFoto = bitmap
            // OPCIONAL: Guardar automáticamente al tomarla
             uri = guardarFotoEnGaleria(bitmap)
            if (uri != null) {
                Toast.makeText(requireContext(), "Foto guardada en Galería", Toast.LENGTH_SHORT).show()
            }

        }
    }

    // Launcher para Permisos
    private val launcherPermisoCamara = registerForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { concedido ->
        if (concedido) launcherTomarFoto.launch(null)
        else Toast.makeText(requireContext(), "Permiso necesario", Toast.LENGTH_SHORT).show()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentNuevoBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.imagen
            .setOnClickListener {
                launcherPermisoCamara.launch(Manifest.permission.CAMERA)
            }
        binding.fecha.setOnClickListener {
            val calendario = Calendar.getInstance()
            DatePickerDialog(
                requireContext(),
                { _, año, mes, dia ->
                    TimePickerDialog(
                        requireContext(),
                        { _, hora, min ->
                            val fechaHora = String.format(
                                "%02d/%02d/%d %02d:%02d",
                                dia,
                                mes + 1,
                                año,
                                hora,
                                min
                            )
                            binding.fecha
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
            ).show() }
        val listaIconos=listOf<Int>(R.drawable._051673,R.drawable.e)
        adapterSpinner= AdapterSpinner(requireContext(),listaIconos)
        binding.spinner.adapter=adapterSpinner
        binding.spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
               icono= listaIconos[position]


            }

            override fun onNothingSelected(parent: AdapterView<*>?) {}
        }
        binding.registro.setOnClickListener {
            val nombreE=binding.nombre.text.toString()
            val fecha=binding.fecha.text.toString()


            val espectaculo= Espectaculo(nombre = nombreE, foto = uri.toString(), icono = icono, fecha = fecha)
            viewModel.insertarEspectaculo(espectaculo)
            parentFragmentManager.popBackStack()
        }

    }

    private fun guardarFotoEnGaleria(bitmap: Bitmap): Uri? {
        val fileName = "espectaculo_${System.currentTimeMillis()}.jpg"
        val resolver = requireContext().contentResolver
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
                        return uri // Devolvemos la URI si todo salió bien
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
//<uses-permission android:name="android.permission.CAMERA" />

