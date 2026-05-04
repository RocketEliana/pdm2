package ui

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.TextView
import com.example.mp.R
import model.Sala

class AdapterSpinner(context: Context, private val lista: List<Sala>) :
    ArrayAdapter<Sala>(context, 0, lista) {

    /**
     * getView se encarga de dibujar cada fila (ítem) que se ve en el Spinner cerrado
     * o en los elementos de una lista.
     */
    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {

        // Optimizamos: si 'convertView' ya existe, la reutilizamos; si no, "inflamos" (creamos) el XML
        // R.layout.item_vista es el diseño personalizado que creaste para cada hada
        val itemView =
            convertView ?: LayoutInflater.from(context).inflate(R.layout.item_spinner, parent, false)

        // Obtenemos el objeto Hada correspondiente a la posición actual de la lista
        val sala = lista[position]

        // Buscamos los componentes dentro del layout inflado (itemView)
        val imagen = itemView.findViewById<ImageView>(R.id.logoSala)
        val nombre = itemView.findViewById<TextView>(R.id.nombre)

        // Asignamos los datos del objeto Hada a la interfaz
        // 'hada.imagen' debe ser un ID de recurso (ej: R.drawable.hada1)
        imagen.setImageResource(sala.imagen)
        nombre.text = sala.nombre

        // Devolvemos la vista lista para ser mostrada
        return itemView
    }

    /**
     * getDropDownView es CRUCIAL para Spinners. Define cómo se ve cada fila
     * CUANDO EL MENÚ SE DESPLIEGA hacia abajo.
     */
    override fun getDropDownView(position: Int, convertView: View?, parent: ViewGroup): View {
        // En este caso, reutilizamos la misma lógica de 'getView' para que se vea igual
        // tanto cerrado como abierto.
        return getView(position, convertView, parent)
    }
}