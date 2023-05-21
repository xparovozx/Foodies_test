package com.example.test.utils

import android.content.Context
import com.example.test.domain.models.Category
import com.example.test.domain.models.Dish
import com.example.test.domain.models.Tag
import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Moshi
import com.squareup.moshi.Types
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory

object Jsons {

    private val moshi = Moshi.Builder()
        .add(KotlinJsonAdapterFactory())
        .build()

    fun getAssetDish(context: Context): List<Dish>? {
        val listType = Types.newParameterizedType(List::class.java, Dish::class.java)
        val adapter: JsonAdapter<List<Dish>> = moshi.adapter(listType)
        val file = "products.json"
        val json = context.assets.open(file).bufferedReader().use { it.readText() }

        return adapter.fromJson(json)
    }

    fun getAssetCategory(context: Context): List<Category>? {
        val listType = Types.newParameterizedType(List::class.java, Category::class.java)
        val adapter: JsonAdapter<List<Category>> = moshi.adapter(listType)
        val file = "categories.json"
        val json = context.assets.open(file).bufferedReader().use { it.readText() }

        return adapter.fromJson(json)
    }

    fun getAssetTag(context: Context): List<Tag>? {
        val listType = Types.newParameterizedType(List::class.java, Tag::class.java)
        val adapter: JsonAdapter<List<Tag>> = moshi.adapter(listType)
        val file = "tags.json"
        val json = context.assets.open(file).bufferedReader().use { it.readText() }

        return adapter.fromJson(json)
    }
}