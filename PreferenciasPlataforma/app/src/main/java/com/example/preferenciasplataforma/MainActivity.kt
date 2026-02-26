package com.example.preferenciasplataforma

import android.content.Context
import android.graphics.Color
import android.os.Bundle
import android.widget.SeekBar
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.example.preferenciasplataforma.databinding.ActivityMainBinding



class MainActivity : AppCompatActivity() {
    companion object{
        const val PREF_CONFIGURACION="prefsConfiguracion"
        const val PREF_NOTA="preferenceNota"
        const val SIZE="size"
        const val NOTA="nota"
        const val COLOR="color"
        const val FECHA="fecha"
    }


    private lateinit var binding: ActivityMainBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val pref_nota=getSharedPreferences(PREF_NOTA,Context.MODE_PRIVATE)
        val pref_configuracion=getSharedPreferences(PREF_CONFIGURACION, Context.MODE_PRIVATE)
        val prefSize=pref_configuracion.getFloat(SIZE,6.0f)
        val prefColor=pref_configuracion.getString(COLOR,"#3F51B5")
        val nota=pref_nota.getInt(NOTA,1)
        val fecha=pref_configuracion.getString(FECHA, java.time.LocalDate.now().toString())
        binding.nota.setTextSize(prefSize)
        binding.nota.text=nota.toString().toInt()
        binding.main.setBackgroundColor(Color.parseColor(prefColor))
        binding.fecha.text=fecha
        var sizeNota=6
        binding.seekTamamioFuente.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {

            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                // Se ejecuta cada vez que mueves la barra
                // 'progress' es el valor actual (de 0 a 10)
                sizeNota=progress

            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {
                // Se ejecuta cuando el usuario TOCA la barra
            }

            override fun onStopTrackingTouch(seekBar: SeekBar?) {
                // Se ejecuta cuando el usuario SUELTA la barra
            }
        })
        binding.guardar.setOnClickListener {
                   pref_nota.edit().putInt(NOTA,binding.nota.text.toInt).apply()
                   val color=binding.grupoColores.checkedRadioButtonId
                  var colorString=""
                  when(color){
                      R.id.azul->colorString="#3F51B5"
                      R.id.verde->colorString="#00796B"
                      R.id.amarillo->colorString="#00796B"
                  }
            val fechaActual= java.time.LocalDate.now()
            pref_configuracion.edit().putString(COLOR,colorString).putFloat(SIZE,sizeNota.toFloat()).putString(FECHA,fechaActual.toString()).apply()
        }
        binding.limpiar.setOnClickListener {
            pref_nota.edit().clear().apply()
            pref_configuracion.edit().clear().apply()
        }

    }
}