package com.example.test.domain.api

import com.example.test.domain.models.Category
import com.example.test.domain.models.Dish
import com.example.test.domain.models.PostModel
import com.example.test.domain.models.Tag
import retrofit2.Call

interface Repository {

    fun getTags(): Call<List<Tag>>

    fun getCategories(): Call<List<Category>>

    fun getProducts(): Call<List<Dish>>

    fun postOrder(cartItems: PostModel): Call<String>
}
