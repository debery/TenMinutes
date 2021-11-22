package com.example.tenminutestest.ui.main.fragment

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.tenminutestest.R
import com.example.tenminutestest.User_IO
import com.example.tenminutestest.ui.main.MainActivity
import com.example.tenminutestest.ui.other.log.DeleteFile
import com.example.tenminutestest.ui.other.log.LogActivity
import java.io.File

class UserTabFragment:Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_mine,container,false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        val btn:Button?=view?.findViewById(R.id.exit)
        btn?.setOnClickListener {
            DeleteFile().delete()
        }
        val btn2:Button?=view?.findViewById(R.id.login)
        btn2?.setOnClickListener {
            val intent= Intent(activity,LogActivity::class.java)
            startActivity(intent)
        }
    }
}