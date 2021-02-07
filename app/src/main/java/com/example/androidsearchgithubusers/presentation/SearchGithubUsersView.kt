package com.example.androidsearchgithubusers.presentation

import android.text.TextWatcher
import android.text.method.KeyListener
import android.view.View
import android.widget.TextView
import com.example.androidsearchgithubusers.data.Items

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
    fun renderList(items: List<Items>)
    var onEditorActionListener: TextView.OnEditorActionListener
    var textWatcherSearch: TextWatcher
    var onKeyListener: View.OnKeyListener
}