package com.example.test.domain.api

import com.example.test.domain.models.Category
import com.example.test.domain.models.Dish
import com.example.test.domain.models.PostModel
import com.example.test.domain.models.Tag
import retrofit2.Call

class ApiRepository(
    private val api: Api
): Repository {

    override fun getTags(): Call<List<Tag>> {
        return api.getTags()
    }

    override fun getCategories(): Call<List<Category>> {
        return api.getCategories()
    }

    override fun getProducts(): Call<List<Dish>> {
        return api.getProducts()
    }

    override fun postOrder(basketItems: PostModel): Call<String> {
        return api.postOrder(basketItems)
    }
}