package com.creativaties.newcampro

import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.MediaController
import android.widget.VideoView
import com.otaliastudios.cameraview.VideoResult

class Preview : AppCompatActivity() {
    private val videoView: VideoView by lazy { findViewById<VideoView>(R.id.video) }


    companion object {
        var videoResult: VideoResult? = null


    }    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_preview)
        val result = videoResult ?: run {
            finish()
            return
        }
        val controller = MediaController(this)
        controller.setAnchorView(videoView)
        controller.setMediaPlayer(videoView)
        videoView.setMediaController(controller)
        videoView.setVideoURI(Uri.fromFile(result.file))
        videoView.setOnPreparedListener { mp ->
            val lp = videoView.layoutParams
            val videoWidth = mp.videoWidth.toFloat()
            val videoHeight = mp.videoHeight.toFloat()
            val viewWidth = videoView.width.toFloat()
            lp.height = (viewWidth * (videoHeight / videoWidth)).toInt()
            videoView.layoutParams = lp

            playVideo()
        }


    }

    private fun playVideo() {
        if (!videoView.isPlaying) {
            videoView.start()

        }
    }
}