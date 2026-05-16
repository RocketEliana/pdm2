package com.example.sitioensayo

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.RatingBar
import android.widget.TextView
import model.Sitio

class AdapterLista(context: Context, private val lista: MutableList<Sitio>) :
    ArrayAdapter<Sitio>(context, 0, lista) {

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {

        val itemView = convertView
            ?: LayoutInflater.from(context).inflate(R.layout.item_lista, parent, false)

        val sitio = getItem(position)

        val direccion = itemView.findViewById<TextView>(R.id.direccion)
        val nombre = itemView.findViewById<TextView>(R.id.nombre)
        val distancia = itemView.findViewById<TextView>(R.id.distancia)
        val icono = itemView.findViewById<ImageView>(R.id.imgTipo)
        val  cali=itemView.findViewById<RatingBar>(R.id.calificacionNueva)

        sitio?.let{
            direccion.text=it.direccion
            nombre.text=it.nombre
            if (it.icono != 0) {
                icono.setImageResource(it.icono)
            }
            cali.rating=it.calificacion
        }

        return itemView
    }

    fun actualizarLista(nuevaLista: List<Sitio>) {
        lista.clear()
        lista.addAll(nuevaLista)
        notifyDataSetChanged()
    }
}