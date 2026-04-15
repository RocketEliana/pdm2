package com.example.mislugares

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.RatingBar
import android.widget.TextView
import com.example.mislugares.model.Lugar

class AdapterLista(context: Context, private val lista: MutableList<Lugar>) :
    ArrayAdapter<Lugar>(context, 0, lista) {

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {

        val itemView = convertView
            ?: LayoutInflater.from(context).inflate(R.layout.item_lista, parent, false)

        val lugar = getItem(position)


        val nombre = itemView.findViewById<TextView>(R.id.nombre)
        val direccion = itemView.findViewById<TextView>(R.id.direccion)
        val valoracion = itemView.findViewById<RatingBar>(R.id.valoracion)
        val distancia=itemView.findViewById<TextView>(R.id.distancia)
        nombre.text=lugar?.nombre.toString() ?: ""
        direccion.text=lugar?.direccion.toString() ?: ""
        valoracion.rating=lugar?.calificacion?.toFloat() ?: 0.0f

        return itemView
    }

    fun actualizarLista(nuevaLista: List<Lugar>) {
        lista.clear()
        lista.addAll(nuevaLista)
        notifyDataSetChanged()
    }
}
