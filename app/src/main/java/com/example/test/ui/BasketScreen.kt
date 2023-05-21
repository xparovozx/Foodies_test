package com.example.test.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.test.R
import com.example.test.viewmodel.MainViewModel
import com.example.test.ui.theme.Typography

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BasketScreen(
    navController: NavController,
    vm: MainViewModel
) {
    val backetList = vm.basketList.observeAsState()
    val dishes = vm.dishList.observeAsState()
    val currentPrice = vm.currentPrice.observeAsState(0)
    val isBacketNotEmpty =
        remember { derivedStateOf { backetList.value != null && backetList.value!!.isNotEmpty() } }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = stringResource(R.string.basket),
                        modifier = Modifier.padding(start = 32.dp)
                    )
                },
                navigationIcon = {
                    IconButton(
                        onClick = { navController.navigateUp() }
                    ) {
                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = "Navigate up",
                            tint = MaterialTheme.colorScheme.primary
                        )
                    }
                },
                modifier = Modifier.shadow(elevation = 8.dp),
                colors = TopAppBarDefaults.topAppBarColors(containerColor = Color.White)
            )
        },
        bottomBar = {
            if (isBacketNotEmpty.value)
                ComButton(
                    onClick = {},
                    modifier = Modifier.shadow(
                        elevation = 24.dp,
                        shape = RectangleShape
                    )
                ) {
                    Text(
                        text = stringResource(R.string.order_for) + " ${currentPrice.value / 100} \u20BD",
                        style = Typography.bodyLarge
                    )
                }
        }
    ) { paddingValues ->
        if (isBacketNotEmpty.value) {
            LazyColumn(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Top,
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
            ) {
                items(backetList.value!!) { backetItem ->
                    val currentDish = dishes.value!!.first { it.id == backetItem.id }
                    BasketRow(
                        dish = currentDish,
                        inBasketCount = backetItem.count,
                        onAdd = {
                            vm.addToBasket(currentDish)
                        },
                        onRemove = {
                            vm.removeFromBasket(currentDish)
                        },
                        modifier = Modifier.padding(bottom = 1.dp)
                    )
                }
            }
        } else {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
            ) {
                Text(
                    text = stringResource(R.string.empty_basket),
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        .align(Alignment.Center)
                        .alpha(.6f)
                        .padding(horizontal = 68.dp)
                )
            }
        }
    }
}