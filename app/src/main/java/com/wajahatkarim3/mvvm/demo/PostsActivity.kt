package com.wajahatkarim3.mvvm.demo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.kennyc.view.MultiStateView
import com.thetechnocafe.gurleensethi.liteutils.RecyclerAdapterUtil
import com.wajahatkarim3.mvvm.demo.databinding.ActivityMainBinding
import com.wajahatkarim3.mvvm.demo.databinding.PostItemLayoutBinding
import com.wajahatkarim3.mvvm.demo.model.PostModel
import com.wajahatkarim3.mvvm.demo.screens.posts_list.*
import java.util.prefs.PreferencesFactory

class PostsActivity : AppCompatActivity() {

    lateinit var bi: ActivityMainBinding
    //lateinit var presenter: PostsPresenter

    // MVVM
    lateinit var viewModel: PostsListViewModel
    private val postsList = ArrayList<PostModel>()

    var recyclerAdapter: RecyclerAdapterUtil<PostModel>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bi = DataBindingUtil.setContentView(this, R.layout.activity_main)

        viewModel = ViewModelProviders.of(this).get(PostsListViewModel::class.java)
        setupViews()
        initObservations()
        viewModel.loadPostsList()
    }


    fun initObservations()
    {
        // Screen State
        viewModel.getObservedScreenState().observe(this, Observer {
            onScreenStateChanged(it)
        })

        // Posts List
        viewModel.getObservedPostsList().observe(this, Observer {
            postsList.clear()
            postsList.addAll(it)
            recyclerAdapter?.notifyDataSetChanged()
        })
    }

    private fun onScreenStateChanged(state: PostsScreenState?) {
        state?.let {
            when(true)
            {
                it.showEmpty -> showEmpty()
                it.showError -> showFailure(it.errorMessage)
                it.showLoading -> showLoading()
                it.showContent -> showContent()
            }
        }
    }


    fun setupViews() {
        // RecyclerView
        recyclerAdapter = RecyclerAdapterUtil(this, postsList, R.layout.post_item_layout)
        recyclerAdapter?.addOnDataBindListener { itemView, item, position, innerViews ->
            var bbb = DataBindingUtil.bind<PostItemLayoutBinding>(itemView)
            bbb?.let {
                it.postModel = item
                it.executePendingBindings()
            }
        }
        bi.listRecyclerView.adapter = recyclerAdapter
        bi.listRecyclerView.layoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)

        // Multistate
        //bi.multistate.getView(MultiStateView.VIEW_STATE_ERROR)
        //        ?.findViewById<Button>(R.id.retry)?.setOnClickListener{
        //            loadPostsList()
        //        }

    }


    fun showEmpty() {
        bi.multistate.viewState = MultiStateView.VIEW_STATE_EMPTY
    }

    fun showFailure(message: String) {
        bi.multistate.viewState = MultiStateView.VIEW_STATE_ERROR
    }

    fun showLoading() {
        bi.multistate.viewState = MultiStateView.VIEW_STATE_LOADING
    }

    fun showContent() {
        recyclerAdapter?.notifyDataSetChanged()
        bi.multistate.viewState = MultiStateView.VIEW_STATE_CONTENT
    }
}
