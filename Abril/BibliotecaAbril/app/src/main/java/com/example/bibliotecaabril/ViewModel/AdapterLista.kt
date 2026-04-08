package com.example.bibliotecaabril.ViewModel

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import com.example.bibliotecaabril.R

import com.example.bibliotecaabril.model.Traslado

class AdapterLista(context: Context, private val lista: MutableList<Traslado>) :
    ArrayAdapter<Traslado>(context, 0, lista) {

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {

        val itemView = convertView
            ?: LayoutInflater.from(context).inflate(R.layout.item_lista, parent, false)

        val traslado = getItem(position)

        val origen = itemView.findViewById<TextView>(R.id.origen)
        val destino = itemView.findViewById<TextView>(R.id.destino)
        val fecha=itemView.findViewById<TextView>(R.id.fecha)


       origen.text=traslado?.origen.toString() ?: ""
        destino.text=traslado?.destino.toString() ?: ""
        fecha.text=traslado?.fecha.toString() ?: ""

        return itemView
    }

    fun actualizarLista(nuevaLista: List<Traslado>) {
        lista.clear()
        lista.addAll(nuevaLista)
        notifyDataSetChanged()
    }
}