package com.example.mislugares

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ImageView

class AdapterIcon (context: Context, private val lista:List<TipoLugar>) :
    ArrayAdapter<TipoLugar>(context, 0, lista) {

    /**
     * Dibuja cada fila del Spinner (vista cerrada o lista)
     */
    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {

        val itemView = convertView
            ?: LayoutInflater.from(context).inflate(R.layout.item_spinner, parent, false)

        val tipoLugar = getItem(position)

        val imagen = itemView.findViewById<ImageView>(R.id.icono)
        val imagenTipoLugar = tipoLugar?.imagen ?: -1
        imagen.setImageResource(imagenTipoLugar)
        return itemView
    }

    /**
     * Vista cuando el Spinner se despliega
     */
    override fun getDropDownView(position: Int, convertView: View?, parent: ViewGroup): View {
        return getView(position, convertView, parent)
    }
}
