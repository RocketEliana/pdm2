package com.example.sitioensayo

import android.Manifest
import android.app.AlertDialog
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
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.example.sitioensayo.databinding.FragmentNuevoBinding
import model.Sitio
import viewModel.AppViewModel

class NuevoFragment : Fragment() {
    private var _binding: FragmentNuevoBinding? = null
    private val binding get() = _binding!!
    private var iconoTipoInt: Int = 0
    private var iconoTipo: String = ""

    private val viewModel: AppViewModel by activityViewModels()
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

            val uriGuardada = guardarFotoEnGaleria(bitmap)
            if (uriGuardada != null) {
                uri = uriGuardada
                rutaImagen = uriGuardada.toString()

                Toast.makeText(
                    requireContext(),
                    "Foto guardada en: $rutaImagen",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }
    private val launcherGaleria = registerForActivityResult(
        ActivityResultContracts.GetContent()
    ) { uriResult ->
        if (uriResult != null) {
            uri = uriResult
            rutaImagen = uriResult.toString()

            binding.imagen.setImageURI(uriResult)

            Toast.makeText(
                requireContext(),
                "Imagen seleccionada de galería",
                Toast.LENGTH_SHORT
            ).show()
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

        val opciones = listOf("Museo", "Teatro")
        val adapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, opciones)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.spinnertipoLugar.adapter = adapter

        binding.spinnertipoLugar.onItemSelectedListener =
            object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(
                    parent: AdapterView<*>,
                    view: View?,
                    pos: Int,
                    id: Long
                ) {
                    iconoTipo = opciones[pos]
                    iconoTipoInt =//aqui,si no no se actualiza
                        when (iconoTipo) {
                            "Museo" -> R.drawable.m
                            "Teatro" -> R.drawable.t
                            else->0
                        }
                }

                override fun onNothingSelected(parent: AdapterView<*>) {}
            }

        binding.imagen.setOnClickListener {
            mostrarOpcionesImagen()
        }
        binding.fechaHora.setOnClickListener {mostrarCalendario()

        }
// Leer valor en cualquier momento:
        val seleccionado = binding.spinnertipoLugar.selectedItem.toString()

        binding.registro.setOnClickListener {
            val nombreN=binding.nombre.text.toString()
            val direccionN=binding.direccion.text.toString()
            val iconoN=iconoTipoInt
            val calificacionN=binding.calificacion.rating
            val telefonoN=binding.telefono.text.toString()
            val fechaN=binding.fechaelegida.text.toString()
            val webN=binding.web.text.toString()
            val descripcionN=binding.descripcion.text.toString()
            val fotoN=rutaImagen.toString()
            val latitudN=binding.latitud.text.toString().toDouble()
            val longitudN=binding.longitud.text.toString().toDouble()
        /*    if (
                !nombreN.isEmpty() ||
                !direccionN.isEmpty() ||
                !telefonoN.isEmpty() ||
                !fechaN.isEmpty() ||
                !webN.isEmpty() ||
                !descripcionN.isEmpty() ||
                fotoN != null||
                latitudN !=null ||
                longitudN != null ||
                iconoN != 0
            ) {



        }*/
            val s = Sitio(nombre =nombreN , direccion = direccionN, icono = iconoN, calificacion = calificacionN, telefono = telefonoN, fecha = fechaN, web = webN, descripcion = descripcionN,foto=fotoN, latitud = latitudN, longitud = longitudN)
            viewModel.insertarSitio(s)
            parentFragmentManager.popBackStack()
        }


    }
    private fun mostrarOpcionesImagen() {
        val opciones = arrayOf("Hacer foto", "Elegir de galería")

        AlertDialog.Builder(requireContext())
            .setTitle("Selecciona imagen")
            .setItems(opciones) { _, which ->
                when (which) {
                    0 -> launcherPermisoCamara.launch(Manifest.permission.CAMERA)
                    1 -> launcherGaleria.launch("image/*")
                }
            }
            .show()
    }
    private fun guardarFotoEnGaleria(bitmap: Bitmap): Uri? {
        val fileName = "pokemon_${System.currentTimeMillis()}.jpg"
        val resolver = requireContext().contentResolver

        val contentValues = ContentValues().apply {
            put(MediaStore.Images.Media.DISPLAY_NAME, fileName)
            put(MediaStore.Images.Media.MIME_TYPE, "image/jpeg")
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                put(MediaStore.Images.Media.RELATIVE_PATH, "Pictures/MisEspectaculos")
            }
        }

        val imageUri = resolver.insert(
            MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
            contentValues
        )

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
                        binding.fechaelegida
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
        ).show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}