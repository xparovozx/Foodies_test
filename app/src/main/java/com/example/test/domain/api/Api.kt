package com.example.test.domain.api

import com.example.test.domain.models.PostModel
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.POST
import com.example.test.domain.models.Category
import com.example.test.domain.models.Dish
import com.example.test.domain.models.Tag

interface Api {

    @GET("/get/tags")
    @Headers("Content-Type: application/json")
    fun getTags(): Call<List<Tag>>

    @GET("/get/categories")
    @Headers("Content-Type: application/json")
    fun getCategories(): Call<List<Category>>

    @GET("/get/products")
    @Headers("Content-Type: application/json")
    fun getProducts(): Call<List<Dish>>

    @POST("/post/")
    fun postOrder(body: PostModel): Call<String>
}