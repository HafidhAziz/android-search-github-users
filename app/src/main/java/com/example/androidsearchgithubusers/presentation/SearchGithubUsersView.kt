package com.example.androidsearchgithubusers.presentation

import android.text.TextWatcher
import android.view.View
import android.widget.TextView

/**
 * Created by M Hafidh Abdul Aziz on 07/02/21.
 */

interface SearchGithubUsersView {
    fun setupToolbar()
    fun setupUI()
    fun setupObserver()
    fun setupScrollListener()
    fun initData()
    fun getGithubUsersFromQuery()
    fun renderList()
    var onEditorActionListener: TextView.OnEditorActionListener
    var textWatcherSearch: TextWatcher
    var onKeyListener: View.OnKeyListener
}