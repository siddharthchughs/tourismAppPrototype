package com.example.tourismappmockup.align_body_elements

import androidx.annotation.DrawableRes
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.tourismappmockup.R

@Composable
fun PopularElement(
    @DrawableRes drawable: Int,
     title: String,
    modifier: Modifier
) {
    Column(
        horizontalAlignment = Alignment.Start,
        verticalArrangement = Arrangement.SpaceBetween,
        modifier = modifier
            .widthIn(min = 200.dp)
            .heightIn(min = 110.dp)
            .clip(RoundedCornerShape(size = 12.dp))
    ) {
        Box(
            contentAlignment = Alignment.BottomStart,
            modifier = modifier
                .widthIn(min = 200.dp)
                .heightIn(min = 110.dp)
                .clip(RoundedCornerShape(size = 12.dp))
                .background(MaterialTheme.colorScheme.secondary, shape = RectangleShape)
        ) {
            Icon(painter = painterResource(drawable),contentDescription = null)
                Text(
                    text = title,
                    modifier = Modifier
                        .wrapContentSize()
                        .padding(start = 8.dp, bottom = 8.dp),
                    style = TextStyle(
                        color = MaterialTheme.colorScheme.surface,
                        fontSize = MaterialTheme.typography.bodyMedium.fontSize
                    )
                )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreviewPopularElements() {
    PopularElement(
        drawable = R.drawable.ic_launcher_foreground,
        title = stringResource(R.string.app_name),
        modifier = Modifier
            .padding(8.dp)
    )
}
