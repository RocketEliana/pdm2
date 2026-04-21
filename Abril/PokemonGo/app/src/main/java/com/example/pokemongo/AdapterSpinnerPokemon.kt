package com.example.pokemongo

import android.content.Context
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.TextView
import com.example.pokemongo.model.Pokemon

class AdapterSpinnerPokemon (context: Context, private val lista: MutableList<Pokemon>) :
    ArrayAdapter<Pokemon>(context, 0, lista) {

    /**
     * Dibuja cada fila del Spinner (vista cerrada o lista)
     */
    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {

        val itemView = convertView
            ?: LayoutInflater.from(context).inflate(R.layout.item_vista, parent, false)

        val poke = getItem(position)

        val imagen = itemView.findViewById<ImageView>(R.id.fotoPokemon)
        val nombre = itemView.findViewById<TextView>(R.id.tipoPokemon)
        val uriImagen= Uri.parse(poke?.foto)

        imagen.setImageURI(uriImagen)
        nombre.text = poke?.tipo

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
    fun actualizarLista(nuevaLista: List<Pokemon>) {
        lista.clear()
        lista.addAll(nuevaLista)
        notifyDataSetChanged()
    }
}