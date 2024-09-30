package com.example.myweatherapp.categoryselect

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.outlined.Place
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
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
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.myweatherapp.R
import com.example.myweatherapp.component.PaymentOperationSuccess

@Composable
fun CategorySelectScreen(
    id: String?,
    navHostController: NavHostController
) {
    Column(
        modifier = Modifier
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top
    ) {
        CategorySelectToolbar(
            id = id,
            navHostController = navHostController
        )
        CategorySelectStructure()
    }
}

@Composable
fun CategorySelectStructure(modifier: Modifier = Modifier) {

    var isExpanded by remember { mutableStateOf(false) }
    Column (modifier = Modifier
        .widthIn(max = 400.dp)
        .wrapContentHeight(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "Hello I am Detail page"
        )
            Row(
                horizontalArrangement = Arrangement.Start,
                verticalAlignment = Alignment.CenterVertically,
                modifier = modifier
                    .padding(horizontal = 16.dp, vertical = 16.dp)
                    .wrapContentWidth()
                    .height(intrinsicSize = IntrinsicSize.Min)
                    .clip(RoundedCornerShape(4.dp))
                    .background(MaterialTheme.colorScheme.surface)
                    .border(BorderStroke(1.dp, color = MaterialTheme.colorScheme.primary))

            ) {

                Image(
                    imageVector = Icons.Filled.CheckCircle,
                    contentDescription = "",
                    contentScale = ContentScale.Fit,
                    colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.secondary),
                    modifier = modifier
                        .wrapContentSize()
                        .aspectRatio(8f / 16f)
                        .padding(start = 4.dp)
                )

                Spacer(
                    modifier = modifier
                        .widthIn(min = 16.dp)
                )

                Column(
                    verticalArrangement = Arrangement.SpaceEvenly,
                    horizontalAlignment = Alignment.Start,
                    modifier = modifier
                        .weight(0.4f)
                        .wrapContentHeight()
                        .background(MaterialTheme.colorScheme.surface)
                ) {
                    Text(
                        text = "Rs.34343 paid",
                        style = TextStyle(
                            fontSize = MaterialTheme.typography.titleMedium.fontSize,
                            color = MaterialTheme.colorScheme.primary
                        )
                    )
                    Text(
                        text = "23 July' 2024",
                        modifier = modifier
                            .padding(vertical = 4.dp),
                        style = TextStyle(
                            fontSize = MaterialTheme.typography.titleSmall.fontSize,
                            color = MaterialTheme.colorScheme.primary
                        )

                    )
                    Text(
                        text = " 8: 11 AM",
                        modifier = modifier
                            .padding(vertical = 2.dp),
                        style = TextStyle(
                            fontSize = MaterialTheme.typography.titleSmall.fontSize,
                            color = MaterialTheme.colorScheme.primary
                        )
                    )
                }



                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween,
                    modifier = modifier
                        .weight(0.2f, false)
                        .heightIn(min = 80.dp)
                        .clickable { }
                ) {
                    Icon(
                        imageVector = Icons.Outlined.Place,
                        contentDescription = "",
                        tint = MaterialTheme.colorScheme.secondary,
                        modifier = modifier
                            .weight(1f)
                            .size(48.dp)
                            .padding(vertical = 4.dp)
                            .clip(RoundedCornerShape(8.dp))
                            .border(
                                BorderStroke(1.dp, color = MaterialTheme.colorScheme.primary),
                                shape = RectangleShape
                            )
                    )

                    Image(
                        painter = if(isExpanded)
                            painterResource(R.drawable.ic_expand)
                        else
                            painterResource(R.drawable.ic_expand_less)
                        ,
                        contentDescription = "",
                        colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.secondary),
                        modifier = modifier
                            .weight(1f)
                            .clickable {
                                isExpanded = !isExpanded
                            }
                    )
                }
            }
        PaymentOperationSuccess(isExpanded = isExpanded)
    }
}




@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CategorySelectToolbar(
    id: String?,
    navHostController: NavHostController,
) {
    var isDisplayed by remember { mutableStateOf(false) }
    TopAppBar(
        modifier = Modifier
            .background(MaterialTheme.colorScheme.primary),
        title = {
            Text(text = id.toString())
        },
        navigationIcon = {
            IconButton(onClick = { navHostController.navigateUp() }) {
                Image(
                    imageVector = Icons.Default.ArrowBack,
                    contentDescription = "Back",
                    colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.primary)
                )
            }
        },
        actions = {
            IconButton(onClick = {
                isDisplayed = !isDisplayed
            }
            ) {
                Image(
                    imageVector = Icons.Default.MoreVert,
                    contentDescription = stringResource(R.string.menu_more_vert),
                    colorFilter = ColorFilter.tint(
                        color = MaterialTheme.colorScheme.primary,
                        blendMode = BlendMode.DstOut
                    )
                )
            }
            DropdownMenu(
                expanded = isDisplayed,
                onDismissRequest = {
                    isDisplayed = !isDisplayed
                }
            ) {
                DropdownMenuItem(
                    text = {
                        Text(
                            text = stringResource(R.string.settings)
                        )
                    },
                    onClick = {

                    }
                )
            }
        }
    )
}


@Composable
fun WeatherOverview() {
    Column {

    }
}

