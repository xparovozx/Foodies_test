package com.example.test.ui

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.animateLottieCompositionAsState
import com.airbnb.lottie.compose.rememberLottieComposition
import com.example.test.R
import com.example.test.ui.theme.navigation.NavRoutesTest

@Composable
fun SplashScreen(navController: NavController) {
    val composition by rememberLottieComposition(spec = LottieCompositionSpec.RawRes(R.raw.splashscreenanimation))
    val progress by animateLottieCompositionAsState(composition = composition)

    Surface(
        color = MaterialTheme.colorScheme.primary,
        modifier = Modifier.fillMaxSize()
    ) {
        LottieAnimation(
            composition = composition,
            modifier = Modifier.fillMaxWidth(),
            enableMergePaths = true,
            progress = { progress }
        )
    }

    LaunchedEffect(key1 = progress) {
        if (progress >= .5f) {
            navController.popBackStack()
            navController.navigate(NavRoutesTest.Catalog.route)
        }
    }
}