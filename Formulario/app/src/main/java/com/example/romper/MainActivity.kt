package com.example.romper

import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.CheckBox
import android.widget.RadioButton
import android.widget.SeekBar
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.romper.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val listaPaises = listOf("España", "Italia", "Francia")
        val adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, listaPaises)
        binding.paises.adapter = adapter
        //para implementar una interfaz que tiene más de un
       // método (como OnSeekBarChangeListener), no puedes usar una simple lambda { }.
        //Necesitas crear lo que llamamos un "object expression" (un objeto anónimo).*/
        val boletin = binding.boletin.isChecked
        binding.seekBar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {

            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                // Se ejecuta cada vez que mueves la barra
                // 'progress' es el valor actual (de 0 a 10)
                binding.valorS.text = progress.toString()
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {
                // Se ejecuta cuando el usuario TOCA la barra
            }

            override fun onStopTrackingTouch(seekBar: SeekBar?) {
                // Se ejecuta cuando el usuario SUELTA la barra
            }
        })
        binding.aceptar.setOnClickListener { view ->
            val nombre = binding.nombre.text.toString()
            val apellidos = binding.apellidos.text.toString()
            val mail = binding.mail.text.toString()

            // 1. PRIMERO VALIDAMOS: Si hay algo vacío, cortamos aquí
            if (nombre.isBlank() || apellidos.isBlank() || mail.isBlank()) {
                binding.resumen.text = "Por favor, rellena todos los campos (Nombre, Apellidos y Mail)"
                return@setOnClickListener
            }

            // 2. AHORA QUE SABEMOS QUE NO ESTÁ VACÍO, RECOGEMOS LO DEMÁS

            // Evitamos el crash si no hay sexo seleccionado
            val idRadio = binding.botonGroup.checkedRadioButtonId
            val sexoseleccionado = if (idRadio != -1) {
                findViewById<RadioButton>(idRadio).text.toString()
            } else {
                "No especificado"
            }

            val paisActual = binding.paises.selectedItem.toString()

            // Corregido: eliminada la música repetida y añadido deporte
            val listaHobbieSeleccionados = listOf<CheckBox>(
                binding.musica,
                binding.arte,
                binding.deporte,
                binding.lectura
            ).filter { it.isChecked }.map { it.text.toString() }

            val satisfaccion = binding.valorS.text.toString()

            // Leemos el boletín JUSTO AHORA para que sea el valor real
            val resultadoBoletin = if (binding.boletin.isChecked) "si" else "no"

            // 3. PINTAMOS EL RESULTADO
            binding.resumen.text = "${nombre}, con apellidos: ${apellidos}, mail ${mail} y sexo ${sexoseleccionado}, " +
                    "tiene como hobbies ${listaHobbieSeleccionados}, pais ${paisActual} nivel de satisfaccion ${satisfaccion}, y ${resultadoBoletin} quiere boletin"
        }

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }
}



