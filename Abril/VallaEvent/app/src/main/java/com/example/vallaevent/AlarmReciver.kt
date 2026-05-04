package com.example.vallaevent

import android.app.NotificationManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import androidx.core.app.NotificationCompat


class AlarmReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context?, intent: Intent?) {
        // Recuperamos el nombre del evento que pasamos al programar la alarma
        val nombre = intent?.getStringExtra("nombre") ?: "Evento"

        val manager = context?.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        // Al pulsar la notificación, abre tu MainActivity
        val intentApertura = Intent(context, MainActivity::class.java)
        val pendingIntent = PendingIntent.getActivity(
            context, 0, intentApertura, PendingIntent.FLAG_IMMUTABLE
        )

        val notificacion = NotificationCompat.Builder(context!!, "mi_canal")
            .setContentTitle("¡Tienes un evento hoy!")
            .setContentText(nombre)
            .setSmallIcon(R.drawable.dep)
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setContentIntent(pendingIntent)
            .setAutoCancel(true)
            .build()

        manager.notify(101, notificacion)
    }
}