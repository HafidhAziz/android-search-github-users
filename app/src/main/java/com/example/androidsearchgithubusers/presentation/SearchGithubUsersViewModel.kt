package com.example.androidsearchgithubusers.presentation

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.androidsearchgithubusers.data.SearchResultsEntity
import com.example.androidsearchgithubusers.repository.AppRepository
import com.example.androidsearchgithubusers.util.Resource
import kotlinx.coroutines.launch

/**
 * Created by M Hafidh Abdul Aziz on 07/02/21.
 */

class SearchGithubUsersViewModel @ViewModelInject constructor(
    private val appRepository: AppRepository
) : ViewModel() {

    companion object {
        private const val PER_PAGE = 10
    }

    private val _searchResultData = MutableLiveData<Resource<SearchResultsEntity>>()
    val searchResultData: LiveData<Resource<SearchResultsEntity>>
        get() = _searchResultData

    fun getSearchGithubUsers(query: String, page: Int) {
        viewModelScope.launch {
            _searchResultData.postValue(Resource.loading(null))
            appRepository.getGithubUsers(query, PER_PAGE, page).let {
                if (it.isSuccessful) {
                    _searchResultData.postValue(Resource.success(it.body()))
                } else _searchResultData.postValue(Resource.error(it.errorBody().toString(), null))
            }
        }
    }

    fun listScrolled(
        visibleItemCount: Int,
        lastVisibleItemPosition: Int,
        totalItemCount: Int,
        query: String,
        page: Int
    ) {
        if ((lastVisibleItemPosition + visibleItemCount) >= totalItemCount) {
            if (page < searchResultData.value?.data?.total_count ?: 0) {
                getSearchGithubUsers(query, page)
            }
        }
    }

}