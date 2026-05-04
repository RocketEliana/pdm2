package viewModel


import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.TextView
import com.example.vallaevent.R
import model.Evento

class AdapterSpinnerEvento(context: Context, private val lista: MutableList<Evento>) :
    ArrayAdapter<Evento>(context, 0, lista) {

    /**
     * Dibuja cada fila del Spinner (vista cerrada o lista)
     */
    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {

        val itemView = convertView
            ?: LayoutInflater.from(context).inflate(R.layout.item_vista, parent, false)

        val evento = getItem(position)

        val imagen = itemView.findViewById<ImageView>(R.id.imagen)
        val nombre=itemView.findViewById<TextView>(R.id.nombre)
        evento?.let {
            imagen.setImageResource(it.icono)
            nombre.text=it.nombre
        }


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
    fun actualizarLista(nuevaLista: List<Evento>) {
        lista.clear()
        lista.addAll(nuevaLista)
        notifyDataSetChanged()
    }
}