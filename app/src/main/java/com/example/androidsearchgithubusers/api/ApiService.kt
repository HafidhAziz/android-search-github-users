package com.example.androidsearchgithubusers.api

import com.example.androidsearchgithubusers.data.SearchResultsEntity
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * Created by M Hafidh Abdul Aziz on 06/02/21.
 */

interface ApiService {

    @GET("/search/users")
    suspend fun getGithubUsers(
        @Query("q") query: String,
        @Query("per_page") perPage: Int,
        @Query("page") page: Int
    ): Response<SearchResultsEntity>
}