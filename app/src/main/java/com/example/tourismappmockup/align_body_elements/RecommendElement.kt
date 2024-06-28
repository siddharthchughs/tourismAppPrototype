package com.example.tourismappmockup.align_body_elements

import androidx.annotation.DrawableRes
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Star
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.tourismappmockup.R
import com.example.tourismappmockup.bottomnavigation.Route

@Composable
fun RecommendElement(
    navHOstController : NavHostController,
    @DrawableRes drawable: Int,
     rating: Float,
     title: String,
     locationName: String,
    modifier: Modifier
) {
    Column(
        horizontalAlignment = Alignment.Start,
        verticalArrangement = Arrangement.SpaceBetween,
        modifier = modifier
            .wrapContentWidth(unbounded = true)
            .heightIn(min = 110.dp)
            .clickable(onClick = {
                navHOstController.navigate(Route.Detail.route)
            }
            )
            .clip(RoundedCornerShape(size = 16.dp))
            .background(MaterialTheme.colorScheme.secondary)
            .alpha(0.8f)

    ) {
        Box(
            modifier = modifier
                .width(180.dp)
                .height(120.dp)
                .clip(RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp))
                .background(MaterialTheme.colorScheme.secondary, shape = RectangleShape)
        ) {
            Icon(painter = painterResource(drawable),contentDescription = null)

            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceEvenly,
                modifier = Modifier
                    .widthIn(min = 60.dp, max = Dp.Unspecified)
                    .heightIn(min = 28.dp)
                    .clip(RoundedCornerShape(size = 16.dp))
                    .background(Color.White, shape = RectangleShape)
            ) {
                Icon(
                    imageVector = Icons.Outlined.Star,
                    contentDescription = stringResource(R.string.rating_label),
                    modifier = Modifier
                        .size(16.dp),
                    tint = MaterialTheme.colorScheme.secondary
                )
                Text(
                    text = rating.toString(),
                    style = TextStyle(
                        color = MaterialTheme.colorScheme.primary,
                        fontSize = MaterialTheme.typography.bodySmall.fontSize
                    )
                )
            }
        }
        Column(
            verticalArrangement = Arrangement.SpaceEvenly,
            modifier = Modifier
                .wrapContentWidth()
                .wrapContentHeight()
                .padding(start = 8.dp, top = 4.dp, bottom = 4.dp)
        ) {
            Text(
                text = title,
                modifier = Modifier
                    .wrapContentSize()
                    .padding(start = 8.dp),
                style = TextStyle(
                    color = MaterialTheme.colorScheme.surface,
                    fontSize = MaterialTheme.typography.bodyMedium.fontSize
                )
            )
            Text(
                text = locationName,
                modifier = Modifier
                    .wrapContentSize()
                    .padding(start = 8.dp),
                style = TextStyle(
                    color = MaterialTheme.colorScheme.surface,
                    fontSize = MaterialTheme.typography.bodySmall.fontSize
                ),
                fontStyle = androidx.compose.ui.text.font.FontStyle.Normal

            )
        }
    }

}

//@Preview(showBackground = true)
//@Composable
//fun DefaultPreviewRecommnedElements() {
//    RecommendElement(
//        drawable = R.drawable.ic_launcher_foreground,
//        rating = R.string.rating_no.toFloat(),
//        title = R.string.app_name.toString(),
//        locationName = R.string.label_location.toString(),
//        modifier = Modifier
//            .padding(8.dp)
//    )
//}
