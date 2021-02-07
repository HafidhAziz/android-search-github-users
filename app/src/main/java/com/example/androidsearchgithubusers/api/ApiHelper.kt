package com.example.androidsearchgithubusers.api

import com.example.androidsearchgithubusers.data.SearchResultsEntity
import retrofit2.Response

/**
 * Created by M Hafidh Abdul Aziz on 06/02/21.
 */

interface ApiHelper {

    suspend fun getGithubUsers(
        query: String,
        perPage: Int,
        page: Int
    ): Response<SearchResultsEntity>
}