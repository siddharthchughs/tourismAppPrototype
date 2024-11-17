package com.example.movieapp.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextGeometricTransform
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.movieapp.R
import com.example.movieapp.settings.BASE_IMAGE_URL
import com.example.movieapp.settings.IMAGE_NOT_AVAILABLE

@Composable
fun CardBanner(
    backgroundPoster: String?,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .height(350.dp)
            .shadow(
                elevation = 4.dp,
                shape = RectangleShape,
            )
    ) {

        Box(
            contentAlignment = Alignment.Center,
            modifier = modifier
                .fillMaxWidth()
                .fillMaxHeight()
        ) {

            if (backgroundPoster == IMAGE_NOT_AVAILABLE)
                AsyncImage(
                    modifier = Modifier
                        .align(Alignment.TopCenter),
                    model = Image(
                        painterResource(R.drawable.ic_launcher_background),
                        contentDescription = null
                    ),
                    placeholder = painterResource(R.drawable.ic_launcher_background),
                    contentDescription = ""
                )
            else
                AsyncImage(
                    modifier = Modifier
                        .align(Alignment.TopCenter),
                    model = BASE_IMAGE_URL.imageLoad(backgroundPoster.toString()),
                    contentDescription = ""
                )
            val annotatedString = buildAnnotatedString {
                withStyle(
                    style = SpanStyle(
                        color = MaterialTheme.colorScheme.surface,
                        fontSize = 20.sp,
                        fontWeight = FontWeight.ExtraBold
                    )
                ) {
                    append(stringResource(R.string.highlight_text))
                }
                append("  ")
                withStyle(
                    style = SpanStyle(
                        color = MaterialTheme.colorScheme.surface,
                        fontSize = 22.sp,
                    )
                ) {
                    append(stringResource(R.string.sub_part_text))
                }
            }
            Text(
                modifier = Modifier
                    .align(Alignment.BottomCenter),
                text = annotatedString,
                textAlign = TextAlign.Center
            )
        }
    }
}
