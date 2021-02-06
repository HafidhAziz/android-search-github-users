package com.example.androidsearchgithubusers.data

import com.squareup.moshi.Json

/**
 * Created by M Hafidh Abdul Aziz on 06/02/21.
 */

data class SearchResultsEntity(
    @Json(name = "total_count")
    val totalCount: Int? = 0,
    @Json(name = "items")
    val items: Items? = null
)

data class Items(
    @Json(name = "login")
    val login: String? = null,
    @Json(name = "id")
    val id: Long? = null,
    @Json(name = "avatar_url")
    val avatarUrl: String? = null,
    @Json(name = "score")
    val score: Float? = null
)