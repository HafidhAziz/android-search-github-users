package com.example.androidsearchgithubusers.api

import com.example.androidsearchgithubusers.data.SearchResultsEntity
import retrofit2.Response
import javax.inject.Inject

/**
 * Created by M Hafidh Abdul Aziz on 06/02/21.
 */

class ApiHelperImpl @Inject constructor(private val apiService: ApiService) : ApiHelper {

    override suspend fun getGithubUsers(
        query: String,
        perPage: Int,
        page: Int
    ): Response<SearchResultsEntity> = apiService.getGithubUsers(query, perPage, page)
}