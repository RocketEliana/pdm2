package com.example.musicplayer

import android.content.Context
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.TextView
import model.Cancion

class AdapterSpinner(context: Context, private val lista: MutableList<Cancion>) :
    ArrayAdapter<Cancion>(context, 0, lista) {

    /**
     * Dibuja cada fila del Spinner (vista cerrada o lista)
     */
    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {

        val itemView = convertView
            ?: LayoutInflater.from(context).inflate(R.layout.item_vista, parent, false)

        val cancion = getItem(position)

        val imagen = itemView.findViewById<ImageView>(R.id.imgItem)
        val nombre = itemView.findViewById<TextView>(R.id.titulo)

        imagen.setImageURI(Uri.parse(cancion?.foto))
        nombre.text =cancion?.titulo

        return itemView
    }

    /**
     * Vista cuando el Spinner se despliega
     */
    override fun getDropDownView(position: Int, convertView: View?, parent: ViewGroup): View {
        return getView(position, convertView, parent)
    }

    /**
     * Actualiza la lista sin recrear el adapter
     */
    fun actualizarLista(nuevaLista: List<Cancion>) {
        lista.clear()
        lista.addAll(nuevaLista)
        notifyDataSetChanged()
    }
}