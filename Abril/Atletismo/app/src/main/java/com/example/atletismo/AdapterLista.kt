package com.example.atletismo

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.TextView
import model.Competicion

class AdapterLista(context: Context, private val lista: MutableList<Competicion>) :
    ArrayAdapter<Competicion>(context, 0, lista) {

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {

        val itemView = convertView
            ?: LayoutInflater.from(context).inflate(R.layout.item_lista, parent, false)

        val competicion = getItem(position)

        val correo = itemView.findViewById<TextView>(R.id.correo)
        val nombre = itemView.findViewById<TextView>(R.id.nombre)
        val imagen=itemView.findViewById<ImageView>(R.id.imgItem)
        competicion?.let{
            correo.text=it.correo
            nombre.text=it.nombre
            imagen.setImageResource(it.imagen)
        }
        val uriCorreo = Uri.parse("mailto:${correo.text}")
        correo.setOnClickListener { val intent= Intent(Intent.ACTION_SENDTO, uriCorreo)
            val chooser=Intent.createChooser(intent,"Mandar con...")
            context.startActivity(chooser)
        }

        return itemView
    }

    fun actualizarLista(nuevaLista: List<Competicion>) {
        lista.clear()
        lista.addAll(nuevaLista)
        notifyDataSetChanged()
    }
}