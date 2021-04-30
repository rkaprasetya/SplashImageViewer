package com.example.utils

import com.example.data.api.Links
import com.example.data.api.ProfileImage
import com.example.data.api.ResultsItem
import com.example.data.api.SplashResponse
import com.example.data.api.Urls
import com.example.data.api.User
import com.example.data.model.ImageModel

fun makeSplashResponse():SplashResponse {
    val url = Urls("small", "thumb", "raw", "url", "full")
    val links = Links("self", "html", "photos", "likes", "download")
    val profileImage = ProfileImage("small", "large", "medium")
    val user =
        User(profileImage, "name", "lastname", links,
            "id", "firstname", "portofolio", "username")
    val resultsItem = ResultsItem(url,"color",50,"createdAt",links,"id",user,50,50)
    return SplashResponse(5,5, listOf(resultsItem))
}
fun makeSplashPost():ResultsItem{
    val url = Urls("small", "thumb", "raw", "regular", "full")
    val links = Links("self", "html", "photos", "likes", "download")
    val profileImage = ProfileImage("small", "large", "medium")
    val user =
        User(profileImage, "name", "lastname", links,
            "id", "firstname", "portofolio", "username")
    return ResultsItem(url,"color",50,"createdAt",links,"id",user,50,50)
}

fun makeSplashImage():ImageModel{
    return ImageModel(0,"username","url")
}