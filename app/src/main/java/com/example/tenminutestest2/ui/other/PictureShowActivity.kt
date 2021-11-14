package com.example.tenminutestest2.ui.other

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.viewpager.widget.PagerAdapter
import androidx.viewpager.widget.ViewPager
import com.example.tenminutestest2.BaseActivity
import com.example.tenminutestest2.MyApplication
import com.example.tenminutestest2.R
import com.example.tenminutestest2.logic.model.Post

class PictureShowActivity : BaseActivity() {

    private val dataList:ArrayList<Int> = ArrayList<Int>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_picture_show)
        //获取数据
        val post=intent.getSerializableExtra("post_data") as Post
        initDataList(post)
        val i = intent.getIntExtra("show_position",0)

        //创建viewPager与它的适配器并加以绑定
        val viewPager:ViewPager= findViewById(R.id.picturePager)
        val pagerAdapter=object :PagerAdapter(){
            override fun getCount()=dataList.size

            override fun isViewFromObject(view: View, `object`: Any) = view==`object`

            override fun instantiateItem(container: ViewGroup, position: Int): Any {
                //在子项布局进行操作
                val view=View.inflate(MyApplication.context, R.layout.picture_pager_item,null)
                val image:ImageView=view.findViewById(R.id.pagerItem)
                image.setImageResource(dataList[position])
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
    private fun initDataList(post: Post){
        dataList.add(post.commentImage1)
        if(post.commentImage2!=0)dataList.add(post.commentImage2)
        if(post.commentImage3!=0)dataList.add(post.commentImage3)
        if(post.commentImage4!=0)dataList.add(post.commentImage4)
        if(post.commentImage5!=0)dataList.add(post.commentImage5)
        if(post.commentImage6!=0)dataList.add(post.commentImage6)
    }

}