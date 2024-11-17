package com.example.movieapp.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.movieapp.R

@Composable
fun MovieTitleLabel(movieTitle: String?) {
    Column(
        verticalArrangement = Arrangement.spacedBy(32.dp),
        modifier = Modifier
            .padding(vertical = 28.dp)
    ) {
        Text(
            text = "${movieTitle}",
            textAlign = TextAlign.Center,
            style = TextStyle(
                fontSize = 22.sp,
                color = MaterialTheme.colorScheme.primary
            )
        )
    }
}

@Composable
fun CustomLabels(vote_count: String, duration: String, releaseStatus: String) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceAround,
        modifier = Modifier
            .padding(horizontal = 16.dp)
            .fillMaxWidth()
            .height(52.dp)
            .background(Color.White)
    ) {

        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .weight(0.4f)
                .wrapContentWidth(Alignment.CenterHorizontally)
                .clip(shape = RoundedCornerShape(16.dp))
,
            ) {

            Text(
                modifier = Modifier
                    .padding( start = 8.dp)
                    .wrapContentWidth(Alignment.CenterHorizontally),
                text = stringResource(R.string.label_genre),
                textAlign = TextAlign.Center,
                style = TextStyle(
                    color = Color.Gray,
                    fontSize = 16.sp
                )
            )
            Spacer(
                modifier = Modifier
                    .width(12.dp)
            )

            Text(
                modifier = Modifier
                    .padding( end = 8.dp)
                    .wrapContentWidth(Alignment.CenterHorizontally),
                text = vote_count,
                textAlign = TextAlign.Center,
                style = TextStyle(
                    fontSize = 16.sp
                )
            )
        }

        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .weight(0.2f)
                .wrapContentWidth(Alignment.CenterHorizontally)
                .clip(shape = RoundedCornerShape(16.dp)),
        ) {
            Image(
                modifier = Modifier
                    .padding( start = 4.dp),
                painter = painterResource(R.drawable.ic_time),
                contentDescription = ""
            )
            Spacer(
                modifier = Modifier
                    .width(12.dp)
            )

            Text(
                modifier = Modifier
                    .padding( end = 4.dp)
                    .wrapContentWidth(Alignment.CenterHorizontally),
                text = vote_count,
                textAlign = TextAlign.Center,
                style = TextStyle(
                    color = Color.Gray,
                    fontSize = 16.sp
                )
            )
        }

        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .weight(0.4f)
                .wrapContentWidth(Alignment.CenterHorizontally)
                .clip(shape = RoundedCornerShape(16.dp)),
            ) {

            Image(
                modifier = Modifier
                    .padding( start = 8.dp),
                painter = painterResource(R.drawable.ic_thumb_up),
                contentDescription = ""
            )
            Spacer(
                modifier = Modifier
                    .width(12.dp)
            )

            Text(
                modifier = Modifier
                    .padding( end = 8.dp)
                    .wrapContentWidth(Alignment.CenterHorizontally),
                text = vote_count,
                textAlign = TextAlign.Center,
                style = TextStyle(
                    color = Color.Gray,
                    fontSize = 16.sp
                )
            )

        }
    }
}

