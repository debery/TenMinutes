package com.example.tenminutestest2.ui.mainactivity

import android.graphics.PixelFormat
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.MediaController
import android.widget.VideoView
import androidx.fragment.app.Fragment
import com.example.tenminutestest2.R

class SportTabFragment:Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.sport_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        val videoView: VideoView? = view?.findViewById(R.id.videoView)
        activity?.window?.setFormat(PixelFormat.TRANSLUCENT)
        val url = Uri.parse("android.resource://${activity?.packageName}/${R.raw.video}")
        videoView?.setMediaController(MediaController(activity))
        videoView?.setVideoURI(url)
    }

    override fun onDestroy() {
        super.onDestroy()
        val videoView: VideoView? = view?.findViewById(R.id.videoView)
        videoView?.suspend()
    }

}