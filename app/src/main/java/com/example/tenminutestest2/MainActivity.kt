package com.example.tenminutestest2


import android.app.Activity
import android.content.Intent
import android.graphics.BitmapFactory
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.net.Uri
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.view.*
import android.widget.*
import androidx.core.content.FileProvider
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import java.io.File


class MainActivity : AppCompatActivity() {

    private var teachTabFragment:TeachTabFragment ?= null
    private var drawTabFragment:DrawTabFragment ?= null
    private var sportTabFragment:SportTabFragment ?=null
    private var userTabFragment:UserTabFragment ?=null
    private var hotTabFragment:HotTabFragment ?=null

    //用于上传动态时调用的获取相机
    lateinit var outputImage:File
    lateinit var imageUri: Uri

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        supportActionBar?.hide()

        //教技、画技、体育、热榜、我的
        val btnTeach: Button =findViewById(R.id.btnTeach)
        val btnDraw:Button=findViewById(R.id.btnDraw)
        val btnSport:Button=findViewById(R.id.btnSport)
        val btnUser:Button=findViewById(R.id.btnUser)
        val btnHot:Button=findViewById(R.id.btnHot)

        //搜索、通知、添加
        val btnSearch:Button=findViewById(R.id.btnSearch)
        val btnAddFolder:Button=findViewById(R.id.addFolder)
        val btnNotice:Button=findViewById(R.id.notice)

        //初始化布局，将fragment创建，add并hide
        initFragment()
        //show默认的fragment
        showFragment(teachTabFragment!!)
        //将选定的图标与文字变色，1、2、3、4、5分别是教技、绘画、体育、用户、热门（新加的）
        changeIconAndText(1)
        //主界面切换点击事件
        btnTeach.setOnClickListener {
            showFragment(teachTabFragment!!)
            changeIconAndText(1)

        }
        btnDraw.setOnClickListener {
            showFragment(drawTabFragment!!)
            changeIconAndText(2)

        }
        btnSport.setOnClickListener {
            showFragment(sportTabFragment!!)
            changeIconAndText(3)

        }
        btnUser.setOnClickListener {
            showFragment(userTabFragment!!)
            changeIconAndText(4)
        }
        btnHot.setOnClickListener {
            showFragment(hotTabFragment!!)
            changeIconAndText(5)
        }

        //弹出popupWindow

        btnAddFolder.setOnClickListener {
            showPopWindow()
        }
        btnNotice.setOnClickListener {
            val intent = Intent(this,NoticeActivity::class.java)
            startActivity(intent)
        }
        btnSearch.setOnClickListener {

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
            teachTabFragment=TeachTabFragment()
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
        val btnDraw:Button=findViewById(R.id.btnDraw)
        val btnSport:Button=findViewById(R.id.btnSport)
        val btnUser:Button=findViewById(R.id.btnUser)
        val btnHot:Button=findViewById(R.id.btnHot)

        btnTeach.setBackgroundResource(R.drawable.jiaoshi)
        btnDraw.setBackgroundResource(R.drawable.meishu)
        btnSport.setBackgroundResource(R.drawable.yundong)
        btnUser.setBackgroundResource(R.drawable.yonghu)
        btnHot.setBackgroundResource(R.drawable.hot)
    }
    private fun reshowText(){
        val textTeach:TextView=findViewById(R.id.textTeach)
        val textDraw:TextView=findViewById(R.id.textDraw)
        val textSport :TextView=findViewById(R.id.textSport)
        val textUser :TextView=findViewById(R.id.textUser)
        val textHot:TextView=findViewById(R.id.textHot)

        textTeach.setTextColor(Color.parseColor("#000000"))
        textDraw.setTextColor(Color.parseColor("#000000"))
        textSport.setTextColor(Color.parseColor("#000000"))
        textUser.setTextColor(Color.parseColor("#000000"))
        textHot.setTextColor(Color.parseColor("#000000"))
    }


