package com.example.test.domain.models

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Dish(
    @Json(name = "id") val id: Int,
    @Json(name = "name") val name: String,
    @Json(name = "description") val description: String,
    @Json(name = "image") val image: String,
    @Json(name = "price_current") val price_current: Int,
    @Json(name = "price_old") val price_old: Int?,
    @Json(name = "category_id") val category_id: Int,
    @Json(name = "measure") val measure: Int,
    @Json(name = "measure_unit") val measure_unit: String,
    @Json(name = "energy_per_100_grams") val energy_per_100_grams: Double,
    @Json(name = "proteins_per_100_grams") val proteins_per_100_grams: Double,
    @Json(name = "fats_per_100_grams") val fats_per_100_grams: Double,
    @Json(name = "carbohydrates_per_100_grams") val carbohydrates_per_100_grams: Double,
    @Json(name = "tag_ids") val tag_ids: List<Int>,
)