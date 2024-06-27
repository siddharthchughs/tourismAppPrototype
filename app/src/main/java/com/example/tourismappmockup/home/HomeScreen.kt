package com.example.tourismappmockup.home

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.outlined.ArrowForward
import androidx.compose.material.icons.outlined.Notifications
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.tourismappmockup.R
import com.example.tourismappmockup.align_body_elements.CategoryElements
import com.example.tourismappmockup.align_body_elements.PopularElement
import com.example.tourismappmockup.align_body_elements.RecommendElement

@Composable
fun HomeScreen(navHostController: NavHostController) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top,
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.surface)
    ) {
        HomeScreenToolbar()
        HomeScreenStructure(navHostController = navHostController)
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreenToolbar() {
    TopAppBar(
        modifier = Modifier
            .fillMaxWidth()
            .heightIn(56.dp),
        title = {
            Text(
                text = stringResource(R.string.title_home)
            )
        },
        navigationIcon = {},
        actions = {
            IconButton(onClick = {}) {
                Icon(
                    imageVector = Icons.Default.Search,
                    contentDescription = ""
                )
            }
            IconButton(onClick = {}) {
                Icon(
                    imageVector = Icons.Outlined.Notifications,
                    contentDescription = ""
                )
            }
        }
    )
}


@Composable
fun HomeScreenStructure(
    modifier: Modifier = Modifier,
    navHostController: NavHostController) {
    Column(
        modifier = modifier
                .background(MaterialTheme.colorScheme.surface)

    ) {
        AlignedBodyStructure()
        PopularStructure()
        RecommendStructure(navHostController = navHostController )
    }
}

@Composable
fun HeaderTitle(modifier: Modifier = Modifier) {
    Column {
        Text(
            text = stringResource(R.string.title_explore),
            modifier = modifier
                .fillMaxWidth()
                .wrapContentHeight()
                .padding(start = 16.dp),
            style = TextStyle(
                color = MaterialTheme.colorScheme.primary,
                fontSize = MaterialTheme.typography.titleLarge.fontSize
            )
        )
    }

}

@Composable
fun AlignedBodyStructure() {
    Column(verticalArrangement = Arrangement.SpaceEvenly)
    {
        CategoryHeaderLabel()
        Spacer(
            modifier = Modifier
                .heightIn(min = 16.dp)
        )
        CategoryElementList()
    }
}

@Composable
fun CategoryHeaderLabel(modifier: Modifier = Modifier) {
        Text(
            text = stringResource(R.string.label_category),
            modifier = modifier
                .fillMaxWidth()
                .wrapContentHeight()
                .padding(start = 16.dp),
            style = TextStyle(
                color = MaterialTheme.colorScheme.onBackground,
                fontSize = MaterialTheme.typography.titleMedium.fontSize
            )
        )
}

@Composable
fun CategoryElementList(modifier: Modifier = Modifier) {
    LazyRow(
        userScrollEnabled = true,
        modifier = modifier
            .padding(horizontal = 16.dp)
    ) {
        items(items = listOfCategories) { categories ->
            CategoryElements(
                drawable = R.drawable.ic_launcher_foreground,
                text = categories,
                modifier = modifier
            )
            Spacer(
                modifier = modifier
                    .widthIn(min = 12.dp)
            )
        }
    }
}

@Composable
fun PopularStructure() {
    Column(horizontalAlignment = Alignment.CenterHorizontally)
    {
        PopularHeaderLabel()
        Spacer(
            modifier = Modifier
                .heightIn(min = 16.dp)
        )
        PopularElementList()
    }
}

@Composable
fun PopularHeaderLabel(modifier: Modifier = Modifier) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .heightIn(48.dp)
            .padding(top = 40.dp)
    ) {

        Text(
            text = stringResource(R.string.label_popular),
            modifier = modifier
                .weight(1f)
                .wrapContentHeight()
                .padding(start = 16.dp),
            style = TextStyle(
                color = MaterialTheme.colorScheme.onBackground,
                fontSize = MaterialTheme.typography.titleMedium.fontSize
            ),
            textAlign = TextAlign.Start

        )

        Text(
            text = stringResource(R.string.label_see_all),
            modifier = modifier
                .weight(1f)
                .wrapContentHeight()
                .padding(end = 16.dp)
                .clickable(
                    onClick = {}
                ),
            style = TextStyle(
                color = MaterialTheme.colorScheme.tertiary,
                fontSize = MaterialTheme.typography.titleMedium.fontSize
            ),
            textAlign = TextAlign.End
        )
    }
}

