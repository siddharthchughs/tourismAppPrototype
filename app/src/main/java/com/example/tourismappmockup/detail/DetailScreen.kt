package com.example.tourismappmockup.detail

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material.icons.outlined.LocationOn
import androidx.compose.material.icons.outlined.Share
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.input.nestedscroll.NestedScrollConnection
import androidx.compose.ui.input.nestedscroll.NestedScrollSource
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.Velocity
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.tourismappmockup.R
import com.example.tourismappmockup.align_body_elements.OfferElements
import com.example.tourismappmockup.home.listOfOffersByPlace

@Composable
fun DetailScreen(
    navHostController: NavHostController
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top,
        modifier = Modifier
            .fillMaxSize()
    ) {
        DetailScreenToolbar(navHostController = navHostController)
        DetailScreenStructure()
    }
}

@Composable
fun DetailScreenStructure() {
    val outerScrollState = rememberScrollState()
    val nestedScrollConnection = object : NestedScrollConnection {
        override suspend fun onPostFling(consumed: Velocity, available: Velocity): Velocity {
            return Velocity.Zero
        }

        override fun onPostScroll(
            consumed: Offset,
            available: Offset,
            source: NestedScrollSource
        ): Offset {
            println("Received onPost Scroll")
            return Offset.Zero

        }

        override suspend fun onPreFling(available: Velocity): Velocity {
            return Velocity.Zero
        }

        override fun onPreScroll(available: Offset, source: NestedScrollSource): Offset {
            println("Received onPress Scroll")
            return Offset.Zero
        }
    }

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(outerScrollState)
            .nestedScroll(nestedScrollConnection)
            .background(MaterialTheme.colorScheme.surface)
    ) {
        PlaceImageSlider()
        PlaceInfoStructure()
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailScreenToolbar(
    navHostController: NavHostController
) {
    TopAppBar(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight(),
        title = {
            Text(
                modifier = Modifier
                    .wrapContentWidth()
                    .wrapContentHeight()
                    .padding(start = 16.dp),
                text = "ABc Place",
                style = TextStyle(
                    color = MaterialTheme.colorScheme.primary,
                    fontSize = MaterialTheme.typography.titleLarge.fontSize
                )
            )
        },
        navigationIcon = {
            IconButton(onClick = { navHostController.navigateUp() }) {
                Image(
                    modifier = Modifier
                        .padding(start = 12.dp),
                    imageVector = Icons.Default.ArrowBack,
                    colorFilter = ColorFilter.tint(color = MaterialTheme.colorScheme.primary),
                    contentDescription = null
                )
            }
        },
        actions = {}
    )
}

@Composable
fun PlaceImageSlider() {
    Column(
        modifier = Modifier
            .padding(
                horizontal = 12.dp
            )
    ) {
        Image(
            modifier = Modifier
                .fillMaxWidth()
                .height(200.dp),
            painter = painterResource(R.drawable.ic_launcher_background),
            contentDescription = "",
            contentScale = ContentScale.FillWidth
        )
    }
}


@Composable
fun PlaceInfoStructure() {
    Column(
        horizontalAlignment = Alignment.Start,
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 12.dp, vertical = 16.dp)
    ) {
        CategorySelectionText()
        PlaceOverview()
    }
}

@Composable
fun PlaceOverview() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight(),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Column(
            horizontalAlignment = Alignment.Start,
            modifier = Modifier
                .weight(1f, fill = true)
        ) {
            SingleLineText()
        }
    }
    PlaceInfo()
    Divider(
        color = MaterialTheme.colorScheme.secondary,
        thickness = 1.dp,
        modifier = Modifier
            .padding(horizontal = 12.dp, vertical = 4.dp)
    )
    MultilineText()
    PlaceOffers()
}

@Composable
fun SingleLineText(modifier: Modifier = Modifier) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = 12.dp)
    ) {
        Image(
            modifier = modifier
                .padding(start = 4.dp),
            imageVector = Icons.Outlined.LocationOn,
            contentDescription = null
        )
        Text(
            modifier = modifier
                .padding(start = 8.dp),
            text = "Europe,Norway",
            style = TextStyle(
                color = MaterialTheme.colorScheme.secondary,
                fontSize = MaterialTheme.typography.titleMedium.fontSize
            )
        )
    }
}

@Composable
fun CategorySelectionText(modifier: Modifier = Modifier) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceEvenly,
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .padding(horizontal = 4.dp, vertical = 4.dp)
    ) {
        Row(
            horizontalArrangement = Arrangement.Start,
            modifier = modifier
                .weight(1f, fill = true)
        ) {
            Text(
                text = stringResource(R.string.label_popular),
                modifier = Modifier
                    .weight(1f, fill = false)
                    .border(
                        BorderStroke(2.dp, MaterialTheme.colorScheme.secondary),
                        shape = RoundedCornerShape(8.dp)
                    )
                    .clip(shape = RoundedCornerShape(8.dp))
                    .padding(start = 8.dp, top = 8.dp, end = 12.dp, bottom = 8.dp),
                style = TextStyle(
                    color = MaterialTheme.colorScheme.primary,
                    fontSize = MaterialTheme.typography.titleSmall.fontSize,
                    fontStyle = androidx.compose.ui.text.font.FontStyle.Normal
                )
            )
        }
        Row(
            verticalAlignment = Alignment.Top,
            horizontalArrangement = Arrangement.End,
            modifier = modifier
                .weight(1f)

        ) {
            Image(
                modifier = modifier
                    .weight(1f, fill = false),
                imageVector = Icons.Outlined.FavoriteBorder,
                contentDescription = null
            )

        }
        Spacer(
            modifier = Modifier
                .heightIn(min = 4.dp)
        )
    }
}

