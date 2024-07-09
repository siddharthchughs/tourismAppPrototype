package com.example.myweatherapp.component

import android.widget.Toast
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.myweatherapp.Route


@Composable
fun ForecastSingleItem(
    modifier: Modifier = Modifier,
    id: String,
    label_SeeAll: String,
    navigateToCategorySelected: NavHostController
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
    ) {

        Text(
            text = id,
            modifier = modifier
                .weight(1f, fill = true),
            style = TextStyle(
                color = MaterialTheme.colorScheme.primary,
                fontSize = MaterialTheme.typography.titleMedium.fontSize
            ),
            textAlign = TextAlign.Start
        )

        Text(
            text = label_SeeAll,
            modifier = modifier
                .padding(horizontal = 8.dp)
                .wrapContentWidth()
                .clickable(onClick = {
                    navigateToCategorySelected.navigate(Route.CategorySelect.route+"/$id")
                }
                ),
            textAlign = TextAlign.End,
            style = TextStyle(
                color = MaterialTheme.colorScheme.primary,
                fontSize = MaterialTheme.typography.titleMedium.fontSize
            )
        )
    }

}


@Composable
fun WeatherTypeRowItem(
    modifier: Modifier = Modifier,
    main: String,
    description: String,
    navigateToDetail: NavHostController
) {
    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
            .widthIn(120.dp)
            .wrapContentHeight()
            .border(BorderStroke(1.dp, color = MaterialTheme.colorScheme.primary))
            .clip(RoundedCornerShape(12.dp))
            .clickable(onClick = {
                navigateToDetail.navigate(Route.Detail.route+"/$main"+"/$description")
            })
    ) {

        Text(
            text = main,
            modifier = modifier
                .wrapContentSize()
                .padding(vertical = 12.dp),
            style = TextStyle(
                color = MaterialTheme.colorScheme.primary,
                fontSize = MaterialTheme.typography.titleMedium.fontSize
            )
        )

        Text(
            text = description,
            modifier = modifier
                .wrapContentSize()
                .padding(vertical = 12.dp),
            style = TextStyle(
                color = MaterialTheme.colorScheme.primary,
                fontSize = MaterialTheme.typography.titleMedium.fontSize
            )
        )
    }
}
