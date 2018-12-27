package com.wajahatkarim3.mvvm.demo.screens.posts_list

data class PostsScreenState(
    val showLoading: Boolean = false,
    val showContent: Boolean = false,
    val showError: Boolean = false,
    val showEmpty: Boolean = false,
    val errorMessage: String = ""
)