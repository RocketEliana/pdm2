package com.example.bibliotecaabril.ViewModel

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.TextView
import com.example.bibliotecaabril.R
import com.example.bibliotecaabril.model.Biblioteca

class AdapterOrigenSpinner(context: Context, private val lista: MutableList<Biblioteca>) :
    ArrayAdapter<Biblioteca>(context, 0, lista) {

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {

        val itemView = convertView
            ?: LayoutInflater.from(context).inflate(R.layout.item_spinner_origen, parent, false)

        val biblioteca = getItem(position)

        val imagen = itemView.findViewById<ImageView>(R.id.imagen)
        val nombre = itemView.findViewById<TextView>(R.id.nombre)
        val correo = itemView.findViewById<TextView>(R.id.correo)



        imagen.setImageResource(biblioteca?.imagen ?: 0)
        nombre.text = biblioteca?.nombre ?: ""
        correo.text=biblioteca?.correo ?: ""
        val uriCorreo = Uri.parse("mailto:${correo.text}")
        correo.setOnClickListener { val intent= Intent(Intent.ACTION_SENDTO,uriCorreo)
                                    val chooser=Intent.createChooser(intent,"Mandar con...")
                                     context.startActivity(chooser)
                                    }

        return itemView
    }

    /**
     * Vista cuando el Spinner se despliega
     */
    override fun getDropDownView(position: Int, convertView: View?, parent: ViewGroup): View {
        return getView(position, convertView, parent)
    }

    fun actualizarLista(nuevaLista: List<Biblioteca>) {
        lista.clear()
        lista.addAll(nuevaLista)
        notifyDataSetChanged()
    }
}