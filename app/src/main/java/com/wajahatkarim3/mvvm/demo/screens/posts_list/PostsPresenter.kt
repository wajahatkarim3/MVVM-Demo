package com.wajahatkarim3.mvvm.demo.screens.posts_list

import com.wajahatkarim3.mvvm.demo.base.BasePresenter
import com.wajahatkarim3.mvvm.demo.model.PostModel
import com.wajahatkarim3.mvvm.demo.networking.NetController
import com.wajahatkarim3.mvvm.demo.networking.PostsApi
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.net.HttpURLConnection.HTTP_OK

class PostsPresenter(view: PostsContract.View, var repository: PostsRepository) : BasePresenter<PostsContract.View>(view), PostsContract.Actions {
    var postsList = ArrayList<PostModel>()

    override fun initScreen() {
        _view?.setupViews()
    }

    override fun loadPostsList()
    {
        _view?.showLoading()
        repository.fetchPostsFromServer(success = {
            if (it.isEmpty())
                _view?.showEmpty()
            else
            {
                postsList.clear()
                postsList.addAll(it)
                _view?.showContent(it)
            }
        }, failure = {
            _view?.showFailure("Any server error occurred!")
        })
    }


}