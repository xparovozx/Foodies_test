package com.example.test.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Divider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import com.example.test.ui.theme.Typography

@Composable
fun DishListItem(
    title: String,
    count: String,
    modifier: Modifier = Modifier
) {
    Column(modifier = Modifier.fillMaxWidth()) {
        Divider(
            modifier = Modifier.fillMaxWidth()
        )
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = modifier
        ) {
            Text(
                text = title,
                style = Typography.bodyLarge,
                modifier = Modifier.alpha(.6f)
            )
            Text(
                text = count,
                style = Typography.bodyMedium
            )
        }
        Divider(
            modifier = Modifier.fillMaxWidth()
        )
    }
}