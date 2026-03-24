package com.example.rutas

import android.Manifest
import android.content.ContentValues
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresPermission
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.example.rutas.databinding.FragmentNuevoBinding
import model.Ruta
import viewModel.RutaViewModel

class NuevoFragment : Fragment() {
    private var _binding: FragmentNuevoBinding? = null
    private val binding get() = _binding!!
    private var bitmapFoto: Bitmap? = null
    private val viewModel: RutaViewModel by activityViewModels()
    private val launcherLocalizacion = registerForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { isGranted ->
        if (isGranted) {
            Toast.makeText(requireContext(), "Localización concedida", Toast.LENGTH_SHORT).show()

        } else {
            Toast.makeText(requireContext(), "Localización denegada", Toast.LENGTH_SHORT).show()
        }
    }
    private val launcherTomarFoto = registerForActivityResult(
        ActivityResultContracts.TakePicturePreview()
    ) { bitmap ->
        if (bitmap != null) {
            binding.foto
                .setImageBitmap(bitmap)
            bitmapFoto = bitmap

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
        comprobarPermisoLocalizacion()
        val fragment = MapsAdd()
        childFragmentManager.beginTransaction().replace(R.id.mapaEdit, fragment).commit()
        childFragmentManager.setFragmentResultListener("ubicacion", this) { _, bundle ->
            val lat = bundle.getDouble("latitud")
            val lng = bundle.getDouble("longitud")

            binding.latitud.setText(lat.toString())
            binding.longitud.setText(lng.toString())
        }
        binding.foto.setOnClickListener {
            launcherPermisoCamara.launch(Manifest.permission.CAMERA)

        }
        binding.aniadir.setOnClickListener {
            if (bitmapFoto != null) {
                var urifoto = guardarFotoEnGaleria(bitmapFoto!!)
                val nombre = binding.nombre.text.toString()
                val provincia = binding.provincia.text.toString()
                val dificultad = binding.ratingBar.rating.toInt()
                val latitud = binding.latitud.text.toString().toDouble()
                val longitud = binding.longitud.text.toString().toDouble()
                val web = binding.web.toString()
                val ruta = Ruta(
                    imagen = urifoto.toString(),
                    nombre = nombre,
                    provincia = provincia,
                    dificultad = dificultad,
                    latitud = latitud,
                    longitud = longitud,
                    web = web
                )
                viewModel.insertarRuta(ruta)
                parentFragmentManager.popBackStack()
                if (ContextCompat.checkSelfPermission(
                        requireContext(),
                        Manifest.permission.POST_NOTIFICATIONS
                    ) == PackageManager.PERMISSION_GRANTED
                ) {
                    mostrarNotificacion(ruta.nombre)
                }

            }
        }


    }

    private fun comprobarPermisoLocalizacion() {
        val permiso = Manifest.permission.ACCESS_FINE_LOCATION
        when {
            ContextCompat.checkSelfPermission(
                requireContext(),
                permiso
            ) == PackageManager.PERMISSION_GRANTED -> {
                Toast.makeText(requireContext(), "Localización ya concedida", Toast.LENGTH_SHORT)
                    .show()

            }

            else -> {
                launcherLocalizacion.launch(permiso)
            }
        }
    }

    @RequiresPermission(Manifest.permission.POST_NOTIFICATIONS)
    fun mostrarNotificacion(nombre: String) {

        val builder = NotificationCompat.Builder(requireContext(), "aniadir_canal")
            .setSmallIcon(android.R.drawable.ic_dialog_alert)
            .setContentTitle("Ruta añadida")
            .setContentText(nombre)
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)

        NotificationManagerCompat.from(requireContext())
            .notify(System.currentTimeMillis().toInt(), builder.build())
    }

    private fun guardarFotoEnGaleria(bitmap: Bitmap): android.net.Uri? {
// 1. Definimos un nombre único para el archivo usando la fecha actual en milisegundos
// Esto evita que si haces dos fotos seguidas se llamen igual.
        val fileName = "ruta_${System.currentTimeMillis()}.jpg"

// 2. Obtenemos el "gestor de contenido" del sistema.
// Como estamos en un Fragment, usamos requireContext() para poder acceder a él.
        val resolver = requireContext().contentResolver

// 3. Creamos una "ficha de datos" (ContentValues) para decirle al sistema qué vamos a guardar.
        val contentValues = ContentValues().apply {
            // Nombre que se verá en la galería
            put(android.provider.MediaStore.Images.Media.DISPLAY_NAME, fileName)
            // Tipo de archivo (MIME type)
            put(android.provider.MediaStore.Images.Media.MIME_TYPE, "image/jpeg")

            // Si el móvil es Android 10 (API 29) o superior, podemos elegir la carpeta exacta.
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.Q) {
                put(
                    android.provider.MediaStore.Images.Media.RELATIVE_PATH,
                    "Pictures/MisPokemons"
                )
            }
        }

// 4. Pedimos permiso al sistema para "insertar" una nueva imagen en la galería pública.
// Esto nos devuelve una URI (una dirección o "enlace") al hueco que Android nos ha reservado.
        val imageUri = resolver.insert(
            android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
            contentValues
        )

// 5. Si Android nos dio la dirección (el hueco no es nulo), procedemos a escribir los datos.
        imageUri?.let { uri ->
            try {
                // Abrimos una "tubería" (OutputStream) hacia esa dirección URI.
                // El '.use' asegura que la tubería se cierre sola al terminar, para no malgastar memoria.
                resolver.openOutputStream(uri).use { outputStream ->
                    if (outputStream != null) {
                        // Cogemos el bitmap (la foto en RAM) y la "estrujamos" (comprimimos)
                        // para enviarla por la tubería en formato JPEG con calidad 90.
                        bitmap.compress(Bitmap.CompressFormat.JPEG, 90, outputStream)
                    }
                }
            } catch (e: Exception) {
                // Si algo falla (memoria llena, etc.), imprimimos el error y devolvemos nulo.
                e.printStackTrace()
                return null
            }
        }

// 6. Devolvemos la dirección final de la foto.
// Esta dirección es la que deberías guardar luego en tu base de datos SQL.
        return imageUri
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}