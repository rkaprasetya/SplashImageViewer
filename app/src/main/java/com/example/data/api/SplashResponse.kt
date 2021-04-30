package com.example.data.api

import com.google.gson.annotations.SerializedName

data class SplashResponse(

    @field:SerializedName("total")
    val total: Int? = null,

    @field:SerializedName("total_pages")
    val totalPages: Int? = null,

    @field:SerializedName("results")
    val results: List<ResultsItem?>? = null
)

data class ProfileImage(

    @field:SerializedName("small")
    val small: String? = null,

    @field:SerializedName("large")
    val large: String? = null,

    @field:SerializedName("medium")
    val medium: String? = null
)

data class Links(

    @field:SerializedName("self")
    val self: String? = null,

    @field:SerializedName("html")
    val html: String? = null,

    @field:SerializedName("photos")
    val photos: String? = null,

    @field:SerializedName("likes")
    val likes: String? = null,

    @field:SerializedName("download")
    val download: String? = null
)

data class User(

    @field:SerializedName("profile_image")
    val profileImage: ProfileImage? = null,

    @field:SerializedName("name")
    val name: String? = null,

    @field:SerializedName("last_name")
    val lastName: String? = null,

    @field:SerializedName("links")
    val links: Links? = null,

    @field:SerializedName("id")
    val id: String? = null,

    @field:SerializedName("first_name")
    val firstName: String? = null,

    @field:SerializedName("portfolio_url")
    val portfolioUrl: String? = null,

    @field:SerializedName("username")
    val username: String? = null
)

data class Urls(

    @field:SerializedName("small")
    val small: String? = null,

    @field:SerializedName("thumb")
    val thumb: String? = null,

    @field:SerializedName("raw")
    val raw: String? = null,

    @field:SerializedName("regular")
    val regular: String? = null,

    @field:SerializedName("full")
    val full: String? = null
)

data class ResultsItem(

    @field:SerializedName("urls")
    val urls: Urls? = null,

    @field:SerializedName("color")
    val color: String? = null,

    @field:SerializedName("width")
    val width: Int? = null,

    @field:SerializedName("created_at")
    val createdAt: String? = null,

    @field:SerializedName("links")
    val links: Links? = null,

    @field:SerializedName("id")
    val id: String? = null,

    @field:SerializedName("user")
    val user: User? = null,

    @field:SerializedName("height")
    val height: Int? = null,

    @field:SerializedName("likes")
    val likes: Int? = null
)
