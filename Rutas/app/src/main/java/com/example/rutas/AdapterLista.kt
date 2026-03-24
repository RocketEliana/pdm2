package com.example.rutas

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import model.Ruta

class AdapterLista(context: Context, private val lista: List<Ruta>) :
    ArrayAdapter<Ruta>(context, 0, lista) {

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
        val ruta = lista[position]

        // Buscamos los componentes dentro del layout inflado (itemView)
        val nombre = itemView.findViewById<TextView>(R.id.nombre)
        val provincia=itemView.findViewById<TextView>(R.id.provincia)

        // Asignamos los datos del objeto Hada a la interfaz
        // 'hada.imagen' debe ser un ID de recurso (ej: R.drawable.hada1)

        nombre.text = ruta.nombre
        provincia.text=ruta.provincia

        // Devolvemos la vista lista para ser mostrada
        return itemView
    }


}