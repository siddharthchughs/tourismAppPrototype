package com.example.tourismappmockup.align_body_elements

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.ArrowForward
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun NextTripElement(
    title: String,
    modifier: Modifier = Modifier
) {
    Spacer(
        modifier = modifier
            .widthIn(min = 24.dp)
            .background(MaterialTheme.colorScheme.inversePrimary)
    )


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
                    text = title,
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
    Spacer(
        modifier = modifier
            .widthIn(min = 24.dp)
            .background(MaterialTheme.colorScheme.inversePrimary)
    )

}

@Preview(showBackground = true)
@Composable
fun DefaultPreviewNextTripElement() {
    NextTripElement(
        title = "",
        modifier = Modifier
            .padding(8.dp)
    )
}
