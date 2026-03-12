package com.example.loginpersonalizado

import android.Manifest
import android.content.ContentValues
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.SeekBar
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.activityViewModels
import com.example.loginpersonalizado.databinding.FragmentNewPokemonBinding
import com.example.loginpersonalizado.model.Pokemon
import com.example.loginpersonalizado.viewModel.bdViewModel
import kotlinx.coroutines.NonDisposableHandle.parent

class NewPokemonFragment : Fragment() {
    private var _binding: FragmentNewPokemonBinding? = null
    private val binding get() = _binding!!
    private var bitmapFoto: Bitmap? = null
    private val bdView: bdViewModel by activityViewModels()

    // Launcher para la Cámara
    private val launcherTomarFoto = registerForActivityResult(
        ActivityResultContracts.TakePicturePreview()
    ) { bitmap ->
        if (bitmap != null) {
            binding.fotoPokemon
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
        _binding = FragmentNewPokemonBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.fotoPokemon.setOnClickListener {
            launcherPermisoCamara.launch(Manifest.permission.CAMERA)
        }
        //nombre,tipo,nivel,foto
        var modoSeleccionado: String = ""

// 2. Configurar la lista y el adaptador
        val lista = listOf("AGUA", "FUEGO", "TIERRA", "AIRE")
        val adapter = ArrayAdapter(requireContext(), android.R.layout.simple_list_item_1, lista)
        binding.listaModoPokemon.adapter = adapter

// 3. EL CAMBIO CLAVE: Usar setOnItemClickListener para Listas
        binding.listaModoPokemon.setOnItemClickListener { parent, view, position, id ->
            // Aquí obtenemos el texto de la posición tocada
            modoSeleccionado = adapter.getItem(position).toString()
        }

               binding.seeknivel.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {

            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                // Se ejecuta cada vez que mueves la barra
                // 'progress' es el valor actual (de 0 a 10)
                binding.nivelPOkemonSeek
                    .text = progress.toString()
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {
                // Se ejecuta cuando el usuario TOCA la barra
            }

            override fun onStopTrackingTouch(seekBar: SeekBar?) {
                // Se ejecuta cuando el usuario SUELTA la barra
            }
        })
        binding.guardar.setOnClickListener {
            // 1. Verificamos que haya foto
            if (bitmapFoto == null) {
                Toast.makeText(requireContext(), "Por favor, haz una foto primero", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val nombre = binding.nombre.text.toString()
            if (nombre.isEmpty()) {
                Toast.makeText(requireContext(), "Introduce un nombre", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            // 2. Guardamos la foto y obtenemos la URI
            val uriFoto = guardarFotoEnGaleria(bitmapFoto!!)

            if (uriFoto != null) {
                val nivelStr = binding.nivelPOkemonSeek.text.toString()
                val nivel = nivelStr.toIntOrNull() ?: 0
                val modoPokemon = modoSeleccionado

                // Creamos el objeto (usando uriFoto.toString())
                val pokemon = Pokemon(
                    nombre = nombre,
                    tipo = modoPokemon,
                    nivel = nivel,
                    foto = uriFoto.toString()
                )

                // 3. Insertamos
                bdView.insertarPokemon(pokemon)

                // 4. Volvemos atrás
                requireActivity().onBackPressedDispatcher.onBackPressed()
            } else {
                Toast.makeText(requireContext(), "Error al guardar la imagen en galería", Toast.LENGTH_SHORT).show()
            }
        }
    }


        override fun onDestroyView() {
            super.onDestroyView()
            _binding = null
        }

        private fun guardarFotoEnGaleria(bitmap: Bitmap): android.net.Uri? {
// 1. Definimos un nombre único para el archivo usando la fecha actual en milisegundos
// Esto evita que si haces dos fotos seguidas se llamen igual.
            val fileName = "pokemon_${System.currentTimeMillis()}.jpg"

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



}