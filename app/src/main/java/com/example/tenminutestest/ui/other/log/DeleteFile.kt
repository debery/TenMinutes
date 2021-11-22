package com.example.tenminutestest.ui.other.log

import android.widget.Toast
import com.example.tenminutestest.MyApplication
import java.io.File

class DeleteFile {

    fun delete(){
        val file= File("data")
        file.delete()
        if(file.exists())
            Toast.makeText(MyApplication.context,"在", Toast.LENGTH_SHORT).show()
        Toast.makeText(MyApplication.context,"退出登录", Toast.LENGTH_SHORT).show()
    }
}