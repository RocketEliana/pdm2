package com.example.rstaurantes


import android.content.Context
import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import model.Bar

class AdapterLista(context: Context, private val lista: MutableList<Bar>) :
    ArrayAdapter<Bar>(context, 0, lista) {

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {

        val itemView = convertView
            ?: LayoutInflater.from(context).inflate(R.layout.item_vista, parent, false)

        val bar = getItem(position)

        val web = itemView.findViewById<TextView>(R.id.web)
        val nombre = itemView.findViewById<TextView>(R.id.nombre)

        nombre.text = bar?.nombre
        web.text = bar?.web
        web.setOnClickListener {
            bar?.web?.let { url ->
                val uri = Uri.parse(url)
                val intent = Intent(Intent.ACTION_VIEW, uri)
                context.startActivity(intent)
            }
        }
        return itemView
    }

    fun actualizarLista(nuevaLista: List<Bar>) {
        lista.clear()
        lista.addAll(nuevaLista)
        notifyDataSetChanged()
    }

}