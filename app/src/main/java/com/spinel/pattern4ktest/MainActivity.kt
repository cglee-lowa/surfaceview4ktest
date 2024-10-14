package com.spinel.pattern4ktest

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.BitmapFactory.Options
import android.os.Bundle
import android.util.Log
import android.view.SurfaceHolder
import android.view.SurfaceView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity(), SurfaceHolder.Callback {
    private var bitmap: Bitmap? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        (findViewById<SurfaceView>(R.id.surfaceView))?.holder?.addCallback(this as SurfaceHolder.Callback?)
    }

    override fun surfaceCreated(holder: SurfaceHolder) {
        Log.i("canvas", "surfaceCreated")
        bitmap = BitmapFactory.decodeResource(resources, R.drawable.sgra_4k, Options().apply {
            inScaled = false
        })
        holder.setFixedSize(3840, 2160)
        Log.i("bitmap", "${bitmap?.width}  ${bitmap?.height}")
    }

    // SurfaceView에서 이미지를 그리는 메서드
    private fun drawBitmap(holder: SurfaceHolder) {
        holder.lockCanvas()?.let { canvas ->
            Log.i("canvas", "canvas size : ${canvas.width}  ${canvas.height}")
            bitmap?.let { canvas.drawBitmap(it, 0f, 0f, null) }
            holder.unlockCanvasAndPost(canvas)
        }
    }

    override fun surfaceChanged(holder: SurfaceHolder, format: Int, width: Int, height: Int) {
        Log.i("canvas", "surfaceChanged $format $width $height")
        if (width == 3840) drawBitmap(holder)
    }

    override fun surfaceDestroyed(holder: SurfaceHolder) {
        bitmap?.recycle()
    }
}