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
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.activityViewModels
import com.example.sitioensayo.databinding.FragmentModificarBinding
import model.Sitio
import viewModel.AppViewModel

class ModificarFragment : Fragment() {

    private var _binding: FragmentModificarBinding? = null
    private val binding get() = _binding!!

    private var iconoTipo = ""
    private var iconoTipoInt = 0

    private val viewModel: AppViewModel by activityViewModels()

    private var bitmapFoto: Bitmap? = null
    private var uri: Uri? = null
    private var rutaImagen: String? = null

    private var sitioViejo: Sitio? = null

    private val launcherTomarFoto = registerForActivityResult(
        ActivityResultContracts.TakePicturePreview()
    ) { bitmap ->

        if (bitmap != null) {

            binding.imagenM.setImageBitmap(bitmap)
            bitmapFoto = bitmap

            val uriGuardada = guardarFotoEnGaleria(bitmap)

            if (uriGuardada != null) {
                uri = uriGuardada
                rutaImagen = uriGuardada.toString()

                Toast.makeText(
                    requireContext(),
                    "Foto guardada",
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

            binding.imagenM.setImageURI(uriResult)

            Toast.makeText(
                requireContext(),
                "Imagen seleccionada",
                Toast.LENGTH_SHORT
            ).show()
        }
    }

    private val launcherPermisoCamara = registerForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { concedido ->

        if (concedido) {
            launcherTomarFoto.launch(null)
        } else {
            Toast.makeText(
                requireContext(),
                "Permiso necesario",
                Toast.LENGTH_SHORT
            ).show()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentModificarBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val id = arguments?.getLong("id", -1L) ?: -1L

        if (id == -1L) return

        sitioViejo = viewModel.getPOrId(id)

        sitioViejo?.let { sitio ->

            binding.nombreM.setText(sitio.nombre)//OJOOOOOOOOOOOOOOOOOOO HINT NO,SETTEXT
            binding.direccionM.setText(sitio.direccion)
            binding.webM.setText(sitio.web)
            binding.telefonoM.setText(sitio.telefono)
            binding.descripcionM.setText(sitio.descripcion)

            binding.calificacionM.rating = sitio.calificacion

            binding.latitudM.setText(sitio.latitud.toString())
            binding.longitudM.setText(sitio.longitud.toString())

            binding.imagenM.setImageURI(
                Uri.parse(sitio.foto)
            )

            rutaImagen = sitio.foto
            iconoTipoInt = sitio.icono
        }

        val opciones = listOf("Museo", "Teatro")

        val adapter = ArrayAdapter(
            requireContext(),
            android.R.layout.simple_spinner_item,
            opciones
        )

        adapter.setDropDownViewResource(
            android.R.layout.simple_spinner_dropdown_item
        )

        binding.spinnertipoLugarM.adapter = adapter

        binding.spinnertipoLugarM.onItemSelectedListener =
            object : AdapterView.OnItemSelectedListener {

                override fun onItemSelected(
                    parent: AdapterView<*>,
                    view: View?,
                    pos: Int,
                    id: Long
                ) {

                    iconoTipo = opciones[pos]

                    iconoTipoInt =
                        when (iconoTipo) {
                            "Museo" -> R.drawable.m
                            "Teatro" -> R.drawable.t
                            else -> sitioViejo?.icono ?: 0
                        }
                }

                override fun onNothingSelected(parent: AdapterView<*>) {}
            }

        binding.fechaHoraM.setOnClickListener {
            mostrarCalendario()
        }

        binding.imagenM.setOnClickListener {
            mostrarOpcionesImagen()
        }

        binding.modificar.setOnClickListener {

            try {

                val sitio = sitioViejo ?: return@setOnClickListener

                val nombreN =
                    binding.nombreM.text.toString()
                        .ifEmpty { sitio.nombre }

                val direccionN =
                    binding.direccionM.text.toString()
                        .ifEmpty { sitio.direccion }

                val telefonoN =
                    binding.telefonoM.text.toString()
                        .ifEmpty { sitio.telefono }

                val fechaN =
                    binding.fechaelegidaM.text.toString()
                        .ifEmpty { sitio.fecha }

                val webN =
                    binding.webM.text.toString()
                        .ifEmpty { sitio.web }

                val descripcionN =
                    binding.descripcionM.text.toString()
                        .ifEmpty { sitio.descripcion }

                val fotoN = rutaImagen ?: sitio.foto

                val latitudN =
                    binding.latitudM.text.toString()
                        .toDoubleOrNull()
                        ?: sitio.latitud

                val longitudN =
                    binding.longitudM.text.toString()
                        .toDoubleOrNull()
                        ?: sitio.longitud

                val s = Sitio(
                    id = id,
                    nombre = nombreN,
                    direccion = direccionN,
                    icono = iconoTipoInt,
                    calificacion = binding.calificacionM.rating,
                    telefono = telefonoN,
                    fecha = fechaN,
                    web = webN,
                    descripcion = descripcionN,
                    foto = fotoN,
                    latitud = latitudN,
                    longitud = longitudN
                )

                viewModel.actualizarSitio(s)

                parentFragmentManager.popBackStack()

            } catch (e: Exception) {

                Toast.makeText(
                    requireContext(),
                    e.message,
                    Toast.LENGTH_LONG
                ).show()

                e.printStackTrace()
            }
        }
    }

    private fun mostrarCalendario() {

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

                        binding.fechaelegidaM.setText(fechaHora)
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

    private fun mostrarOpcionesImagen() {

        val opciones = arrayOf(
            "Hacer foto",
            "Elegir de galería"
        )

        AlertDialog.Builder(requireContext())
            .setTitle("Selecciona imagen")
            .setItems(opciones) { _, which ->

                when (which) {
                    0 -> launcherPermisoCamara.launch(
                        Manifest.permission.CAMERA
                    )

                    1 -> launcherGaleria.launch("image/*")
                }
            }
            .show()
    }

    private fun guardarFotoEnGaleria(bitmap: Bitmap): Uri? {

        val fileName =
            "pokemon_${System.currentTimeMillis()}.jpg"

        val resolver =
            requireContext().contentResolver

        val contentValues = ContentValues().apply {

            put(
                MediaStore.Images.Media.DISPLAY_NAME,
                fileName
            )

            put(
                MediaStore.Images.Media.MIME_TYPE,
                "image/jpeg"
            )

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {

                put(
                    MediaStore.Images.Media.RELATIVE_PATH,
                    "Pictures/MisEspectaculos"
                )
            }
        }

        val imageUri = resolver.insert(
            MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
            contentValues
        )

        imageUri?.let { uri ->

            try {

                resolver.openOutputStream(uri)
                    .use { outputStream ->

                        if (outputStream != null) {

                            bitmap.compress(
                                Bitmap.CompressFormat.JPEG,
                                90,
                                outputStream
                            )

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