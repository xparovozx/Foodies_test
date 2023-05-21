package com.example.test.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.BottomSheetScaffold
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.SheetValue
import androidx.compose.material3.Text
import androidx.compose.material3.rememberBottomSheetScaffoldState
import androidx.compose.material3.rememberStandardBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.test.R
import com.example.test.ui.theme.navigation.NavRoutesTest
import com.example.test.viewmodel.MainViewModel
import kotlinx.coroutines.launch
import com.example.test.ui.theme.Typography

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CatalogScreen(
    navController: NavController,
    vm: MainViewModel
) {

    val basket = vm.basketList.observeAsState()
    val bottomSheetState =
        rememberStandardBottomSheetState(initialValue = SheetValue.Hidden)
    val lazyGridScrollState = rememberLazyGridState()
    val dishes = vm.filteredDishList.observeAsState()
    val coroutineScope = rememberCoroutineScope()
    val isBasketNotEmpty =
        remember { derivedStateOf { basket.value != null && basket.value!!.isNotEmpty() } }
    val tags = vm.filtersList.observeAsState()
    val checkedTags = vm.checkedTagsList.observeAsState()
    val discountTag = vm.discountOnly.observeAsState()
    val categories = vm.categories.observeAsState(listOf())
    val currentPrice = vm.currentPrice.observeAsState(0)
    val currentCategory = vm.currentCategory.observeAsState(categories.value.first().id)

    BottomSheetScaffold(
        topBar = {
            ComTopLine(
                categories = categories.value,
                currentCategory = currentCategory.value,
                isScrolled = remember { derivedStateOf { lazyGridScrollState.firstVisibleItemScrollOffset > 0 } }.value,
                onFilterClick = {
                    coroutineScope.launch {
                        if (!bottomSheetState.isVisible)
                            bottomSheetState.expand()
                    }
                },
                onSearchClick = {
                    navController.navigate(NavRoutesTest.Search.route)
                },
                onCategoryClick = {
                    if (currentCategory.value != it) {
                        vm.onCategoryClick(it)
                        coroutineScope.launch {
                            lazyGridScrollState.scrollToItem(index = 1)
                        }
                    }
                },
                badgeValue = if (checkedTags.value != null) {
                    if (discountTag.value == true)
                        checkedTags.value!!.size + 1
                    else
                        checkedTags.value!!.size
                } else 0
            )
        },
        containerColor = Color.White,
        sheetContainerColor = Color.Transparent,
        sheetSwipeEnabled = false,
        sheetShape = RectangleShape,
        sheetPeekHeight = 0.dp,
        scaffoldState = rememberBottomSheetScaffoldState(bottomSheetState = bottomSheetState),
        sheetDragHandle = {},
        sheetContent = {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .fillMaxWidth()
                    .background(
                        color = Color.Black.copy(alpha = .6f)
                    )
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f, fill = true)
                        .clickable {
                            coroutineScope.launch {
                                vm.onFiltersChanged()
                                bottomSheetState.hide()
                            }
                        }
                )
                Column(
                    modifier = Modifier
                        .background(
                            color = Color.White,
                            shape = RoundedCornerShape(topStart = 50.dp, topEnd = 50.dp)
                        )
                ) {
                    Text(
                        text = stringResource(R.string.pick_up_dishes),
                        style = Typography.titleMedium,
                        modifier = Modifier
                            .padding(top = 32.dp, start = 24.dp, end = 24.dp)
                            .fillMaxWidth()
                    )
                    LazyColumn(
                        modifier = Modifier
                            .height(200.dp)
                            .padding(vertical = 16.dp, horizontal = 24.dp)
                    ) {
                        if (tags.value != null)
                            items(tags.value!!) {
                                Box {
                                    CatalogFilter(
                                        title = it.name,
                                        checked = checkedTags.value!!.contains(it.id),
                                        onCheckedChange = { newValue ->
                                            vm.checkTag(
                                                newValue = newValue,
                                                tagId = it.id
                                            )
                                        },
                                        modifier = Modifier.fillMaxWidth()
                                    )
                                    Divider(
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .align(Alignment.BottomCenter)
                                    )
                                }
                            }
                        item {
                            CatalogFilter(
                                title = stringResource(R.string.discount_tag),
                                checked = if (discountTag.value != null) discountTag.value!! else false,
                                onCheckedChange = { vm.checkDiscountTag(it) },
                                modifier = Modifier.fillMaxWidth()
                            )
                        }
                    }
                    ComButton(
                        onClick = {
                            coroutineScope.launch {
                                vm.onFiltersChanged()
                                bottomSheetState.hide()
                            }
                        }
                    ) {
                        Text(text = stringResource(R.string.done))
                    }
                }
            }
        }
    ) { paddingValues ->
        if (!dishes.value.isNullOrEmpty() && dishes.value!!.isNotEmpty()) {
            Box(modifier = Modifier.fillMaxSize()) {
                LazyVerticalGrid(
                    state = lazyGridScrollState,
                    columns = GridCells.Fixed(count = 2),
                    modifier = Modifier
                        .padding(paddingValues)
                        .fillMaxSize()
                        .padding(bottom = if (isBasketNotEmpty.value) 70.dp else 0.dp)
                ) {
                    items(dishes.value!!) { dish ->
                        BasketDish(
                            dish = dish,
                            inBacketCount = basket.value?.firstOrNull { it.id == dish.id }?.count ?: 0,
                            modifier = Modifier.padding(8.dp),
                            onCardClick = {
                                navController.navigate(route = "${NavRoutesTest.Dish.route}/${dish.id}")
                            },
                            onAdd = { thisDish ->
                                vm.addToBasket(thisDish)
                            },
                            onRemove = { thisDish ->
                                vm.removeFromBasket(thisDish)
                            },
                            labels = {
                                if (dish.price_old != null)
                                    Image(
                                        painter = painterResource(id = R.drawable.ic_sales),
                                        contentDescription = "Discount",
                                        modifier = Modifier
                                            .size(28.dp)
                                            .padding(start = 4.dp)
                                    )
                                if (remember { derivedStateOf { tags.value?.firstOrNull { it.name == "Острое" } != null } }.value)
                                    if (dish.tag_ids.contains(tags.value!!.first { it.name == "Острое" }.id))
                                        Image(
                                            painter = painterResource(id = R.drawable.ic_hot),
                                            contentDescription = "Spicy",
                                            modifier = Modifier
                                                .size(28.dp)
                                                .padding(start = 4.dp)
                                        )
                                if (remember { derivedStateOf { tags.value?.firstOrNull { it.name == "Вегетарианское блюдо" } != null } }.value)
                                    if (dish.tag_ids.contains(tags.value!!.first { it.name == "Вегетарианское блюдо" }.id))
                                        Image(
                                            painter = painterResource(id = R.drawable.ic_vegan),
                                            contentDescription = "Vegetables",
                                            modifier = Modifier
                                                .size(28.dp)
                                                .padding(start = 4.dp)
                                        )
                            }
                        )
                    }
                }
                if (isBasketNotEmpty.value) {
                    ComButton(
                        onClick = { navController.navigate(NavRoutesTest.Basket.route) },
                        modifier = Modifier
                            .align(Alignment.BottomCenter)
                            .shadow(
                                elevation = 24.dp,
                                shape = RectangleShape
                            )
                    ) {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_cart),
                            contentDescription = null,
                            modifier = Modifier.size(24.dp)
                        )
                        Text(
                            text = "${basket.value?.size}" + stringResource(R.string.count) + " ${currentPrice.value / 100} \u20BD",
                            style = Typography.titleMedium,
                            modifier = Modifier.padding(start = 8.dp)
                        )
                    }
                }
            }
        } else {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding()
            ) {
                Text(
                    text = stringResource(R.string.empty_catalog),
                    modifier = Modifier
                        .alpha(.6f)
                        .align(Alignment.Center)
                )
                if (isBasketNotEmpty.value) {
                    ComButton(
                        onClick = { navController.navigate(NavRoutesTest.Basket.route) },
                        modifier = Modifier
                            .align(Alignment.BottomCenter)
                            .shadow(
                                elevation = 24.dp,
                                shape = RectangleShape
                            )
                    ) {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_cart),
                            contentDescription = null,
                            modifier = Modifier.size(24.dp)
                        )
                        Text(
                            text = "${basket.value?.size}" + stringResource(R.string.count) + " ${currentPrice.value / 100} \u20BD",
                            style = Typography.titleMedium,
                            modifier = Modifier.padding(start = 8.dp)
                        )
                    }
                }
            }
        }
    }
}

