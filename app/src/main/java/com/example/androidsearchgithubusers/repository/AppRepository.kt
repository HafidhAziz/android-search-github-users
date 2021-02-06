package com.example.androidsearchgithubusers.repository

import com.example.androidsearchgithubusers.api.ApiHelper
import javax.inject.Inject

/**
 * Created by M Hafidh Abdul Aziz on 06/02/21.
 */

class AppRepository @Inject constructor(private val apiHelper: ApiHelper) {

    suspend fun getGithubUsers(query: String, perPage: Int, page: Int) =
        apiHelper.getGithubUsers(query, perPage, page)
}