package com.example.lwes

import android.Manifest
import android.app.DatePickerDialog
import android.app.TimePickerDialog
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
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.activityViewModels
import com.example.lwes.databinding.FragmentNuevoBinding
import model.Lugar
import view.AppViewModel
import java.util.Calendar

class NuevoFragment : Fragment() {
    private var _binding: FragmentNuevoBinding? = null
    private val binding get() = _binding!!
    private val viewModel: AppViewModel by activityViewModels ()
    private var bitmapFoto: Bitmap? = null
    private var uri: Uri? = null
    private var rutaImagen: String? = null
    private val launcherTomarFoto = registerForActivityResult(
        ActivityResultContracts.TakePicturePreview()
    ) { bitmap ->
        if (bitmap != null) {
            binding.imagen
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
        _binding = FragmentNuevoBinding.inflate(inflater, container, false)
        return binding.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.imagen
            .setOnClickListener {
                launcherPermisoCamara.launch(Manifest.permission.CAMERA)
            }
        binding.fechaHora.setOnClickListener { mostrarCalendario() }
        binding.registro.setOnClickListener {
            val nombre=binding.nombre.text.toString()
            val categoria=binding.categoria.text.toString()
            val fex=binding.fechaElegida.text.toString()
            val lat=binding.latitud.text.toString().toDoubleOrNull()
            val lon=binding.longitud.text.toString().toDoubleOrNull()
            val valoracion=binding.calificacion.rating
            val lugar= Lugar(nombre=nombre, fecha = fex, foto = rutaImagen!!, valoracion = valoracion, latitud = lat!!, longitud = lon!!, categoria = categoria)
            viewModel.insertarLug(lugar)
            parentFragmentManager.popBackStack()
        }




    }
    private fun guardarFotoEnGaleria(bitmap: Bitmap): Uri? {
        val fileName = "lugar_${System.currentTimeMillis()}.jpg"
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
    private fun mostrarCalendario() {
        val calendario = Calendar.getInstance()
        DatePickerDialog(
            requireContext(),
            { _, año, mes, dia ->
                TimePickerDialog(
                    requireContext(),
                    { _, hora, min ->
                        val fechaHora =
                            String.format("%02d/%02d/%d %02d:%02d", dia, mes + 1, año, hora, min)
                        binding.fechaElegida.setText(fechaHora)
                    },
                    calendario.get(Calendar.HOUR_OF_DAY),
                    calendario.get(Calendar.MINUTE),
                    true
                ).show()
            },
            calendario.get(Calendar.YEAR),
            calendario.get(Calendar.MONTH),
            calendario.get(Calendar.DAY_OF_MONTH)
        ).show()
    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}