package com.example.androidsearchgithubusers.presentation

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.KeyEvent
import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.TextView
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.androidsearchgithubusers.R
import com.example.androidsearchgithubusers.data.Items
import com.example.androidsearchgithubusers.databinding.ActivitySearchGithubUsersBinding
import com.example.androidsearchgithubusers.presentation.adapter.GithubUsersAdapter
import com.example.androidsearchgithubusers.util.Status
import com.example.androidsearchgithubusers.util.ViewUtils
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SearchGithubUsersActivity : AppCompatActivity(), SearchGithubUsersView {

    lateinit var binding: ActivitySearchGithubUsersBinding
    private val viewModel: SearchGithubUsersViewModel by viewModels()
    private lateinit var githubUsersAdapter: GithubUsersAdapter

    companion object {
        private const val DEFAULT_SEARCH_QUERY = "android"
        private const val FIRST_PAGE = 1
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_search_github_users)

        setupToolbar()
        setupUI()
        setupObserver()
        initData()
    }

    override fun setupToolbar() {
        binding.toolbarSearch.apply {
            btnClear.setOnClickListener {
                searchInputQuery.text = null
                btnClear.visibility = View.GONE
            }
            searchInputQuery.setOnEditorActionListener(onEditorActionListener)
            searchInputQuery.addTextChangedListener(textWatcherSearch)
            searchInputQuery.setOnKeyListener(onKeyListener)
        }
    }

    override fun setupUI() {
        val searchLayoutManager = LinearLayoutManager(this)
        searchLayoutManager.orientation = LinearLayoutManager.VERTICAL
        binding.searchRecycler.layoutManager = searchLayoutManager
        githubUsersAdapter = GithubUsersAdapter()
        binding.searchRecycler.adapter = githubUsersAdapter
    }

    override fun setupObserver() {
        viewModel.searchResultData.observe(this, {
            when (it.status) {
                Status.SUCCESS -> {
                    binding.progressBar.visibility = View.GONE
                    it.data?.items?.let { items ->
                        renderList(items)
                        binding.searchRecycler.visibility = View.VISIBLE
                    }
                }
                Status.LOADING -> {
                    binding.apply {
                        progressBar.visibility = View.VISIBLE
                        searchRecycler.visibility = View.GONE
                    }
                }
                Status.ERROR -> {
                    binding.progressBar.visibility = View.GONE
                    Toast.makeText(this, it.message, Toast.LENGTH_LONG).show()
                }
            }
        })
    }

    override fun setupScrollListener() {
        val layoutManager = binding.searchRecycler.layoutManager as LinearLayoutManager
        binding.searchRecycler.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                val totalItemCount = layoutManager.itemCount
                val visibleItemCount = layoutManager.childCount
                val lastVisibleItem = layoutManager.findLastVisibleItemPosition()

                viewModel.listScrolled(visibleItemCount, lastVisibleItem, totalItemCount, "", 0)
            }
        })
    }

    override fun initData() {
        viewModel.getSearchGithubUsers(DEFAULT_SEARCH_QUERY, FIRST_PAGE)
    }

    override fun getGithubUsersFromQuery() {
        binding.toolbarSearch.searchInputQuery.text.toString().trim().let {
            if (it.isNotEmpty()) {
                binding.searchRecycler.scrollToPosition(0)
                viewModel.getSearchGithubUsers(it, FIRST_PAGE)
            }
        }
    }

    override fun renderList(items: List<Items>) {
        githubUsersAdapter.submitList(items)
    }

    override var onEditorActionListener: TextView.OnEditorActionListener
        get() = TextView.OnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                getGithubUsersFromQuery()
                ViewUtils.hideKeyboard(this, binding.toolbarSearch.searchInputQuery)
                return@OnEditorActionListener true
            }
            false
        }
        set(_) {}

    override var textWatcherSearch: TextWatcher
        get() = object : TextWatcher {

            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {}

            override fun afterTextChanged(s: Editable?) {
                s?.let {
                    if (it.isBlank()) {
                        binding.toolbarSearch.btnClear.visibility = View.GONE
                        initData()
                    } else {
                        binding.toolbarSearch.btnClear.visibility = View.VISIBLE
                    }
                }
            }
        }
        set(_) {}

    override var onKeyListener: View.OnKeyListener
        get() = View.OnKeyListener { _, keyCode, event ->
            if (event.action == KeyEvent.ACTION_DOWN && keyCode == KeyEvent.KEYCODE_ENTER) {
                getGithubUsersFromQuery()
                ViewUtils.hideKeyboard(this, binding.toolbarSearch.searchInputQuery)
                true
            } else {
                false
            }
        }
        set(_) {}
}