package com.wajahatkarim3.mvvm.demo.screens.posts_list

import android.app.Application
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.wajahatkarim3.mvvm.demo.base.ObservableViewModel
import com.wajahatkarim3.mvvm.demo.model.PostModel

class PostsListViewModel @JvmOverloads constructor(app: Application, val repository: PostsRepository = PostsRepository()) : ObservableViewModel(app)
{

    private var screenState = MutableLiveData<PostsScreenState>()
    private var postsList = MutableLiveData<List<PostModel>>()

    init {

    }

    fun getObservedScreenState() = screenState

    fun getObservedPostsList() = postsList

    fun loadPostsList()
    {
        screenState.postValue(
            PostsScreenState(
                showLoading = true
            ))

        repository.fetchPostsFromServer(success = {
            if (it.isEmpty())
            {
                screenState.postValue(
                    PostsScreenState(showEmpty = true)
                )
            }
            else
            {
                screenState.postValue(
                    PostsScreenState(showContent = true)
                )

                postsList.postValue(it)
            }

        }, failure = {
            screenState.postValue(
                PostsScreenState(
                    showError = true,
                    errorMessage = it
                )
            )
        })
    }
}