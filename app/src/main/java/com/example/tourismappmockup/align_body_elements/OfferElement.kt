package com.example.tourismappmockup.align_body_elements

import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.tourismappmockup.R

@Composable
fun OfferElements(
    @DrawableRes drawable: Int,
    text:String,
    modifier: Modifier
){
    Box(
       contentAlignment = Alignment.BottomCenter,
        modifier = modifier
            .widthIn(min = 60.dp)
            .heightIn(min = 60.dp)
            .clip(RoundedCornerShape(size = 10.dp))
        .background(MaterialTheme.colorScheme.secondary)
        ) {
        Image(
            painter = painterResource(drawable),
            contentDescription = null,
            contentScale = ContentScale.FillBounds,
            modifier = Modifier
                .size(88.dp)
                .clip(CircleShape)
        )
        Text(
            text = text,
            modifier = Modifier
                .padding(top = 24.dp, bottom = 8.dp),
            style = TextStyle(
                color = MaterialTheme.colorScheme.surface,
                fontSize = MaterialTheme.typography.bodyMedium.fontSize
            )
        )
    }
}


@Preview(showBackground = true, backgroundColor = 0xffffcc11)
@Composable
fun DefaultPreviewOfferElements(){
    CategoryElements(
        drawable = R.drawable.ic_launcher_foreground,
        text = stringResource(R.string.app_name),
        modifier = Modifier
            .padding(8.dp)
    )
}