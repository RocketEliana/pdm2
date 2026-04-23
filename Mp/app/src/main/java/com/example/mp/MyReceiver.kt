package com.example.mp

import android.app.NotificationManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import androidx.core.app.NotificationCompat

class MyReceiver : BroadcastReceiver() {

    // Se utiliza el mismo ID de canal que en MainActivity
    private val ID_CANAL = "canal_notificaciones_01"
    private val idNotificacion = 101

    /**
     * Método que sobreescribe la función onReceive y se ejecuta cuando se activa la alarma
     */
    override fun onReceive(context: Context?, intent: Intent?) {
        // Obtengo el sistema de notificaciones, que lo casteo como un NotificationManager
        val administradorNotificaciones = context?.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        // Intent para reiniciar la aplicación cuando se haga clic en la notificación
        val intentReinicio = Intent(context, MainActivity::class.java)
        val pendingIntentReinicio = PendingIntent.getActivity(context, 0, intentReinicio, PendingIntent.FLAG_IMMUTABLE)

        // Creo una notificación con el método Builder, pasando el contexto y aseguro que este no
        // será nulo, le paso además el ID del canal
        // Le paso los parámetros para crearla.
        val notificacion = NotificationCompat.Builder(context!!, ID_CANAL)
            .setSmallIcon(R.drawable.ic_notificacion)
            .setContentTitle("Cercanía detectada")
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .build()

        // Muestra la notificación
        administradorNotificaciones.notify(idNotificacion, notificacion)
    }

}