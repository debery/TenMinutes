package com.example.tenminutestest.ui.other

import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.viewpager.widget.PagerAdapter
import androidx.viewpager.widget.ViewPager
import com.bumptech.glide.Glide
import com.example.tenminutestest.BaseActivity
import com.example.tenminutestest.MyApplication
import com.example.tenminutestest.R
import com.example.tenminutestest.logic.model.PostB

class ImageShowActivity:BaseActivity() {

    private val imageList=ArrayList<String>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_picture_show)//从其他activity偷的
        val post=intent.getSerializableExtra("post_data_2") as PostB
        val i = intent.getIntExtra("show_position",0)

        initImageList(post)

        val viewPager: ViewPager = findViewById(R.id.picturePager)
        val pagerAdapter=object : PagerAdapter(){
            override fun getCount()=imageList.size

            override fun isViewFromObject(view: View, `object`: Any) = view==`object`

            override fun instantiateItem(container: ViewGroup, position: Int): Any {
                //在子项布局进行操作
                val view= View.inflate(MyApplication.context, R.layout.item_picture_pager,null)
                val image: ImageView =view.findViewById(R.id.pagerItem)
                //点击退出
                image.setOnClickListener {
                    finish()
                }
                view.setOnClickListener{
                    finish()
                }
                Glide.with(MyApplication.context).load(imageList[position]).into(image)
                container.addView(view)
                return view
            }


            //必须进行清除，不然可能会崩溃
            override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
                container.removeView(`object`as View)
            }
        }
        viewPager.adapter=pagerAdapter
        viewPager.currentItem=i  //根据具体情况首先展示对应界面,i来自点击图片的响应事件
    }
    private fun initImageList(post: PostB){
        post.picture_1?.let { imageList.add(it) }
        post.picture_2?.let { imageList.add(it) }
        post.picture_3?.let { imageList.add(it) }
        post.picture_4?.let { imageList.add(it) }
        post.picture_5?.let { imageList.add(it) }
        post.picture_6?.let { imageList.add(it) }
    }
}