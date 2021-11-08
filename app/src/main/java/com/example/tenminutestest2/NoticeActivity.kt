package com.example.tenminutestest2

import androidx.appcompat.app.AppCompatActivity
import com.example.tenminutestest2.R
import android.os.Bundle
import android.widget.Button
import android.widget.LinearLayout

import android.widget.ListView
import android.widget.TextView

class NoticeActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_notice)
        supportActionBar?.hide()

        val finish:Button=findViewById(R.id.noticeActivityFinish)
        val mentionA:LinearLayout=findViewById(R.id.noticeActivityMention)
        val mentionB:LinearLayout=findViewById(R.id.noticeActivityComment)
        val agreed:LinearLayout=findViewById(R.id.noticeActivityAgreed)
        val friend:LinearLayout=findViewById(R.id.noticeActivityFriend)

        finish.setOnClickListener {
            finish()
        }

    }




}