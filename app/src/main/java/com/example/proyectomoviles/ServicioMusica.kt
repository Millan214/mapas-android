package com.example.proyectomoviles

import android.app.Service
import android.content.Intent
import android.media.MediaPlayer
import android.os.IBinder

class ServicioMusica : Service() {

    private lateinit var mp:MediaPlayer

    override fun onCreate() {
        super.onCreate()
        mp = MediaPlayer.create(this,R.raw.lapanterarosa)
        mp.isLooping = true
        mp.setVolume(100F,100F)
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        mp.start()
        return START_STICKY
    }

    override fun onDestroy() {
        super.onDestroy()
        if (mp.isPlaying) mp.stop()
        mp.release()
    }

    override fun onBind(intent: Intent?): IBinder? {
        return null
    }
}