@Composable
fun SinglelineBoldText() {
    Column(
        horizontalAlignment = Alignment.Start,
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .padding(start = 12.dp, bottom = 12.dp)
    ) {
        Spacer(
            modifier = Modifier
                .heightIn(min = 12.dp)
        )

        Text(
            modifier = Modifier
                .wrapContentSize(),
            text = "ABc Place",
            style = TextStyle(
                color = MaterialTheme.colorScheme.secondary,
                fontSize = MaterialTheme.typography.displaySmall.fontSize,
                fontStyle = androidx.compose.ui.text.font.FontStyle.Normal
            ),
            maxLines = 2
        )
    }
}

@Composable
fun PlaceInfo(modifier: Modifier = Modifier) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceEvenly,
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .padding(horizontal = 8.dp, vertical = 8.dp)
    ) {

        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceEvenly,
            modifier = modifier
                .weight(1f, fill = true)
                .padding(horizontal = 12.dp, vertical = 12.dp)
        ) {
            Image(
                modifier = modifier
                    .padding(start = 4.dp),
                imageVector = Icons.Filled.Star,
                contentDescription = null
            )
            Text(
                text = "4.5",
                style = TextStyle(
                    color = MaterialTheme.colorScheme.secondary,
                    fontSize = MaterialTheme.typography.titleMedium.fontSize
                )
            )
        }
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceEvenly,
            modifier = modifier
                .weight(1f, fill = true)
                .padding(horizontal = 12.dp, vertical = 12.dp)
        ) {
            Image(
                modifier = modifier
                    .padding(start = 4.dp),
                imageVector = Icons.Outlined.LocationOn,
                contentDescription = null
            )
            Text(
                text = "10 km",
                style = TextStyle(
                    color = MaterialTheme.colorScheme.secondary,
                    fontSize = MaterialTheme.typography.titleMedium.fontSize
                )
            )
        }
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceAround,
            modifier = modifier
                .weight(0.5f)
                .padding(horizontal = 4.dp, vertical = 12.dp)
        ) {
            Image(
                alignment = Alignment.Center,
                imageVector = Icons.Outlined.Share,
                contentDescription = null,
                contentScale = ContentScale.Fit
            )
        }
    }
}

@Composable
fun MultilineText() {
    Column(
        horizontalAlignment = Alignment.Start,
        verticalArrangement = Arrangement.SpaceAround,
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .padding(horizontal = 16.dp, vertical = 8.dp)
    ) {
        var isVisible by remember { mutableStateOf(false) }
        val minimumLines by remember {
            mutableStateOf(3)
        }
        Text(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight(),
            text = stringResource(R.string.detail_description),
            style = TextStyle(
                fontSize = MaterialTheme.typography.titleMedium.fontSize
            ),
            maxLines = if (isVisible) Int.MAX_VALUE else minimumLines,
            overflow = TextOverflow.Ellipsis
        )
        Text(
            text = if (isVisible) stringResource(R.string.label_read_less) else stringResource(R.string.label_read_more),
            modifier = Modifier
                .clickable(onClick = {
                    isVisible = !isVisible
                }
            ),
            style = TextStyle(
                color = MaterialTheme.colorScheme.primary,
                fontSize = MaterialTheme.typography.titleSmall.fontSize
            )
        )
    }
}

@Composable
fun PlaceOffers(modifier: Modifier = Modifier) {
    Column {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceAround,
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
        ) {
            Text(
                modifier = modifier
                    .wrapContentSize()
                    .padding(start = 16.dp, top = 8.dp),
                text = stringResource(R.string.place_detail_what_we_offer),
                style = TextStyle(
                    color = MaterialTheme.colorScheme.secondary,
                    fontSize = MaterialTheme.typography.titleMedium.fontSize,
                    fontStyle = androidx.compose.ui.text.font.FontStyle.Normal
                ),
            )
            Divider(
                color = MaterialTheme.colorScheme.secondary,
                thickness = 1.dp,
                modifier = Modifier
                    .padding(horizontal = 16.dp)
            )
        }

        Spacer(
            modifier = modifier
                .heightIn(min = 8.dp)
        )

        LazyRow(
            modifier = modifier
                .padding(horizontal = 12.dp)
        ) {
            items(items = listOfOffersByPlace) { categories ->
                OfferElements(
                    text = categories,
                    modifier = modifier
                )
                Spacer(
                    modifier = modifier
                        .widthIn(min = 4.dp)
                )
            }
        }

    }

}

