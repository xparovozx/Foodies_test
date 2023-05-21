package com.example.test.ui.theme.navigation

import android.app.Activity
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalView
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import androidx.navigation.navigation
import com.example.test.ui.BasketScreen
import com.example.test.ui.CatalogScreen
import com.example.test.ui.DishScreen
import com.example.test.ui.SearchScreen
import com.example.test.ui.SplashScreen
import com.example.test.viewmodel.MainViewModel

@Composable
fun NavGraphTest(
    navHostController: NavHostController
) {

    val startDestination = NavRoutesTest.Splash.route

    NavHost(navController = navHostController, startDestination = "main_graph") {
        navigation(
            startDestination = startDestination,
            route = "main_graph"
        ) {
            composable(route = NavRoutesTest.Splash.route) {
                SplashScreen(navController = navHostController)
            }
            composable(route = NavRoutesTest.Catalog.route) { backStackEntry ->
                val view = LocalView.current
                val parentEntry = remember(backStackEntry) {
                    navHostController.getBackStackEntry("main_graph")
                }
                val viewModel = hiltViewModel<MainViewModel>(parentEntry)
                SideEffect {
                    val window = (view.context as Activity).window
                    window.statusBarColor = Color.White.toArgb()
                }
                CatalogScreen(
                    navController = navHostController,
                    vm = viewModel
                )
            }
            composable(route = NavRoutesTest.Basket.route) { backStackEntry ->
                val parentEntry = remember(backStackEntry) {
                    navHostController.getBackStackEntry("main_graph")
                }
                val viewModel = hiltViewModel<MainViewModel>(parentEntry)
                BasketScreen(
                    navController = navHostController,
                    vm = viewModel
                )
            }
            composable(
                route = NavRoutesTest.Dish.route + "/{id}",
                arguments = listOf(navArgument("id") {})
            ) { backStackEntry ->
                val parentEntry = remember(backStackEntry) {
                    navHostController.getBackStackEntry("main_graph")
                }
                val viewModel = hiltViewModel<MainViewModel>(parentEntry)
                DishScreen(
                    navController = navHostController,
                    dishId = backStackEntry.arguments?.getString("id")?.toInt() ?: 0,
                    vm = viewModel
                )
            }
            composable(route = NavRoutesTest.Search.route) { backStackEntry ->
                val parentEntry = remember(backStackEntry) {
                    navHostController.getBackStackEntry("main_graph")
                }
                val viewModel = hiltViewModel<MainViewModel>(parentEntry)
                SearchScreen(
                    navController = navHostController,
                    vm = viewModel
                )
            }
        }
    }
}