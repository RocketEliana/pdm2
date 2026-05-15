package com.example.pokemonextension

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.graphics.SurfaceTexture
import android.media.AudioAttributes
import android.media.MediaPlayer
import android.net.Uri
import android.view.Surface
import android.view.TextureView
import android.widget.Button
import android.widget.Toast

class Video : AppCompatActivity(), TextureView.SurfaceTextureListener {

    private lateinit var textureView: TextureView
    private var mediaPlayer: MediaPlayer? = null
    private var surface: Surface? = null
    private var isPrepared = false

    private val videoUrl = "https://media.istockphoto.com/id/946257202/es/v%C3%ADdeo/navega-en-tiempo-real-golpe-de-mar-a%C3%A9rea-superior-vista-para-abajo.mp4?s=mp4-640x640-is&k=20&c=Hx2ci9xbImoX83T02cZYtmpwrqdrC57jozXDh-HbG6o="

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_video)

        textureView = findViewById(R.id.textureView)
        textureView.surfaceTextureListener = this

        val btnPlay = findViewById<Button>(R.id.btnPlay)
        val btnPause = findViewById<Button>(R.id.btnPause)
        val btnStop = findViewById<Button>(R.id.btnStop)
        val btnretroceder = findViewById<Button>(R.id.retroceder)
        val btnavanzar = findViewById<Button>(R.id.adelantar)
        val btnsonido = findViewById<Button>(R.id.silenciarActivarSonido)
        val omitir = findViewById<Button>(R.id.omitir)

        var sonidoActivado = true

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

        btnretroceder.setOnClickListener {
            mediaPlayer?.let {
                val nuevaPos = (it.currentPosition - 10_000).coerceAtLeast(0)
                it.seekTo(nuevaPos)
            }
        }

        btnavanzar.setOnClickListener {
            mediaPlayer?.let {
                val nuevaPos = (it.currentPosition + 10_000).coerceAtMost(it.duration)
                it.seekTo(nuevaPos)
            }
        }

        btnsonido.setOnClickListener {
            mediaPlayer?.let {
                sonidoActivado = !sonidoActivado

                it.setVolume(
                    if (sonidoActivado) 1f else 0f,
                    if (sonidoActivado) 1f else 0f
                )

                Toast.makeText(
                    this,
                    if (sonidoActivado) "Sonido activado" else "Sonido silenciado",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }

        // ⏭ OMITIR (AQUÍ VA LA LÓGICA)
        omitir.visibility = Button.VISIBLE

        omitir.postDelayed({
            omitir.visibility = Button.GONE
        }, 5000)

        omitir.setOnClickListener {
            mediaPlayer?.seekTo(mediaPlayer!!.duration)
        }
    }

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

            setOnCompletionListener {
                finish()
            }

            setOnPreparedListener {
                isPrepared = true
                it.start()

                Toast.makeText(
                    this@Video,
                    "Reproduciendo drone...",
                    Toast.LENGTH_SHORT
                ).show()
            }

            setOnErrorListener { _, what, _ ->
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