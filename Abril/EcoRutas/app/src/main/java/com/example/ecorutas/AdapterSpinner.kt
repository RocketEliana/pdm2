package com.example.ecorutas

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.TextView
import model.Espacio

class AdapterSpinner(context: Context, private val lista: MutableList<Espacio>) :
    ArrayAdapter<Espacio>(context, 0, lista) {

    /**
     * Dibuja cada fila del Spinner (vista cerrada o lista)
     */
    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {

        val itemView = convertView
            ?: LayoutInflater.from(context).inflate(R.layout.item_spinner, parent, false)

        val espacio = getItem(position)

        val imagen = itemView.findViewById<ImageView>(R.id.imgItem)
        val nombre = itemView.findViewById<TextView>(R.id.nombre)
        val telefono = itemView.findViewById<TextView>(R.id.telefono)

        espacio?.let {

            nombre.text=it.nombre
            telefono.text=it.telefono
            imagen.setImageResource(it.icono)
        }

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
    fun actualizarLista(nuevaLista: List<Espacio>) {
        lista.clear()
        lista.addAll(nuevaLista)
        notifyDataSetChanged()
    }
}