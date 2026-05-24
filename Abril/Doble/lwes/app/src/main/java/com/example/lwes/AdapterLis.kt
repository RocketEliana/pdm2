package com.example.lwes

import android.content.Context
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.TextView
import model.Lugar

class AdapterLis(context: Context, private val lista: MutableList<Lugar>) :
    ArrayAdapter<Lugar>(context, 0, lista) {

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {

        val itemView = convertView
            ?: LayoutInflater.from(context).inflate(R.layout.item_lis, parent, false)

        val lugar = getItem(position)

        val cate = itemView.findViewById<TextView>(R.id.cate)
        val nombre = itemView.findViewById<TextView>(R.id.nombre)
        val im = itemView.findViewById<ImageView>(R.id.imgItem)
        lugar?.let {
            cate.text=it.categoria
            nombre.text=it.nombre
            im.setImageURI(Uri.parse(it.foto))
        }


        return itemView
    }

    fun actualizarLista(nuevaLista: List<Lugar>) {
        lista.clear()
        lista.addAll(nuevaLista)
        notifyDataSetChanged()
    }
}