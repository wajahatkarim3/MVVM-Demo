package com.wajahatkarim3.mvvm.demo.screens.posts_list

import com.wajahatkarim3.mvvm.demo.model.PostModel

interface PostsContract {

    interface View
    {
        fun setupViews()
        fun showLoading()
        fun showFailure(message: String)
        fun showEmpty()
        fun showContent(postsList: List<PostModel>)
    }

    interface Actions
    {
        fun initScreen()
        fun loadPostsList()
    }

    interface Repository
    {
        fun fetchPostsFromServer(success: (postsList: List<PostModel>) -> Unit, failure: (message: String) -> Unit)
    }
}