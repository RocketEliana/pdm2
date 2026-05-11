package com.example.comidallevar

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.TextView
import model.Pedido
import model.Restaurante

class AdapterListaPedido(
    context: Context,
    private val lista: MutableList<Pedido>) :
    ArrayAdapter<Pedido>(context, 0, lista) {

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {

        val itemView = convertView
            ?: LayoutInflater.from(context).inflate(R.layout.item_lista_pedido, parent, false)

        val pedido = getItem(position)

        val nombre = itemView.findViewById<TextView>(R.id.rest)
        val direccion = itemView.findViewById<TextView>(R.id.direccion)
        pedido?.let {
            nombre.text=it.restaurante
            direccion.text=it.entrega
        }


        return itemView
    }

    fun actualizarLista(nuevaLista: List<Pedido>) {
        lista.clear()
        lista.addAll(nuevaLista)
        notifyDataSetChanged()
    }
}