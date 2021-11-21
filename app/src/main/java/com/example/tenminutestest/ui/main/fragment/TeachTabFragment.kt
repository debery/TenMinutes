package com.example.tenminutestest.ui.main.fragment


import android.content.Intent
import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import android.widget.Button
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.tenminutestest.R
import com.example.tenminutestest.logic.model.Post
import com.example.tenminutestest.ui.main.adapter.PostAdapter
import com.example.tenminutestest.logic.model.Comment
import com.example.tenminutestest.ui.main.AddButton
import com.example.tenminutestest.ui.other.NoticeActivity
import com.example.tenminutestest.ui.other.SearchActivity

class TeachTabFragment: Fragment() {


    private val postList=ArrayList<Post>()
    private val replyList=ArrayList<Comment>()

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
            AddButton().showWindow(activity)
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

        replyList.add(Comment(100001,"波神",R.drawable.xing_bo_head,"tql",1))
        replyList.add(Comment(100002,"陈皇",R.drawable.chen,"qs",2))
        replyList.add(Comment(100001,"波神",R.drawable.xing_bo_head,"tql",1))
        replyList.add(Comment(100002,"陈皇",R.drawable.chen,"qs",2))
        postList.add(
            Post(100000001,"区神",
            R.drawable.little_dog,"无敌","无敌",
            R.drawable.ou_he_ming,commentList = replyList)
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

}