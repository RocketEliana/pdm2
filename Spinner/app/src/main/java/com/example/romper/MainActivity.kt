package com.example.romper

import android.os.Bundle
import android.view.View
import android.widget.AdapterView
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
    private lateinit var adapter: Adapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val listaAdas=listOf<Hada>(
            Hada(R.drawable.loto,"Loto"),
            Hada(R.drawable.luna,"luna"),
            Hada(R.drawable.tierra,"tierra")
        )
        adapter= Adapter(this,listaAdas)
        binding.spinner.adapter=adapter
        binding.spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                //el objeto que te devuelve adapter.getItem(position) es efectivamente un objeto de tipo Hada,
                val hada = adapter.getItem(position)
                hada?.let { binding.text.text=it.nombre }//asegurate siempre de que controlas los nulos
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {}
        }



        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }
}



