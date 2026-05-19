package com.example.atletismo

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import model.Categoria

class AdapterCategoria(context: Context, private val lista: MutableList<Categoria>) :
    ArrayAdapter<Categoria>(context, 0, lista) {

    /**
     * Dibuja cada fila del Spinner (vista cerrada o lista)
     */
    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {

        val itemView = convertView
            ?: LayoutInflater.from(context).inflate(R.layout.item_categoria, parent, false)

        val categoria = getItem(position)
        val nombre = itemView.findViewById<TextView>(R.id.nombre)

    categoria?.let{
        nombre.text=it.nombre
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
    fun actualizarLista(nuevaLista: List<Categoria>) {
        lista.clear()
        lista.addAll(nuevaLista)
        notifyDataSetChanged()
    }
}