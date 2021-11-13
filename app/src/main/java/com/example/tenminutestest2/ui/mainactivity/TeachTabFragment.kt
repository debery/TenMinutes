package com.example.tenminutestest2.ui.mainactivity


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.tenminutestest2.R
import com.example.tenminutestest2.logic.model.Post
import com.example.tenminutestest2.ui.mainactivity.adapter.PostAdapter
import com.example.tenminutestest2.logic.model.Reply

class TeachTabFragment: Fragment() {


    private val postList=ArrayList<Post>()
    private val replyList=ArrayList<Reply>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.teach_fragment,container,false)
        initPostData()
        val recyclerView:RecyclerView?=view?.findViewById(R.id.displayRecycler)
        recyclerView?.layoutManager=LinearLayoutManager(activity)
        recyclerView?.adapter= PostAdapter(postList)
        return view
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


}