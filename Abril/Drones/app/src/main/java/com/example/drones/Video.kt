package com.example.drones

import android.graphics.SurfaceTexture
import android.media.AudioAttributes
import android.media.MediaPlayer
import android.net.Uri
import android.os.Bundle
import android.view.Surface
import android.view.TextureView
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class Video : AppCompatActivity(), TextureView.SurfaceTextureListener {

    private lateinit var textureView: TextureView
    private var mediaPlayer: MediaPlayer? = null
    private var surface: Surface? = null
    private var isPrepared = false

    // URL verificada de GitHub para evitar el error 403/404 de Google
    private val videoUrl = "https://media.istockphoto.com/id/946257202/es/v%C3%ADdeo/navega-en-tiempo-real-golpe-de-mar-a%C3%A9rea-superior-vista-para-abajo.mp4?s=mp4-640x640-is&k=20&c=Hx2ci9xbImoX83T02cZYtmpwrqdrC57jozXDh-HbG6o="

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_video)

        textureView = findViewById(R.id.textureView)
        textureView.surfaceTextureListener = this

        val btnPlay = findViewById<Button>(R.id.btnPlay)
        val btnPause = findViewById<Button>(R.id.btnPause)
        val btnStop = findViewById<Button>(R.id.btnStop)

        btnPlay.setOnClickListener {
            if (isPrepared) mediaPlayer?.start()
        }

        btnPause.setOnClickListener {
            if (mediaPlayer?.isPlaying == true) mediaPlayer?.pause()
        }

        btnStop.setOnClickListener {
            if (isPrepared) {
                mediaPlayer?.stop()
                releaseMediaPlayer()
                surface?.let { initMediaPlayer(it) }
            }
        }
        /*avanti btnForward.setOnClickListener {
    mediaPlayer?.let { player ->
        if (isPrepared) {
            // Obtenemos la posición actual
            val currentPos = player.currentPosition
            // Sumamos 10 segundos (10,000 ms)
            val nextPos = currentPos + 10000

            // Verificamos no pasarnos del total de la duración
            if (nextPos < player.duration) {
                player.seekTo(nextPos)
            } else {
                // Si el salto supera el final, lo enviamos al final
                player.seekTo(player.duration)
            }
        }
    }
}*/
    }

    // -------------------------------------------------------------------------
    // TEXTUREVIEW LISTENERS
    // -------------------------------------------------------------------------

    override fun onSurfaceTextureAvailable(surfaceTexture: SurfaceTexture, width: Int, height: Int) {
        surface = Surface(surfaceTexture)
        initMediaPlayer(surface!!)
    }

    override fun onSurfaceTextureSizeChanged(s: SurfaceTexture, w: Int, h: Int) {}

    override fun onSurfaceTextureDestroyed(s: SurfaceTexture): Boolean {
        releaseMediaPlayer()
        surface?.release()
        surface = null
        return true
    }

    override fun onSurfaceTextureUpdated(s: SurfaceTexture) {}

    // -------------------------------------------------------------------------
    // MEDIAPLAYER (SOLO UNA FUNCIÓN)
    // -------------------------------------------------------------------------

    private fun initMediaPlayer(theSurface: Surface) {
        if (mediaPlayer != null) return

        mediaPlayer = MediaPlayer().apply {
            setAudioAttributes(
                AudioAttributes.Builder()
                    .setContentType(AudioAttributes.CONTENT_TYPE_MOVIE)
                    .setUsage(AudioAttributes.USAGE_MEDIA)
                    .build()
            )

            setSurface(theSurface)
            setDataSource(this@Video, Uri.parse(videoUrl))

            // 1. Vuelve a la actividad principal al terminar
            setOnCompletionListener {
                finish()
            }

            // 2. Se autoejecuta al estar listo
            setOnPreparedListener {
                isPrepared = true
                it.start()
                Toast.makeText(this@Video, "Reproduciendo drone...", Toast.LENGTH_SHORT).show()
            }

            // 3. Si hay error, cierra para no quedarse colgado
            setOnErrorListener { _, what, extra ->
                isPrepared = false
                Toast.makeText(this@Video, "Error: $what", Toast.LENGTH_SHORT).show()
                finish()
                true
            }

            prepareAsync()
        }
    }

    private fun releaseMediaPlayer() {
        mediaPlayer?.reset()
        mediaPlayer?.release()
        mediaPlayer = null
        isPrepared = false
    }

    override fun onPause() {
        super.onPause()
        if (mediaPlayer?.isPlaying == true) mediaPlayer?.pause()
    }

    override fun onDestroy() {
        super.onDestroy()
        releaseMediaPlayer()
        surface?.release()
    }
}