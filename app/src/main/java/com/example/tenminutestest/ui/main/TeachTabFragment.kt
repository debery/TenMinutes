package com.example.tenminutestest.ui.main


import android.content.Intent
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import android.widget.Button
import android.widget.PopupWindow
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.tenminutestest.R
import com.example.tenminutestest.logic.model.Post
import com.example.tenminutestest.ui.main.adapter.PostAdapter
import com.example.tenminutestest.logic.model.Reply
import com.example.tenminutestest.ui.other.AddPostActivity
import com.example.tenminutestest.ui.other.NoticeActivity
import com.example.tenminutestest.ui.other.SearchActivity

class TeachTabFragment: Fragment() {


    private val postList=ArrayList<Post>()
    private val replyList=ArrayList<Reply>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_teach,container,false)
        initPostData()
        val recyclerView:RecyclerView?=view?.findViewById(R.id.displayRecycler)
        recyclerView?.layoutManager=LinearLayoutManager(activity)
        recyclerView?.adapter= PostAdapter(postList)
        return view
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        val btnSearch: Button? =view?.findViewById(R.id.btnSearch)
        val btnAddFolder: Button? =view?.findViewById(R.id.addFolder)
        val btnNotice: Button? =view?.findViewById(R.id.notice)


        btnAddFolder?.setOnClickListener {
            showPopWindow()
        }
        btnNotice?.setOnClickListener {
            val intent = Intent(activity, NoticeActivity::class.java)
            startActivity(intent)
        }
        btnSearch?.setOnClickListener {
            val intent=Intent(activity, SearchActivity::class.java)
            startActivity(intent)
        }

    }

    private fun initPostData(){

        replyList.add(Reply(100001,"波神",R.drawable.xing_bo_head,"tql",1))
        replyList.add(Reply(100002,"陈皇",R.drawable.chen,"qs",2))
        replyList.add(Reply(100001,"波神",R.drawable.xing_bo_head,"tql",1))
        replyList.add(Reply(100002,"陈皇",R.drawable.chen,"qs",2))
        postList.add(
            Post(100000001,"区神",
            R.drawable.little_dog,"无敌","无敌",
            R.drawable.ou_he_ming,replyList = replyList)
        )
        postList.add(Post(100000002,"boShen", R.drawable.xing_bo_head,"确实","qs"))
        postList.add(
            Post(100000003,"陈皇", R.drawable.chen,"仙之巅，傲世间，有我伊豪便有天","凡人，休得僭越",
                R.drawable.sword_girl, R.drawable.mode_leader, R.drawable.maid_picture,
                R.drawable.under_tree
            )
        )
        postList.add(
            Post(100000004,"boShen",
            R.drawable.xing_bo_head,"确实","qs",
            R.drawable.lovely_picture,
            R.drawable.maid
        )
        )

    }
    private fun showPopWindow(){

        val contentView= LayoutInflater.from(activity).inflate(R.layout.pop_create_post,null)
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
            val intent= Intent(activity, AddPostActivity::class.java)
            startActivity(intent)
        }

        btnCancel.setOnClickListener {
            popupWindow.dismiss()
            backgroundAlpha(1f)
        }
    }

    //设置透明度的代码
    private fun backgroundAlpha(alphaVal:Float){
        val activity:MainActivity= activity as MainActivity
        activity.backgroundAlpha(alphaVal)
    }


}