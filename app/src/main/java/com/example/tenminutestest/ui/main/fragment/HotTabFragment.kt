package com.example.tenminutestest.ui.main.fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import com.example.tenminutestest.R
import com.example.tenminutestest.logic.model.HotWord
import com.example.tenminutestest.logic.network.HotService
import com.example.tenminutestest.logic.network.ServiceCreator
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HotTabFragment: Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_hot,container,false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        val btn:Button=view?.findViewById(R.id.button)!!
        btn.setOnClickListener {
            val hotService=ServiceCreator.create3(HotService::class.java)
            hotService.getHotWord().enqueue(object :Callback<List<HotWord>>{
                override fun onResponse(call: Call<List<HotWord>>, response: Response<List<HotWord>>) {
                    val list=response.body()
                    if (list != null) {
                        for (hot in list){
                            Log.d("HotFragment",hot.pname)
                            Log.d("HotFragment",hot.time.toString())
                        }

                    }
                }

                override fun onFailure(call: Call<List<HotWord>>, t: Throwable) {
                    t.printStackTrace()
                }

            })
        }

    }
}