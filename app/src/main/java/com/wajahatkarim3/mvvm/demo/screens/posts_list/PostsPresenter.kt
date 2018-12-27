package com.wajahatkarim3.mvvm.demo.screens.posts_list

import com.wajahatkarim3.mvvm.demo.base.BasePresenter
import com.wajahatkarim3.mvvm.demo.model.PostModel
import com.wajahatkarim3.mvvm.demo.networking.NetController
import com.wajahatkarim3.mvvm.demo.networking.PostsApi
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.net.HttpURLConnection.HTTP_OK

class PostsPresenter(view: PostsContract.View) : BasePresenter<PostsContract.View>(view), PostsContract.Actions {
    var postsList = ArrayList<PostModel>()

    override fun initScreen() {
        _view?.setupViews()
    }

    override fun loadPostsList()
    {
        _view?.showLoading()
        var call = NetController.createService(PostsApi::class.java).getPosts()
        call.enqueue(object : Callback<List<PostModel>>{

            override fun onResponse(call: Call<List<PostModel>>, response: Response<List<PostModel>>)
            {
                if (response.code() == HTTP_OK)
                {
                    response.body()?.let {
                        if (it.isEmpty())
                            _view?.showEmpty()
                        else
                        {
                            postsList.clear()
                            postsList.addAll(it)
                            _view?.showContent(it)
                        }
                        return
                    }
                    _view?.showFailure("Unknown Error Occurred!")
                    return
                }
                else
                {
                    // Get the error from response
                    _view?.showFailure("Any server error occurred!")
                }
            }

            override fun onFailure(call: Call<List<PostModel>>, t: Throwable) {
                // Show other errors like network connectivity etc here
                _view?.showFailure(t.localizedMessage)
                t.printStackTrace()
            }
        })
    }


}