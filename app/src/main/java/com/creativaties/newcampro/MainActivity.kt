package com.creativaties.newcampro

import android.content.Intent
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.otaliastudios.cameraview.CameraListener
import com.otaliastudios.cameraview.CameraView
import com.otaliastudios.cameraview.PictureResult
import com.otaliastudios.cameraview.VideoResult
import com.otaliastudios.cameraview.controls.Mode
import kotlinx.android.synthetic.main.activity_main.*
import java.io.File

class MainActivity : AppCompatActivity(), View.OnClickListener {

    private val camera : CameraView by lazy{
        findViewById(R.id.cameraRecorder)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        camera.mode = Mode.VIDEO

        camera.previewFrameRate = 120f

        camera.setLifecycleOwner(this)
        camera.addCameraListener(object : CameraListener() {
            override fun onPictureTaken(result: PictureResult) {
                super.onPictureTaken(result)
            }

            override fun onVideoTaken(result: VideoResult) {
                super.onVideoTaken(result)

                Preview.videoResult = result
                val intent = Intent(this@MainActivity, Preview::class.java)
                startActivity(intent)
            }
        })

        setListener()

    }
    private fun setListener() {
        capturevideo.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        when(v!!.id){
            R.id.capturevideo->{
                captureVideo()
            }
        }

    }
    private fun captureVideo() {

        if (camera.isTakingVideo) return
        camera.takeVideo(File(filesDir, "video.mp4"), 15000)

    }
    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String?>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        val valid = grantResults.all { it == PackageManager.PERMISSION_GRANTED }
        if (valid && !camera.isOpened) {
            camera.open()
        }
    }
}