    private fun changeIconAndText(request:Int){

        val btnTeach: Button =findViewById(R.id.btnTeach)
        val btnDraw:Button=findViewById(R.id.btnDraw)
        val btnSport:Button=findViewById(R.id.btnSport)
        val btnUser:Button=findViewById(R.id.btnUser)
        val btnHot:Button=findViewById(R.id.btnHot)

        val textTeach:TextView=findViewById(R.id.textTeach)
        val textDraw:TextView=findViewById(R.id.textDraw)
        val textSport :TextView=findViewById(R.id.textSport)
        val textUser :TextView=findViewById(R.id.textUser)
        val textHot:TextView=findViewById(R.id.textHot)

        val topLayout:LinearLayout=findViewById(R.id.top_layout)

        //额外设置将用户界面的topLayout隐藏或显示
        when(request){
            1->{
                btnTeach.setBackgroundResource(R.drawable.teach_fill)
                textTeach.setTextColor(Color.parseColor("#6E9D4E"))
                if(topLayout.visibility== View.GONE){
                    topLayout.visibility=View.VISIBLE
                }
            }
            2->{
                btnDraw.setBackgroundResource(R.drawable.draw_fill)
                textDraw.setTextColor(Color.parseColor("#6E9D4E"))
                if(topLayout.visibility== View.GONE){
                    topLayout.visibility=View.VISIBLE
                }
            }
            3->{
                btnSport.setBackgroundResource(R.drawable.sport_fill)
                textSport.setTextColor(Color.parseColor("#6E9D4E"))
                if(topLayout.visibility== View.GONE){
                    topLayout.visibility=View.VISIBLE
                }
            }
            4->{
                btnUser.setBackgroundResource(R.drawable.user_fill)
                textUser.setTextColor(Color.parseColor("#6E9D4E"))
                if(topLayout.visibility!= View.GONE){
                    topLayout.visibility=View.GONE
                }
            }
            5->{
                btnHot.setBackgroundResource(R.drawable.hot_fill)
                textHot.setTextColor(Color.parseColor("#6E9D4E"))
                if(topLayout.visibility!= View.GONE){
                    topLayout.visibility=View.GONE
                }
            }
        }
    }

    //弹窗的控制代码
    private fun showPopWindow(){

        val contentView= LayoutInflater.from(this).inflate(R.layout.add_post_pop,null)
        val popupWindow= PopupWindow(contentView, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
        popupWindow.isFocusable=true
        popupWindow.setBackgroundDrawable(ColorDrawable(0x800000))
        popupWindow.isOutsideTouchable=true
        popupWindow.isTouchable=true

        popupWindow.showAtLocation(contentView, Gravity.BOTTOM,0,0)
        //弹出窗口后将背景虚化
        backgroundAlpha(0.5f)
        popupWindow.setOnDismissListener {
            popupWindow.dismiss()
            backgroundAlpha(1f)
        }

        //获取popupWindow里的控件之前，使用view保存R.layout.pop生成的contentView
        val view:View =popupWindow.contentView
        val btnAdd:Button=view.findViewById(R.id.btnAdd)
        val btnCancel:Button=view.findViewById(R.id.btnCancelInPop)



        //点击”动态“
        btnAdd.setOnClickListener {
            popupWindow.dismiss()
            backgroundAlpha(1f)
            val intent=Intent(this,AddPostActivity::class.java)
            startActivity(intent)
        }

        btnCancel.setOnClickListener {
            popupWindow.dismiss()
            backgroundAlpha(1f)
        }
    }

    //设置透明度的代码
    private fun backgroundAlpha(alphaVal:Float){
        val alp:WindowManager.LayoutParams = this.window.attributes
        alp.alpha=alphaVal
        this.window.attributes=alp
        this.window.addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND)
    }

    //启动ActivityForResult后回调此处，1是在popupWindow里的cameraMethod，2是AlbumMethod
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when(requestCode){
            1->{
                if(resultCode== Activity.RESULT_OK){
                    val imageViewShow: ImageView=findViewById(R.id.imageViewShow)//
                    val bitmap= BitmapFactory.decodeStream(this.contentResolver?.openInputStream(imageUri))
                    imageViewShow.setImageBitmap(bitmap)
                }
            }
            2->{
                if(resultCode== Activity.RESULT_OK&&data!=null)
                    data.data?.let{uri ->
                        val imageViewShow: ImageView=findViewById(R.id.imageViewShow)//
                        val bitmap=contentResolver?.openFileDescriptor(uri,"r")?.use{
                            BitmapFactory.decodeFileDescriptor(it.fileDescriptor)
                        }
                        imageViewShow.setImageBitmap(bitmap)
                    }
            }
        }
    }


}