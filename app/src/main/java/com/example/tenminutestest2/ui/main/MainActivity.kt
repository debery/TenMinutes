package com.example.tenminutestest2.ui.main


import android.graphics.Color
import android.net.Uri
import android.os.Bundle
import android.view.*
import android.widget.*
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import com.example.tenminutestest2.BaseActivity
import com.example.tenminutestest2.R
import java.io.File


class MainActivity : BaseActivity() {

    private var teachTabFragment: TeachTabFragment?= null
    private var drawTabFragment: DrawTabFragment?= null
    private var sportTabFragment: SportTabFragment?=null
    private var userTabFragment: UserTabFragment?=null
    private var hotTabFragment: HotTabFragment?=null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //教技、画技、体育、热榜、我的

        //初始化布局，将fragment创建，add并hide
        initFragment()
        //show默认的fragment
        showFragment(teachTabFragment!!)
        //将选定的图标与文字变色，1、2、3、4、5分别是教技、绘画、体育、用户、热门（新加的）
        changeIconAndText(1)
        //主界面切换点击事件
        setClickEvent()

        //弹出popupWindow

    }
    private fun setClickEvent(){

        val tabTeach:LinearLayout=findViewById(R.id.tabTeach)
        val tabArt:LinearLayout=findViewById(R.id.tabArt)
        val tabSport:LinearLayout=findViewById(R.id.tabSport)
        val tabHot:LinearLayout=findViewById(R.id.tabHot)
        val tabMine:LinearLayout=findViewById(R.id.tabMine)
        val btnTeach:Button =findViewById(R.id.btnTeach)
        val btnArt:Button=findViewById(R.id.btnArt)
        val btnSport:Button=findViewById(R.id.btnSport)
        val btnMine:Button=findViewById(R.id.btnMine)
        val btnHot:Button=findViewById(R.id.btnHot)

        //教技
        tabTeach.setOnClickListener {
            showFragment(teachTabFragment!!)
            changeIconAndText(1)
        }
        btnTeach.setOnClickListener {
            showFragment(teachTabFragment!!)
            changeIconAndText(1)
        }

        //艺术
        tabArt.setOnClickListener {
            showFragment(drawTabFragment!!)
            changeIconAndText(2)
        }
        btnArt.setOnClickListener {
            showFragment(drawTabFragment!!)
            changeIconAndText(2)
        }
        //体育
        tabSport.setOnClickListener {
            showFragment(sportTabFragment!!)
            changeIconAndText(3)
        }
        btnSport.setOnClickListener {
            showFragment(sportTabFragment!!)
            changeIconAndText(3)
        }
        //热榜
        tabHot.setOnClickListener {
            showFragment(hotTabFragment!!)
            changeIconAndText(5)
        }
        btnHot.setOnClickListener {
            showFragment(hotTabFragment!!)
            changeIconAndText(5)
        }
        //我的
        tabMine.setOnClickListener {
            showFragment(userTabFragment!!)
            changeIconAndText(4)
        }
        btnMine.setOnClickListener {
            showFragment(userTabFragment!!)
            changeIconAndText(4)
        }
    }


    //隐藏所有fragment，用在showFragment前
    private fun hideFragment(transaction: FragmentTransaction){
        transaction.hide(teachTabFragment!!)
        transaction.hide(drawTabFragment!!)
        transaction.hide(sportTabFragment!!)
        transaction.hide(userTabFragment!!)
        transaction.hide(hotTabFragment!!)
    }

    //初始化并隐藏所有fragment
    private fun initFragment(){
        val fragmentManager=supportFragmentManager
        val transaction=fragmentManager.beginTransaction()

        if(teachTabFragment==null){
            teachTabFragment= TeachTabFragment()
            transaction.add(R.id.content_fragment, teachTabFragment!!)
            transaction.hide(teachTabFragment!!)
        }
        if(drawTabFragment==null){
            drawTabFragment= DrawTabFragment()
            transaction.add(R.id.content_fragment,drawTabFragment!!)
            transaction.hide(drawTabFragment!!)
        }

        if(sportTabFragment==null) {
            sportTabFragment = SportTabFragment()
            transaction.add(R.id.content_fragment, sportTabFragment!!)
            transaction.hide(sportTabFragment!!)
        }
        if(userTabFragment==null){
            userTabFragment= UserTabFragment()
            transaction.add(R.id.content_fragment,userTabFragment!!)
            transaction.hide(userTabFragment!!)
        }
        if(hotTabFragment==null){
            hotTabFragment= HotTabFragment()
            transaction.add(R.id.content_fragment,hotTabFragment!!)
            transaction.hide(hotTabFragment!!)
        }

        transaction.commit()
    }

    //展示fragment同时重置图标与文字
    private fun showFragment(fragment: Fragment) {
        reshowIcon()
        reshowText()
        val fragmentManager=supportFragmentManager
        val transaction=fragmentManager.beginTransaction()
        hideFragment(transaction)
        transaction.show(fragment)
        transaction.commit()
    }

    //重置Icon与Text
    private fun reshowIcon(){
        val btnTeach: Button =findViewById(R.id.btnTeach)
        val btnArt:Button=findViewById(R.id.btnArt)
        val btnSport:Button=findViewById(R.id.btnSport)
        val btnMine:Button=findViewById(R.id.btnMine)
        val btnHot:Button=findViewById(R.id.btnHot)

        btnTeach.setBackgroundResource(R.drawable.jiaoshi)
        btnArt.setBackgroundResource(R.drawable.meishu)
        btnSport.setBackgroundResource(R.drawable.yundong)
        btnMine.setBackgroundResource(R.drawable.yonghu)
        btnHot.setBackgroundResource(R.drawable.hot)
    }
    private fun reshowText(){

        val textTeach:TextView=findViewById(R.id.textTeach)
        val textArt:TextView=findViewById(R.id.textArt)
        val textSport :TextView=findViewById(R.id.textSport)
        val textMine :TextView=findViewById(R.id.textMine)
        val textHot:TextView=findViewById(R.id.textHot)

        textTeach.setTextColor(Color.parseColor("#000000"))
        textArt.setTextColor(Color.parseColor("#000000"))
        textSport.setTextColor(Color.parseColor("#000000"))
        textMine.setTextColor(Color.parseColor("#000000"))
        textHot.setTextColor(Color.parseColor("#000000"))
    }


    private fun changeIconAndText(request:Int){

        val btnTeach: Button =findViewById(R.id.btnTeach)
        val btnArt:Button=findViewById(R.id.btnArt)
        val btnSport:Button=findViewById(R.id.btnSport)
        val btnMine:Button=findViewById(R.id.btnMine)
        val btnHot:Button=findViewById(R.id.btnHot)
        val textTeach:TextView=findViewById(R.id.textTeach)
        val textArt:TextView=findViewById(R.id.textArt)
        val textSport :TextView=findViewById(R.id.textSport)
        val textMine :TextView=findViewById(R.id.textMine)
        val textHot:TextView=findViewById(R.id.textHot)


        when(request){
            1->{
                btnTeach.setBackgroundResource(R.drawable.teach_fill)
                textTeach.setTextColor(Color.parseColor("#6E9D4E"))
            }
            2->{
                btnArt.setBackgroundResource(R.drawable.draw_fill)
                textArt.setTextColor(Color.parseColor("#6E9D4E"))
            }
            3->{
                btnSport.setBackgroundResource(R.drawable.sport_fill)
                textSport.setTextColor(Color.parseColor("#6E9D4E"))
            }
            4->{
                btnMine.setBackgroundResource(R.drawable.user_fill)
                textMine.setTextColor(Color.parseColor("#6E9D4E"))
            }
            5->{
                btnHot.setBackgroundResource(R.drawable.hot_fill)
                textHot.setTextColor(Color.parseColor("#6E9D4E"))
            }
        }
    }


    //设置透明度的代码
    fun backgroundAlpha(alphaVal:Float){
        val alp:WindowManager.LayoutParams = this.window.attributes
        alp.alpha=alphaVal
        this.window.attributes=alp
        this.window.addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND)
    }




}