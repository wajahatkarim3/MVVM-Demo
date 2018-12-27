package com.wajahatkarim3.mvvm.demo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.kennyc.view.MultiStateView
import com.thetechnocafe.gurleensethi.liteutils.RecyclerAdapterUtil
import com.wajahatkarim3.mvvm.demo.databinding.ActivityMainBinding
import com.wajahatkarim3.mvvm.demo.databinding.PostItemLayoutBinding
import com.wajahatkarim3.mvvm.demo.model.PostModel
import com.wajahatkarim3.mvvm.demo.screens.posts_list.PostsContract
import com.wajahatkarim3.mvvm.demo.screens.posts_list.PostsPresenter
import com.wajahatkarim3.mvvm.demo.screens.posts_list.PostsRepository

class PostsActivity : AppCompatActivity(), PostsContract.View {

    lateinit var bi: ActivityMainBinding
    lateinit var presenter: PostsPresenter
    var recyclerAdapter: RecyclerAdapterUtil<PostModel>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bi = DataBindingUtil.setContentView(this, R.layout.activity_main)

        presenter = PostsPresenter(this, PostsRepository())
        bi.view = this
        bi.presenter = presenter

        presenter.initScreen()
        presenter.loadPostsList()
    }

    override fun setupViews() {
        // RecyclerView
        recyclerAdapter = RecyclerAdapterUtil(this, presenter.postsList, R.layout.post_item_layout)
        recyclerAdapter?.addOnDataBindListener { itemView, item, position, innerViews ->
            var bbb = DataBindingUtil.bind<PostItemLayoutBinding>(itemView)
            bbb?.let {
                it.postModel = item
                it.executePendingBindings()
            }
        }
        bi.listRecyclerView.adapter = recyclerAdapter
        bi.listRecyclerView.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)

        // Multistate
        bi.multistate.getView(MultiStateView.VIEW_STATE_ERROR)
                ?.findViewById<Button>(R.id.retry)?.setOnClickListener{
                    presenter.loadPostsList()
                }

    }

    override fun showEmpty() {
        bi.multistate.viewState = MultiStateView.VIEW_STATE_EMPTY
    }

    override fun showFailure(message: String) {
        bi.multistate.viewState = MultiStateView.VIEW_STATE_ERROR
    }

    override fun showLoading() {
        bi.multistate.viewState = MultiStateView.VIEW_STATE_LOADING
    }

    override fun showContent(postsList: List<PostModel>) {
        recyclerAdapter?.notifyDataSetChanged()
        bi.multistate.viewState = MultiStateView.VIEW_STATE_CONTENT
    }
}
