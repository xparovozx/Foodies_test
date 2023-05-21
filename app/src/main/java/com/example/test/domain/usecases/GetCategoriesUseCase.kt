package com.example.test.domain.usecases

import android.content.Context
import com.example.test.domain.models.Category
import com.example.test.utils.Jsons

class GetCategoriesUseCase(
    private val context: Context
) {

    operator fun invoke(): List<Category> {
        return Jsons.getAssetCategory(context) ?: listOf()
    }

}