package com.example.drones

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.TextView
import model.Instituto

class AdapterSpinnerDestino (context: Context, private val lista: MutableList<Instituto>) :
    ArrayAdapter<Instituto>(context, 0, lista){
    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {

        val itemView = convertView
            ?: LayoutInflater.from(context).inflate(R.layout.item_spinner_destino, parent, false)

        val insti = getItem(position)

        val imagen = itemView.findViewById<ImageView>(R.id.imgItem)
        val nombre = itemView.findViewById<TextView>(R.id.nombre)

        imagen.setImageResource(insti?.icono ?: 0)
        nombre.text = insti?.nombre

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
    fun actualizarLista(nuevaLista: List<Instituto>) {
        lista.clear()
        lista.addAll(nuevaLista)
        notifyDataSetChanged()
    }
}