package com.example.drones

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.TextView
import model.Instituto

class AdapterOrigen (context: Context, private val lista: MutableList<Instituto>, private val onLlamar: (String) -> Unit) :
    ArrayAdapter<Instituto>(context, 0, lista) {

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {

        val itemView = convertView
            ?: LayoutInflater.from(context).inflate(R.layout.item_lista, parent, false)

        val insti = getItem(position)

        val imagen = itemView.findViewById<ImageView>(R.id.imgItem)
        val nombre = itemView.findViewById<TextView>(R.id.nombre)
        val telefono = itemView.findViewById<TextView>(R.id.telefono)
        telefono.setOnClickListener { insti?.let { onLlamar(it.telefono) } }

        nombre.text = insti?.nombre
        telefono.text=insti?.telefono
        val icono=insti?.icono ?: 0
        imagen.setImageResource(icono!!)


        return itemView
    }

    fun actualizarLista(nuevaLista: List<Instituto>) {
        lista.clear()
        lista.addAll(nuevaLista)
        notifyDataSetChanged()
    }
}