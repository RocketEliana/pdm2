package com.example.tareaprogramada

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.tareaprogramada.databinding.ActivityMainBinding
import com.example.tareaprogramada.viewModel.TareaViewModel
import kotlin.getValue

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val viewModel: TareaViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.toolbar)
        binding.add.setOnClickListener {
            android.widget.Toast.makeText(this, "Botón pulsado", android.widget.Toast.LENGTH_SHORT).show()
            val fragmentAdd= NuevoFragment()
            val transaction=supportFragmentManager.beginTransaction()
            transaction.replace(R.id.fragmento_principal,fragmentAdd).addToBackStack(null).commit()
        }
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.item_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.acercade -> {

                true
            }

            R.id.alfabetico -> {

                true
            }

            else ->  super.onOptionsItemSelected(item)//"Que se encargue Android"
        }
    }


}