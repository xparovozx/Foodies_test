package com.example.test.domain.usecases

data class UseCases(
    val getTagsUseCase: GetTagsUseCase,
    val getCategoriesUseCase: GetCategoriesUseCase,
    val getProductsUseCase: GetProductsUseCase
)
