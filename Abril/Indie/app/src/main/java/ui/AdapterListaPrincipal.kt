package ui

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import com.example.mp.MainActivity
import com.example.mp.R
import model.Sala



class AdapterListaPrincipal(
    context: Context,
    private val lista: List<Sala>,
    private val onLlamar: (String) -> Unit   // callback, el Main decide qué hacer
) : ArrayAdapter<Sala>(context, 0, lista) {

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val itemView = convertView
            ?: LayoutInflater.from(context).inflate(R.layout.item_lista, parent, false)

        val sala = lista[position]

        val imagen   = itemView.findViewById<ImageView>(R.id.logoSala)
        val nombre   = itemView.findViewById<TextView>(R.id.nombre)
        val telefono = itemView.findViewById<TextView>(R.id.telefono)

        imagen.setImageResource(sala.imagen)
        nombre.text   = sala.nombre
        telefono.text = sala.telefono

        // El número queda capturado en el closure, sin variables compartidas
        telefono.setOnClickListener {
            onLlamar(sala.telefono)
        }

        return itemView
    }
}