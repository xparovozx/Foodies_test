package com.example.test.domain.usecases

import android.content.Context
import com.example.test.utils.Jsons
import com.example.test.domain.models.Dish

class GetProductsUseCase(
    private val context: Context
) {
    operator fun invoke(): List<Dish> {
        return Jsons.getAssetDish(context) ?: listOf()
    }
}