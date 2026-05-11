package com.example.comidallevar

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.TextView
import model.Restaurante

class AdapterListaRestaurantea(
    context: Context,
    private val lista: MutableList<Restaurante>,
    private val onLlamar: (String) -> Unit
) :
    ArrayAdapter<Restaurante>(context, 0, lista) {

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {

        val itemView = convertView
            ?: LayoutInflater.from(context).inflate(R.layout.item_lista, parent, false)

        val restaurante = getItem(position)

        val nombre = itemView.findViewById<TextView>(R.id.nombre)
        val telefono = itemView.findViewById<TextView>(R.id.telefono)

        val imagen = itemView.findViewById<ImageView>(R.id.imagen)
        restaurante?.let {
            nombre.text = it.nombre
            telefono.text=it.telefono

            imagen.setImageResource(it.foto)

        }
        telefono.setOnClickListener { onLlamar(telefono.text.toString()) }


        return itemView
    }

    fun actualizarLista(nuevaLista: List<Restaurante
            >) {
        lista.clear()
        lista.addAll(nuevaLista)
        notifyDataSetChanged()
    }
}
