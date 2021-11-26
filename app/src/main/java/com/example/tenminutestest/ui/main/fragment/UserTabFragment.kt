package com.example.tenminutestest.ui.main.fragment

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import android.widget.AdapterView.OnItemClickListener
import androidx.fragment.app.Fragment
import com.example.tenminutestest.MyApplication
import com.example.tenminutestest.R
import com.example.tenminutestest.User_IO
import com.example.tenminutestest.ui.main.MainActivity
import com.example.tenminutestest.ui.other.Config
import com.example.tenminutestest.ui.other.Punch_pt
import com.example.tenminutestest.ui.other.log.DeleteFile
import com.example.tenminutestest.ui.other.log.LogActivity
import java.io.File

class UserTabFragment:Fragment() {
    //配置两ListView
    var first: ListView? = null
    var second: ListView? = null
    private val firsticon: ImageView? = null
    private val secondicon: ImageView? = null //分别为两个ListView所用，下同
    private val firsttitle: TextView? = null
    private val secondtitle: TextView? = null
    val firsticons = listOf(R.drawable.helper, R.drawable.renzheng, R.drawable.er_wei_ma, R.drawable.my_level)
    val secondicons = listOf(R.drawable.my_class, R.drawable.helper)
    val firsttitles = listOf("每日打卡", "身份认证", "我的二维码", "我的等级")
    val secondtitles = listOf("我的行政班级", "帮助与反馈中心")

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        return inflater.inflate(R.layout.fragment_mine,container,false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        first = view?.findViewById(R.id.Firstlist)
        second = view?.findViewById(R.id.Secondlist)
        val fir = firstMineAdapter(firsticons,firsttitles)
        first!!.adapter = fir
        val SMA = SecondMineAdapter(secondicons,secondtitles)
        second!!.adapter = SMA

        first!!.onItemClickListener = OnItemClickListener { parent, view, position, id ->
            if (position == 0) {
                val intent = Intent(activity,Punch_pt::class.java)
                startActivity(intent)
            }
        }

    }

    fun config(view: View?) {
        val intent = Intent(activity, Config::class.java)
        startActivity(intent)
    }

    private class firstMineAdapter(val list:List<Int>,val listSecond:List<String>) : BaseAdapter() //"我的"页面第一个适配器
    {
        override fun getCount(): Int {
            return list.size
        }

        override fun getItem(position: Int): Any {
            return list.get(position)
        }

        override fun getItemId(position: Int): Long {
            return position.toLong()
        }

        override fun getView(position: Int, convertView: View, parent: ViewGroup): View {
            var convertView = convertView
            var fmine: FMineViewHolder? = null
            if (convertView == null) {
                convertView = View.inflate(MyApplication.context, R.layout.itemlaout, null)
                fmine = FMineViewHolder()
                fmine.firsticon = convertView.findViewById(R.id.icon)
                fmine.firsttitle = convertView.findViewById(R.id.identity)
                convertView.tag = fmine
            } else {
                fmine = convertView.tag as FMineViewHolder
            }
            fmine.firsticon!!.setBackgroundResource(list.get(position))
            fmine.firsttitle?.setText(listSecond.get(position))
            return convertView
        }
    }

    internal class FMineViewHolder {
        var firsticon: ImageView? = null
        var firsttitle: TextView? = null
    }


    private class SecondMineAdapter(val list:List<Int>,val list2:List<String>) : BaseAdapter() //"我的"页面第二个适配器
    {
        override fun getCount(): Int {
            return list.size
        }

        override fun getItem(position: Int): Any {
            return list.get(position)
        }

        override fun getItemId(position: Int): Long {
            return position.toLong()
        }

        override fun getView(position: Int, convertView: View, parent: ViewGroup): View {
            var convertView = convertView
            var smine: SMineViewHolder? = null
            if (convertView == null) {
                convertView = View.inflate(MyApplication.context, R.layout.secmine_item, null)
                smine = SMineViewHolder()
                smine.secondticon = convertView.findViewById(R.id.icon2)
                smine.secondtitle = convertView.findViewById(R.id.identity2)
                convertView.tag = smine
            } else {
                smine = convertView.tag as SMineViewHolder
            }
            smine.secondticon!!.setBackgroundResource(list.get(position))
            smine.secondtitle?.setText(list2.get(position))
            return convertView
        }
    }

    internal class SMineViewHolder {
        var secondticon: ImageView? = null
        var secondtitle: TextView? = null
    }
}