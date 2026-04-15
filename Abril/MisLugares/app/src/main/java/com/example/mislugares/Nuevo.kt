package com.example.mislugares
import android.util.Log

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
import android.view.View
import android.widget.AdapterView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.mislugares.databinding.ActivityMainBinding
import com.example.mislugares.databinding.ActivityNuevoBinding
import com.example.mislugares.model.Lugar
import com.example.mislugares.viewModel.AppViewModel

class Nuevo : AppCompatActivity() {
    private lateinit var binding: ActivityNuevoBinding
    private val viewModel: AppViewModel by viewModels()
    private lateinit var adapter: AdapterIcon
    private var tipoLugarIcon: Int = -1 //el modelo espera un int no int?
    private var fechaL: String? = null
    private var bitmapFoto: Bitmap? = null
    private var uri: Uri? = null
    private var rutaImagen: String? = null
    private val launcherTomarFoto = registerForActivityResult(
        ActivityResultContracts.TakePicturePreview()
    ) { bitmap ->
        if (bitmap != null) {
            binding.imagen.setImageBitmap(bitmap)
            bitmapFoto = bitmap
            // OPCIONAL: Guardar automáticamente al tomarla
            uri = guardarFotoEnGaleria(bitmap)
            if (uri != null) {
                rutaImagen = uri.toString()
                Toast.makeText(this, "Foto guardada en Galería", Toast.LENGTH_SHORT).show()
            }

        }
    }

    // Launcher para Permisos
    private val launcherPermisoCamara = registerForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { concedido ->
        if (concedido) launcherTomarFoto.launch(null)
        else Toast.makeText(this, "Permiso necesario", Toast.LENGTH_SHORT).show()
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityNuevoBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val listaIcono = listOf<TipoLugar>(
            TipoLugar(R.drawable.res, "restaurante"),
            TipoLugar(R.drawable.pla, "playa"),
            TipoLugar(R.drawable.mon, "moontaña"),
            TipoLugar(R.drawable.mus, "museo")
        )
        adapter = AdapterIcon(this, listaIcono)
        binding.tipoLugar.adapter = adapter
        binding.tipoLugar.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                val tipoLugar = adapter.getItem(position)
                tipoLugarIcon = tipoLugar?.imagen ?: -1
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {}
        }

        binding.fechaHora.setOnClickListener {
            mostrarCalendario { fechaFinal ->

                fechaL = fechaFinal
            }
        }
        binding.imagen
            .setOnClickListener {
                launcherPermisoCamara.launch(Manifest.permission.CAMERA)

            }


        //aqui deberias controlar que no hay nada vacio....

        binding.registro.setOnClickListener {

            val nombreL = binding.nombre.text.toString()
            val direccionL = binding.direccion.text.toString()
            val webL = binding.web.text.toString()
            val telefonoL = binding.telefono.text.toString()
            val descripcionL = binding.descripcion.text.toString()
            val calificacionL = binding.calificacion.rating.toFloat()
            val longitudL = binding.longitud.text.toString().toDoubleOrNull() ?: 0.0
            val latitudL = binding.latitud.text.toString().toDoubleOrNull() ?: 0.0
            if( nombreL.isNotEmpty() &&
                direccionL.isNotEmpty() &&
                telefonoL.isNotEmpty() &&
                webL.isNotEmpty() &&
                descripcionL.isNotEmpty() &&
                tipoLugarIcon != -1 &&
                fechaL != null ) {
                val lugar = Lugar(
                    nombre = nombreL,
                    tipoIncon = tipoLugarIcon,
                    direccion = direccionL,
                    telefono = telefonoL,
                    web = webL,
                    descripcion = descripcionL,
                    fecha = fechaL!!,
                    calificacion = calificacionL,
                    foto = rutaImagen ?: "",
                    latitud = latitudL,
                    longitud = longitudL
                )


                if (viewModel.insertarL(lugar) != -1L) {
                    Log.d("DB_CHECK", "¡Éxito al insertar!")
                    finish()
                } else {
                    Log.e("DB_CHECK", "Error: No se pudo insertar.")
                    return@setOnClickListener
                }
            }
            else{
                Toast.makeText(this,"faltan campos por rellenar", Toast.LENGTH_SHORT).show();
            }
        }







        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }

    private fun mostrarCalendario(callback: (String) -> Unit) {
        val calendario = Calendar.getInstance()

        DatePickerDialog(
            this,
            { _, año, mes, dia ->
                TimePickerDialog(
                    this,
                    { _, hora, min ->
                        // Formateamos el resultado

                        val fechaSeleccionada =
                            String.format("%d/%02d/%02d %02d:%02d", año, mes + 1, dia, hora, min)

                        // Ejecutamos el callback con el resultado
                        callback(fechaSeleccionada)

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

    private fun guardarFotoEnGaleria(bitmap: Bitmap): Uri? {
        val fileName = "lugar_${System.currentTimeMillis()}.jpg"
        val resolver = contentResolver
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
}

