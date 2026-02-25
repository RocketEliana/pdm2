package com.example.crack.data

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import com.example.crack.R

class PersonAdapter (context: Context, private val lista: List<Persona>) :
    ArrayAdapter<Persona>(context, 0, lista) {

    /**
     * getView se encarga de dibujar cada fila (ítem) que se ve en el Spinner cerrado
     * o en los elementos de una lista.
     */
    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {

        // Optimizamos: si 'convertView' ya existe, la reutilizamos; si no, "inflamos" (creamos) el XML
        // R.layout.item_vista es el diseño personalizado que creaste para cada hada
        val itemView =
            convertView ?: LayoutInflater.from(context).inflate(R.layout.item_vista, parent, false)

        // Obtenemos el objeto Hada correspondiente a la posición actual de la lista
        val persona = lista[position]

        // Buscamos los componentes dentro del layout inflado (itemView)
        val nombre = itemView.findViewById<TextView>(R.id.nombre)
        val edad=itemView.findViewById<TextView>(R.id.edad)

        // Asignamos los datos del objeto Persona a la interfaz


        nombre.text = persona.nombre
        edad.text=persona.edad.toString()

        // Devolvemos la vista lista para ser mostrada
        return itemView
    }


}