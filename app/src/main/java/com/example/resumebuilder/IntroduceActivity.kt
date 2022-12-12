package com.example.resumebuilder

import android.Manifest.permission.CAMERA
import android.Manifest.permission.WRITE_EXTERNAL_STORAGE
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.widget.MediaController
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import kotlinx.android.synthetic.main.activity_introduce.*
import java.io.File
import java.io.FileOutputStream
import java.net.URI
import java.text.SimpleDateFormat
import java.util.*
import java.util.jar.Manifest
import javax.xml.transform.Result

class IntroduceActivity : AppCompatActivity() {
    var imagePath:String? = null
    var vedioPath:String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_introduce)


        if(ContextCompat.checkSelfPermission(this, android.Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, arrayOf(CAMERA, WRITE_EXTERNAL_STORAGE), 111)
        } else {
            capPicture.isEnabled = true
            recBtn.isEnabled = true
        }

        if(ContextCompat.checkSelfPermission(this, WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, arrayOf(WRITE_EXTERNAL_STORAGE), 114)
        }

        imagePath = Environment.getExternalStorageDirectory().toString() + "/Image.jpg"
        vedioPath = Environment.getDataDirectory().absolutePath + "/myVedio.mp4"
//        var imageBitMap: Bitmap? = BitmapFactory.decodeFile(imagePath)

//        if (imageBitMap != null) {
//            imageView.setImageBitmap(imageBitMap)
//        }

//        if (vedioPath != null) {
//            videoView.setVideoPath(vedioPath)
//        }

        capPicture.setOnClickListener{
            var image = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
            startActivityForResult(image, 112)
        }

        gallery.setOnClickListener {
            pickImageGallery()
        }

        recBtn.setOnClickListener{
            var video = Intent(MediaStore.ACTION_VIDEO_CAPTURE)
            startActivityForResult(video, 211)
        }

        back.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }

        var mediaController = MediaController(this)
        mediaController?.setAnchorView(videoView)
        videoView.setMediaController(mediaController)

    }

    fun pickImageGallery() {
        val pickIntent = Intent(Intent.ACTION_GET_CONTENT)
        pickIntent.type = "image/*"
        startActivityForResult(pickIntent, 113)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 112 && resultCode == RESULT_OK) {
            var pic = data?.getParcelableExtra<Bitmap>("data")
//            if (pic != null) {
//                saveImage(pic)
//            }
//            imagePath = pic
            imageView.setImageBitmap(pic)
        } else if (requestCode == 211 && resultCode == RESULT_OK) {
            val uri = data?.data
//            vedioPath = uri
            videoView.setVideoURI(uri)
            videoView.start()
        } else if (requestCode == 113 && resultCode == RESULT_OK) {
//            imagePath = data?.data as Bitmap
            imageView.setImageURI(data?.data)
        }
    }
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == 111 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            capPicture.isEnabled = true
            recBtn.isEnabled = true
        }
    }

    fun saveImage(pic: Bitmap) {

        val root = Environment.getExternalStorageDirectory().toString()
        val myDir = File(root + "/capture_photo")
        myDir.mkdirs()
        var name = "Image.jpg"
        val file = File(myDir, name)
        try {
            val out = FileOutputStream(file)
            pic.compress(Bitmap.CompressFormat.JPEG, 90, out)
            out.flush()
            out.close()
            Toast.makeText(this, "Image saved ${Uri.parse(file.absolutePath)}", Toast.LENGTH_LONG).show()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

//    private fun saveImage(finalBitmap: Bitmap) {
//
//        val root = Environment.getExternalStorageDirectory().toString()
//        val myDir = File(root + "/capture_photo")
//        myDir.mkdirs()
//        val generator = Random()
//        var n = 10000
//        n = generator.nextInt(n)
//        val OutletFname = "Image-$n.jpg"
//        val file = File(myDir, OutletFname)
//        if (file.exists()) file.delete()
//        try {
//            val out = FileOutputStream(file)
//            finalBitmap.compress(Bitmap.CompressFormat.JPEG, 90, out)
//            imagePath = file.absolutePath
//            out.flush()
//            out.close()
//            Toast.makeText(this, "Image saved ${Uri.parse(file.absolutePath)}", Toast.LENGTH_LONG).show()
//
//        } catch (e: Exception) {
//            e.printStackTrace()
//
//        }
//
//    }

//    fun saveImage(finalBitmap: Bitmap): String {
//        val root = Environment.getExternalStorageDirectory().toString()
//        val myDir = File("$root/saved_images")
//        myDir.mkdirs()
//        val timeStamp = SimpleDateFormat("yyyyMMdd_HHmmss").format(Date())
//        val fname = "FfmTmpImage$timeStamp.jpg"
//        val file = File(myDir, fname)
//        if (file.exists()) file.delete()
////        file.createNewFile()
//        return try {
//            val out = FileOutputStream(file)
//            finalBitmap.compress(Bitmap.CompressFormat.JPEG, 100, out);
//            out.flush()
//            out.close()
//            Toast.makeText(this, "Image saved ${Uri.parse(file.absolutePath)}", Toast.LENGTH_LONG).show()
//            file.absolutePath.toString()
//        } catch (e: Exception) {
//            e.printStackTrace()
//            ""
//        }
//    }


}