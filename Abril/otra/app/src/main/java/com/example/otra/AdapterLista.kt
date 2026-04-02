package com.example.otra

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.TextView
import model.Espectaculo

class AdapterLista (context: Context, private val lista: List<Espectaculo>) :
    ArrayAdapter<Espectaculo>(context, 0, lista) {

    /**
     * getView se encarga de dibujar cada fila (ítem) que se ve en el Spinner cerrado
     * o en los elementos de una lista.
     */
    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {

        // Optimizamos: si 'convertView' ya existe, la reutilizamos; si no, "inflamos" (creamos) el XML
        // R.layout.item_vista es el diseño personalizado que creaste para cada hada
        val itemView =
            convertView ?: LayoutInflater.from(context).inflate(R.layout.item_lista, parent, false)

        // Obtenemos el objeto Hada correspondiente a la posición actual de la lista
        val espectaculo = lista[position]

        // Buscamos los componentes dentro del layout inflado (itemView)
        val imagen = itemView.findViewById<ImageView>(R.id.imagen)
        val nombre = itemView.findViewById<TextView>(R.id.nombre)

        // Asignamos los datos del objeto Hada a la interfaz
        // 'hada.imagen' debe ser un ID de recurso (ej: R.drawable.hada1)
        imagen.setImageResource(espectaculo.icono)
        nombre.text = espectaculo.nombre

        // Devolvemos la vista lista para ser mostrada
        return itemView
    }

}