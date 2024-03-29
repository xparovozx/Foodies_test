package com.example.test.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Badge
import androidx.compose.material3.BadgedBox
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.test.R
import com.example.test.domain.models.Category

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ComTopLine(
    categories: List<Category>,
    currentCategory: Int,
    isScrolled: Boolean,
    badgeValue: Int,
    onFilterClick: () -> Unit = {},
    onSearchClick: () -> Unit = {},
    onCategoryClick: (Int) -> Unit = {}
) {

    Column(
        modifier = Modifier.shadow(if (isScrolled) 8.dp else 0.dp)
    ) {
        CenterAlignedTopAppBar(
            title = {
                Image(
                    painter = painterResource(id = R.drawable.logo),
                    contentDescription = "Foodies logo",
                    contentScale = ContentScale.FillBounds,
                    modifier = Modifier.size(width = 111.dp, height = 44.dp)
                )
            },
            navigationIcon = {
                BadgedBox(
                    badge = {
                        if (badgeValue != 0)
                            Badge(
                                containerColor = MaterialTheme.colorScheme.primary,
                                contentColor = MaterialTheme.colorScheme.onPrimary,
                            ) {
                                Text(text = badgeValue.toString())
                            }
                    },
                    modifier = Modifier.padding(start = 8.dp).size(30.dp)
                ) {
                    IconButton(
                        onClick = {
                            onFilterClick()
                        }
                    ) {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_filter),
                            contentDescription = "Filters",
                            modifier = Modifier.size(24.dp)
                        )
                    }
                }
            },
            actions = {
                IconButton(onClick = {
                    onSearchClick()
                }) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_search),
                        contentDescription = "Search dish",
                        modifier = Modifier.size(24.dp)
                    )
                }
            },
            colors = TopAppBarDefaults.centerAlignedTopAppBarColors(containerColor = Color.White)
        )

        LazyRow(
            horizontalArrangement = Arrangement.SpaceEvenly,
            modifier = Modifier
                .background(Color.White)
                .padding(all = 8.dp)
        ) {
            items(categories) { tag ->
                Text(
                    text = tag.name,
                    color = if (tag.id == currentCategory) Color.White else Color.Black,
                    style = MaterialTheme.typography.bodyLarge,
                    modifier = Modifier
                        .padding(horizontal = 4.dp)
                        .clickable {
                            onCategoryClick(tag.id)
                        }
                        .background(
                            color = if (tag.id == currentCategory)
                                MaterialTheme.colorScheme.primary
                            else
                                Color.Transparent,
                            shape = RoundedCornerShape(8.dp)
                        )
                        .padding(horizontal = 16.dp, vertical = 12.dp)
                )
            }
        }
    }
}