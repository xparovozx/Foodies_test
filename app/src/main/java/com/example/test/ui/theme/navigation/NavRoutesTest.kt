package com.example.test.ui.theme.navigation

sealed class NavRoutesTest(val route: String) {
    object Splash : NavRoutesTest("splash")
    object Catalog : NavRoutesTest("catalog")
    object Dish : NavRoutesTest("dish")
    object Search: NavRoutesTest("search")
    object Basket : NavRoutesTest("basket")
}