package com.example.test.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.test.R
import com.example.test.domain.models.Dish
import com.example.test.ui.theme.TestTheme
import com.example.test.ui.theme.Typography

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BasketDish(
    dish: Dish,
    modifier: Modifier = Modifier,
    inBacketCount: Int = 0,
    onAdd: (Dish) -> Unit = {},
    onRemove: (Dish) -> Unit = {},
    onCardClick: () -> Unit = {},
    labels:@Composable () -> Unit
) {

    Card(
        onClick = { onCardClick() },
        modifier = modifier.width(170.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
    ) {

        Box {
            Image(
                painter = painterResource(id = R.drawable.photo),
                contentDescription = null,
                modifier = Modifier
                    .fillMaxWidth()
                    .align(Alignment.Center)
            )

            Row(
                modifier = Modifier
                    .align(Alignment.TopStart)
                    .padding(start = 8.dp, top = 8.dp)
            ) {
                labels()
            }
        }
        Column(
            modifier = Modifier
                .padding(12.dp)
                .fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Column {
                Text(
                    text = dish.name,
                    style = Typography.bodyMedium,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    modifier = Modifier
                        .padding(bottom = 4.dp)
                        .fillMaxWidth()
                )
                Text(
                    text = "${dish.measure} ${dish.measure_unit}",
                    style = Typography.bodyMedium,
                    modifier = Modifier.alpha(.6f)
                )
            }

            if (inBacketCount > 0)
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 12.dp)
                        .padding(vertical = 4.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Button(
                        onClick = { onRemove(dish) },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color.White,
                            contentColor = MaterialTheme.colorScheme.primary
                        ),
                        shape = RoundedCornerShape(8.dp),
                        elevation = ButtonDefaults.buttonElevation(
                            defaultElevation = 2.dp
                        ),
                        contentPadding = PaddingValues(1.dp),
                        modifier = Modifier
                            .size(40.dp)
                            .weight(1f)
                    ) {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_minus),
                            contentDescription = "Add dish",
                            tint = MaterialTheme.colorScheme.primary
                        )
                    }

                    Text(
                        text = inBacketCount.toString(),
                        textAlign = TextAlign.Center,
                        style = Typography.titleSmall,
                        modifier = Modifier.weight(1.8f)
                    )

                    Button(
                        onClick = { onAdd(dish) },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color.White,
                            contentColor = MaterialTheme.colorScheme.primary
                        ),
                        shape = RoundedCornerShape(8.dp),
                        elevation = ButtonDefaults.buttonElevation(
                            defaultElevation = 2.dp
                        ),
                        contentPadding = PaddingValues(1.dp),
                        modifier = Modifier
                            .size(40.dp)
                            .weight(1f)
                    ) {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_plus),
                            contentDescription = "Add dish",
                            tint = MaterialTheme.colorScheme.primary
                        )
                    }
                }
            else
                Button(
                    onClick = { onAdd(dish) },
                    modifier = Modifier
                        .padding(top = 12.dp)
                        .fillMaxWidth(),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color.White,
                        contentColor = MaterialTheme.colorScheme.onSurface
                    ),
                    shape = RoundedCornerShape(8.dp),
                    elevation = ButtonDefaults.buttonElevation(
                        defaultElevation = 2.dp
                    )
                ) {
                    Text(
                        text = "${dish.price_current / 100} \u20BD",
                        style = Typography.titleSmall,
                        maxLines = 1
                    )
                    if (dish.price_old != null)
                        Text(
                            text = "${dish.price_old / 100} \u20BD",
                            maxLines = 1,
                            overflow = TextOverflow.Visible,
                            textDecoration = TextDecoration.LineThrough,
                            style = Typography.titleSmall,
                            modifier = Modifier.alpha(.6f)
                        )
                }
        }
    }
}

@Preview
@Composable
fun CardPreview() {
    TestTheme {

        Row {
            BasketDish(
                inBacketCount = 0,
                dish = Dish(
                    id = 1,
                    category_id = 676168,
                    name = "Авокадо Кранч Маки 8шт",
                    description = "Ролл с нежным мясом камчатского краба, копченой курицей и авокадо.Украшается соусом\\\"Унаги\\\" и легким майонезом  Комплектуется бесплатным набором для роллов (Соевый соус Лайт 35г., васаби 6г., имбирь 15г.). +1 набор за каждые 600 рублей в заказе",
                    image = "1.jpg",
                    price_current = 47000,
                    price_old = null,
                    measure = 250,
                    measure_unit = "г",
                    energy_per_100_grams = 307.8,
                    proteins_per_100_grams = 6.1,
                    fats_per_100_grams = 11.4,
                    carbohydrates_per_100_grams = 45.1,
                    tag_ids = listOf()
                ),
                labels = {
                    Image(
                        painter = painterResource(id = R.drawable.ic_sales),
                        contentDescription = "Discount",
                        modifier = Modifier
                            .size(24.dp)
                    )
                }
            )
            Divider(modifier = Modifier.width(8.dp))
            BasketDish(
                inBacketCount = 1,
                dish = Dish(
                    id = 8,
                    category_id = 676168,
                    name = "Сезам Ролл 8шт",
                    description = "Урамаки ролл, украшенный кунжутом, с начинкой из тунца, обжаренного в соусе BBQ, огурца, сливочного сыра и зеленого лука  Комплектуется бесплатным набором для роллов (Соевый соус Лайт 35г., васаби 6г., имбирь 15г.). +1 набор за каждые 600 рублей в заказе",
                    image = "1.jpg",
                    price_current = 39000,
                    price_old = null,
                    measure = 240,
                    measure_unit = "г",
                    energy_per_100_grams = 277.2,
                    proteins_per_100_grams = 9.5,
                    fats_per_100_grams = 5.9,
                    carbohydrates_per_100_grams = 46.6,
                    tag_ids = listOf()
                ),
                labels = {}
            )
        }
    }
}