package com.example.authoritytest

import android.content.Intent
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import kotlinx.android.synthetic.main.activity_main.*
import java.util.jar.Manifest

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        btnCamera.setOnClickListener {
            checkPermission()
        }
    }

    fun checkPermission(){
        val cameraPermission = ContextCompat.checkSelfPermission(this, android.Manifest.permission.CAMERA)
        
        if(cameraPermission == PackageManager.PERMISSION_GRANTED){
            //승인 일 경우
            startProcess() //카메라 실행
        }else {
            //승인 되지 않았을 경우
            requestPermission() //미승인 일경우 권한 요청
        }
    }

    fun startProcess(){
        //승인이면 프로그램 진행하는 메서드
        Toast.makeText(this, "카메라를 실행합니다.", Toast.LENGTH_LONG).show()

        val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent, 101)
    }

    fun requestPermission(){
        //미승인 일 때 권한 요청하는 메서드
        ActivityCompat.requestPermissions(this, arrayOf(android.Manifest.permission.CAMERA), 99)
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        when (requestCode){
            99 -> {
                if(grantResults[0] == PackageManager.PERMISSION_GRANTED){
                    startProcess()
                } else{
                    Toast.makeText(this, "Permission Denied", Toast.LENGTH_LONG).show()
                }
            }
        }
    }
}