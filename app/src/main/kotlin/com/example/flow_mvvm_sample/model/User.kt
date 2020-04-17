package com.example.flow_mvvm_sample.model

import com.squareup.moshi.Json
import java.util.*

data class User(
    val login: String,
    val id: Long,
    @Json(name = "avatar_url")
    val avatar_url: String,
    @Json(name = "gravatar_id")
    val gravatar_id: String,
    val url: String,
    @Json(name = "html_url")
    val htmlUrl: String,
    @Json(name = "followers_url")
    val followersUrl: String,
    @Json(name = "following_url")
    val followingUrl: String,
    @Json(name = "gists_url")
    val gistsUrl: String,
    @Json(name = "starred_url")
    val starredUrl: String,
    @Json(name = "subscriptions_url")
    val subscriptionsUrl: String,
    @Json(name = "organizations_url")
    val organizationsUrl: String,
    @Json(name = "repos_url")
    val reposUrl: String,
    @Json(name = "events_url")
    val eventsUrl: String,
    @Json(name = "received_events_url")
    val receivedEventsUrl: String,
    val type: String,
    val name: String,
    val blog: String,
    val location: String,
    val email: String,
    @Json(name = "public_repos")
    val publicRepos: Int,
    @Json(name = "public_gists")
    val publicGists: Int,
    val followers: Int,
    val following: Int,
    @Json(name = "created_at")
    val createdAt: Date,
    @Json(name = "updated_at")
    val updatedAt: Date
)