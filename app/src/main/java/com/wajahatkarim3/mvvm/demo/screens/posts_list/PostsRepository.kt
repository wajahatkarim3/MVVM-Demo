package com.wajahatkarim3.mvvm.demo.screens.posts_list

import com.wajahatkarim3.mvvm.demo.model.PostModel
import com.wajahatkarim3.mvvm.demo.networking.NetController
import com.wajahatkarim3.mvvm.demo.networking.PostsApi
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.net.HttpURLConnection

class PostsRepository
{
    fun fetchPostsFromServer(success: (postsList: List<PostModel>) -> Unit, failure: (message: String) -> Unit) {
        var call = NetController.createService(PostsApi::class.java).getPosts()
        call.enqueue(object : Callback<List<PostModel>> {

            override fun onResponse(call: Call<List<PostModel>>, response: Response<List<PostModel>>)
            {
                if (response.code() == HttpURLConnection.HTTP_OK)
                {
                    response.body()?.let {
                        success(it)
                        return
                    }
                    failure("Unknown Error Occurred!")
                    return
                }
                else
                {
                    // Get the error from response
                    failure("Any server error occurred!")
                }
            }

            override fun onFailure(call: Call<List<PostModel>>, t: Throwable) {
                // Show other errors like network connectivity etc here
                failure(t.localizedMessage)
                t.printStackTrace()
            }
        })
    }
}