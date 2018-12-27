package com.wajahatkarim3.mvvm.demo.networking

import com.wajahatkarim3.mvvm.demo.model.PostModel
import retrofit2.Call
import retrofit2.http.GET

interface PostsApi {

    @GET("posts")
    fun getPosts() : Call<List<PostModel>>

}