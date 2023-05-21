package com.example.test.domain.usecases

import android.content.Context
import com.example.test.utils.Jsons
import com.example.test.domain.models.Tag

class GetTagsUseCase(
    private val context: Context
) {
    operator fun invoke(): List<Tag> {
        return Jsons.getAssetTag(context) ?: listOf()
    }
}