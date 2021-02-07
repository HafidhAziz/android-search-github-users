package com.example.androidsearchgithubusers.data

import com.squareup.moshi.Json

/**
 * Created by M Hafidh Abdul Aziz on 06/02/21.
 */

data class SearchResultsEntity(
    @Json(name = "total_count")
    val total_count: Int? = 0,
    @Json(name = "incomplete_results")
    val incomplete_results: Boolean? = false,
    @Json(name = "items")
    val items: List<Items>? = null
)

data class Items(
    @Json(name = "login")
    val login: String? = null,
    @Json(name = "id")
    val id: Long? = null,
    @Json(name = "avatar_url")
    val avatar_url: String? = null,
    @Json(name = "score")
    val score: Float? = null
)