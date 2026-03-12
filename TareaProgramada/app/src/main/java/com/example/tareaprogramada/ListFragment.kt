package com.example.tareaprogramada

import android.app.AlertDialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.room.Update
import com.example.tareaprogramada.databinding.FragmentListBinding
import com.example.tareaprogramada.model.TareaAdapter
import com.example.tareaprogramada.viewModel.TareaViewModel
class ListFragment : Fragment() {
    private var _binding: FragmentListBinding? = null
    private val binding get() = _binding!!
    private lateinit var adapter: TareaAdapter
    private val viewModel: TareaViewModel by activityViewModels()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentListBinding.inflate(inflater, container, false)
        return binding.root
    }
    /*2. El "Truco" de LiveData: .value
Cuando usas un LiveData (como tu listaTareas), tienes dos formas de acceder a los datos:

Modo Pasivo (Observe): "Dime cuándo cambien". (Lo usas para pintar la lista).

Modo Activo (.value): "Dame lo que tengas ahora mismo en la caja". (Lo usas para acciones puntuales como un click).*/
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
       // val listaTareas=viewModel.listaTarea esto estaria mal,no es un dato real, es un liveData
        val listaTareas=viewModel.listaTareas.observe(viewLifecycleOwner){tareas->//"el ciclo de vida de las vistas del fragment",si
            //fuera una actividad seria yhis por que La activity ES el LifecycleOwner, y sus vistas viven mientras existe la activity,
            //Las vistas del fragment tienen su propio ciclo de vida (se crean en onCreateView y se destruyen en onDestroyView)
        adapter= TareaAdapter(requireContext(),tareas)
            binding.listaTarea.adapter=adapter
        }
        binding.listaTarea.setOnItemLongClickListener { _, _, i, _ ->
            val listaActual = viewModel.listaTareas.value//te está devolviendo la lista completa

            val tareaDeLaFoto = listaActual?.get(i)?.let { actual ->

            val dialogo = AlertDialog.Builder(requireContext()).setTitle("Confirmar borrado").setMessage("¿Estás seguro de que quieres borrar?")

            // Botón de SI
            .setPositiveButton("Borrar") { _, _ ->
                // La acción de borrar la mueves AQUÍ dentro
                viewModel.borrar(actual)
                Toast.makeText(requireContext(), "Borrado", Toast.LENGTH_SHORT).show()
            }

            // Botón de NO
                .setNegativeButton(
                "Cancelar",
                null) // 'null' porque no queremos que haga nada especial, solo cerrarse

            // Mostrarlo
            dialogo.show()
        }

            true // El LongClick se consume aquí, el diálogo aparecerá encima
        }
     binding.listaTarea.setOnItemClickListener{_,_,i,l->
         val listaFoto=viewModel.listaTareas.value
         val tareaConcreto=listaFoto?.get(i)?.let{t->
             val id=t.id
             val fragmento= UpdateFragment()
             val bundle=Bundle()
             bundle.putInt("ID",id)
             fragmento.arguments=bundle
             val transaction=parentFragmentManager.beginTransaction()
             transaction.replace(R.id.fragmento_principal,fragmento).addToBackStack(null).commit()
         }



     }


        }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}