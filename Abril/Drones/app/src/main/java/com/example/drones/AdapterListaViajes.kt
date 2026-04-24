package com.example.drones
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import model.Viaje

class AdapterListaViajes (context: Context, private val lista: MutableList<Viaje>) :
    ArrayAdapter<Viaje>(context, 0, lista) {

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {

        val itemView = convertView
            ?: LayoutInflater.from(context).inflate(R.layout.item_lista_viaje, parent, false)

        val viaje = getItem(position)

        val origen = itemView.findViewById<TextView>(R.id.origen)
        val destino = itemView.findViewById<TextView>(R.id.destino)
        val fecha = itemView.findViewById<TextView>(R.id.fecha)

       origen.text=viaje?.origen
        destino.text=viaje?.destino
        fecha.text=viaje?.fechaHora

        return itemView
    }

    fun actualizarLista(nuevaLista: List<Viaje>) {
        lista.clear()
        lista.addAll(nuevaLista)
        notifyDataSetChanged()
    }
}