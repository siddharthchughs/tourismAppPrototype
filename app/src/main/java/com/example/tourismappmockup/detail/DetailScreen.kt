package com.example.tourismappmockup.detail

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
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
import androidx.compose.material.icons.outlined.LocationOn
import androidx.compose.material.icons.outlined.Star
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
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
        modifier = Modifier
            .fillMaxSize()
    ) {
        DetailScreenToolbar(navHostController = navHostController)
        DetailScreenStructure()
    }

}

@Composable
fun DetailScreenStructure() {
        Column() {
            PlaceImageStructure()
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
                text = stringResource(R.string.title_detail),
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
        actions = {

        }
    )
}

@Composable
fun PlaceImageStructure() {
    Column(
        modifier = Modifier
            .padding(
                horizontal = 16.dp,
                vertical = 8.dp
            )
            .verticalScroll(rememberScrollState())
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

    LocationDetail()
    Divider(
        color = MaterialTheme.colorScheme.secondary,
        thickness = 1.dp,
        modifier = Modifier
            .padding(horizontal = 16.dp, vertical = 4.dp)
    )
    MultilineText()
    PlaceOffers()

}


@Composable
fun LocationDetail() {
    Column {
        SinglelineBoldText()
        Row(
            modifier = Modifier
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Column(
                horizontalAlignment = Alignment.Start,
                modifier = Modifier
                    .weight(1f, fill = true)
            ) {
                SingleLineText()
            }
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .weight(0.8f)
            ) {
                Image(
                    imageVector = Icons.Outlined.Star,
                    contentDescription = null
                )
            }
        }

    }
}

@Composable
fun SingleLineText(modifier: Modifier = Modifier) {
    Row(
        modifier = modifier
            .wrapContentSize()
            .padding(horizontal = 12.dp, vertical = 12.dp)
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
fun SinglelineBoldText() {
    Column(
        horizontalAlignment = Alignment.Start,
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .padding(start = 12.dp, bottom = 12.dp)
    ) {
        Text(
            modifier = Modifier
                .wrapContentSize()
                .clip(shape = RoundedCornerShape(20.dp))
                .background(MaterialTheme.colorScheme.secondary)
                .padding(start = 12.dp, top = 8.dp, end = 12.dp, bottom = 8.dp),
            text = stringResource(R.string.label_popular),
            style = TextStyle(
                color = MaterialTheme.colorScheme.surface,
                fontSize = MaterialTheme.typography.titleSmall.fontSize,
                fontStyle = androidx.compose.ui.text.font.FontStyle.Normal
            )
        )
        Spacer(
            modifier = Modifier
                .heightIn(min = 12.dp)
        )
        Text(
            modifier = Modifier
                .wrapContentSize(),
            text = "ASLKFASLDKJ",
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
fun MultilineText() {
    Column(
        horizontalAlignment = Alignment.Start,
        verticalArrangement = Arrangement.SpaceAround,
        modifier = Modifier
            .wrapContentWidth()
            .wrapContentHeight()
            .padding(horizontal = 16.dp, vertical = 8.dp)
    ) {
        Text(
            text = stringResource(R.string.place_detail_place_description),
            style = TextStyle(
                color = MaterialTheme.colorScheme.secondary,
                fontSize = MaterialTheme.typography.titleMedium.fontSize,
                fontStyle = androidx.compose.ui.text.font.FontStyle.Normal
            ),
        )
    }
}

@Composable
fun PlaceOffers(modifier: Modifier = Modifier) {
    Column {
        Column(horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceAround,
            modifier = Modifier
                .wrapContentWidth()
        ) {
            Text(
                modifier = modifier
                    .wrapContentSize()
                    .padding(start = 16.dp, top = 8.dp),
                text = stringResource(R.string.place_detail_what_we_offer),
                style = TextStyle(
                    color = MaterialTheme.colorScheme.secondary,
                    fontSize = MaterialTheme.typography.headlineSmall.fontSize,
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
            .heightIn(min=8.dp)
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

@Preview(showBackground = true)
@Composable
fun DefaultPreviewDetail() {
//    DetailScreen(navHostController = {})
}

@Preview(showBackground = true)
@Composable
fun DefaultPreviewMultiLineDetail() {
    MultilineText()
}

@Preview(showBackground = true)
@Composable
fun DefaultPreviewImageDetail() {
    PlaceImageStructure()
}

@Preview(showBackground = true)
@Composable
fun DefaultPreviewPlaceDetail() {
    LocationDetail()
}
