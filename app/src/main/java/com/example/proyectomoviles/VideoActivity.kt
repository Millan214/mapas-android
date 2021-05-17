package com.example.proyectomoviles

import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.MediaController
import android.widget.VideoView
import kotlinx.android.synthetic.main.activity_video_view.*

class VideoActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_video_view)

        va_video_view.setVideoURI(Uri.parse("android.resource://"+packageName+"/"+R.raw.ibaivideo))
        va_video_view.start()

    }
}