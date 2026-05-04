package com.example.vallaevent


import android.Manifest
import android.app.AlarmManager
import android.app.DatePickerDialog
import android.app.PendingIntent
import android.app.TimePickerDialog
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.icu.util.Calendar
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ListView
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.example.vallaevent.databinding.FragmentNuevoBinding
import model.Evento
import viewModel.AppViewModel
import java.text.SimpleDateFormat
import java.util.Locale

class NuevoFragment : Fragment() {
    private var _binding: FragmentNuevoBinding? = null
    private val viewModel: AppViewModel by activityViewModels()
    private val binding get() = _binding!!
    private var tipo: String = ""
    private var fechaN: String = ""
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentNuevoBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val tipoList = listOf("Musica", "Teatro", "Deporte")
        // Y en el adapter, pon android.R explícitamente:
        val adapter =
            ArrayAdapter(requireContext(), android.R.layout.simple_list_item_activated_1, tipoList)
        binding.listatipo.adapter = adapter
        // Importante: Dile al ListView que solo se puede elegir uno a la vez
        binding.listatipo.choiceMode = ListView.CHOICE_MODE_SINGLE
        binding.listatipo.setOnItemClickListener { parent, view, position, id ->
            binding.listatipo.setItemChecked(position, true)
            tipo = tipoList[position]
        }
        binding.fecha.setOnClickListener { mostrarCalendario() }
        binding.registro.setOnClickListener {
            val nombreN = binding.nombreD.text.toString()
            binding.registro.setOnClickListener {
                val nombreN = binding.nombreD.text.toString()

                var iconoT = when (tipo) {
                    "Musica" -> R.drawable.music
                    "Deporte" -> R.drawable.dep
                    "Teatro" -> R.drawable.tea
                    else -> R.drawable.ic_launcher_background
                }
                fechaN = binding.fecha.text.toString()
                val lati = binding.latitud.text.toString().toDouble()
                val long = binding.longitud.text.toString().toDouble()
                val cali = binding.calificacion.rating
                val evemto = Evento(
                    nombre = nombreN,
                    fecha = fechaN,
                    tipo = tipo,
                    icono = iconoT,
                    latitud = lati,
                    longitud = long,
                    valoracion = cali
                )
                val id = viewModel.insertaEvento(evemto)
                // copy() crea una copia del objeto evemto cambiando solo el id,
                // ya que Room asigna el id real al insertar pero no actualiza el objeto original
                programarAlarma(evemto.copy(id = id))
                if (id != -1L) {
                    Toast.makeText(requireContext(), "Insertado con exito", Toast.LENGTH_LONG)
                        .show()
                    if (evemto.tipo == "Musica") {
                        if (ContextCompat.checkSelfPermission(
                                requireContext(),
                                Manifest.permission.POST_NOTIFICATIONS
                            ) == PackageManager.PERMISSION_GRANTED
                        ) {
                            (requireActivity() as MainActivity).mostrarNotificacion()

                        }

                        parentFragmentManager.popBackStack()
                    } else {
                        Toast.makeText(
                            requireContext(),
                            "No se ha podido insertar",
                            Toast.LENGTH_LONG
                        ).show()

                    }
                }

            }


        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun mostrarCalendario() {
        val calendario = Calendar.getInstance()
        DatePickerDialog(
            requireContext(),
            { _, año, mes, dia ->
                TimePickerDialog(
                    requireContext(),
                    { _, hora, min ->
                        val fechaHora =
                            String.format("%02d/%02d/%d %02d:%02d", dia, mes + 1, año, hora, min)
                        binding.fecha
                            .setText(fechaHora)
                    },
                    calendario.get(Calendar.HOUR_OF_DAY),
                    calendario.get(Calendar.MINUTE),
                    true
                ).show()
            },
            calendario.get(Calendar.YEAR),
            calendario.get(Calendar.MONTH),
            calendario.get(Calendar.DAY_OF_MONTH)
        ).show()
    }

    private fun programarAlarma(evento: Evento) {

        Toast.makeText(requireContext(), "Entrando en programarAlarma", Toast.LENGTH_SHORT).show()

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            val alarmManager =
                requireContext().getSystemService(Context.ALARM_SERVICE) as AlarmManager
            if (!alarmManager.canScheduleExactAlarms()) {
                Toast.makeText(requireContext(), "SIN PERMISO de alarma exacta", Toast.LENGTH_LONG)
                    .show()
                androidx.appcompat.app.AlertDialog.Builder(requireContext())
                    .setTitle("Permiso necesario")
                    .setMessage("Para programar alarmas la app necesita un permiso especial.")
                    .setPositiveButton("Aceptar") { _, _ ->
                        startActivity(Intent(Settings.ACTION_REQUEST_SCHEDULE_EXACT_ALARM))
                    }
                    .setNegativeButton("Cancelar", null)
                    .show()
                return
            }
        }

        val sdf = SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.getDefault())
        val fecha = sdf.parse(evento.fecha) ?: run {
            Toast.makeText(
                requireContext(),
                "ERROR: fecha no parseable: ${evento.fecha}",
                Toast.LENGTH_LONG
            ).show()
            return
        }

        if (fecha.time <= System.currentTimeMillis()) {
            Toast.makeText(requireContext(), "ERROR: fecha ya pasada", Toast.LENGTH_LONG).show()
            return
        }

        Toast.makeText(
            requireContext(),
            "Alarma programada para ${evento.fecha}",
            Toast.LENGTH_LONG
        ).show()

        val intent = Intent(requireContext(), AlarmReceiver::class.java).apply {
            putExtra("nombre", evento.nombre)
        }
        val pendingIntent = PendingIntent.getBroadcast(
            requireContext(),
            evento.id.toInt(),
            intent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )
        val alarmManager = requireContext().getSystemService(Context.ALARM_SERVICE) as AlarmManager
        alarmManager.setExact(AlarmManager.RTC_WAKEUP, fecha.time, pendingIntent)
    }
}
