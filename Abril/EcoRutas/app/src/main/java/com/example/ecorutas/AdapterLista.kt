package com.example.ecorutas

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import model.Viaje


class AdapterLista(context: Context, private val lista: MutableList<Viaje>) :
    ArrayAdapter<Viaje>(context, 0, lista) {

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {

        val itemView = convertView
            ?: LayoutInflater.from(context).inflate(R.layout.item_lista, parent, false)

        val viaje = getItem(position)

        val origen = itemView.findViewById<TextView>(R.id.origen)
        val destino = itemView.findViewById<TextView>(R.id.destino)
        val fecha = itemView.findViewById<TextView>(R.id.fecha)
        viaje?.let {
            origen.text=it.origen
            destino.text=it.destino
            fecha.text=it.fecha
        }

        return itemView
    }

    fun actualizarLista(nuevaLista: List<Viaje>) {
        lista.clear()
        lista.addAll(nuevaLista)
        notifyDataSetChanged()
    }
}