@Composable
fun PopularElementList(modifier: Modifier = Modifier) {
    LazyRow(
        userScrollEnabled = true,
        modifier = modifier
            .padding(horizontal = 16.dp)
    ) {
        items(items = listOfCategories) { categories ->
            PopularElement(
                drawable = R.drawable.ic_launcher_foreground,
                title = categories,
                modifier = modifier
            )
            Spacer(
                modifier = modifier
                    .widthIn(min = 12.dp)
            )
        }
    }
}

@Composable
fun RecommendStructure(navHostController: NavHostController) {
    Column(horizontalAlignment = Alignment.CenterHorizontally)
    {
        RecommendHeaderLabel()
        Spacer(
            modifier = Modifier
                .heightIn(min = 16.dp)
        )
        RecommendElementList(navHostController = navHostController)
    }
}

@Composable
fun RecommendHeaderLabel(modifier: Modifier = Modifier) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .heightIn(48.dp)
            .padding(top = 40.dp)
    ) {

        Text(
            text = stringResource(R.string.label_recommend),
            modifier = modifier
                .weight(1f)
                .wrapContentHeight()
                .padding(start = 16.dp),
            style = TextStyle(
                color = MaterialTheme.colorScheme.onBackground,
                fontSize = MaterialTheme.typography.titleMedium.fontSize
            ),
            textAlign = TextAlign.Start

        )

        Text(
            text = stringResource(R.string.label_see_all),
            modifier = modifier
                .weight(1f)
                .wrapContentHeight()
                .padding(end = 16.dp)
                .clickable(
                    onClick = {}
                ),
            style = TextStyle(
                color = MaterialTheme.colorScheme.tertiary,
                fontSize = MaterialTheme.typography.titleMedium.fontSize
            ),
            textAlign = TextAlign.End
        )
    }
}

@Composable
fun RecommendElementList(
    modifier: Modifier = Modifier,
    navHostController: NavHostController
    ) {
    LazyRow(
        userScrollEnabled = true,
        modifier = modifier
            .padding(horizontal = 16.dp)
    ) {
        items(items = listOfRecommendation) { itemRecommend ->
            RecommendElement(
                drawable = R.drawable.ic_launcher_foreground,
                rating = 4.5.toFloat(),
                title = itemRecommend,
                locationName = "Norway",
                modifier = modifier,
                navHOstController = navHostController
            )
            Spacer(
                modifier = modifier
                    .widthIn(min = 12.dp)
            )
        }
    }
}

@Composable
fun NextTripStructure(modifier: Modifier = Modifier) {

    Spacer(
        modifier = modifier
            .heightIn(min = 40.dp)
    )

    Button(onClick = {

    }){
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceEvenly,
            modifier = modifier
                .fillMaxWidth()
                .heightIn(min = 56.dp)
                .clip(RoundedCornerShape(size = 12.dp))
                .background(MaterialTheme.colorScheme.primary)
        ) {
            Text(
                text = stringResource(R.string.label_next_trip),
                modifier = Modifier
                    .wrapContentSize()
                    .padding(start = 8.dp),
                style = TextStyle(
                    color = Color.LightGray,
                    fontSize = MaterialTheme.typography.bodyMedium.fontSize
                )
            )
            Icon(modifier = modifier
                , imageVector = Icons.Outlined.ArrowForward,
                contentDescription = null)
        }
    }
}


@Preview(showBackground = true)
@Composable
fun DefaultPreviewHomeScreen() {
}


