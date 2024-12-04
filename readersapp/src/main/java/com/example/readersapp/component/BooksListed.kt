package com.example.readersapp.component

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import coil.compose.rememberImagePainter
import com.example.readersapp.R
import com.example.readersapp.home.BookModel

@Composable
fun BookListedItem(
    bookModel: BookModel,
    onPressItem: (String) -> Unit
) {
    val context = LocalContext.current
    val resources = context.resources
    val displayMetrics = resources.displayMetrics
    val screenWidth = displayMetrics.widthPixels / displayMetrics.density
    val spacing = 10.dp
    Card(
        modifier = Modifier
            .padding(start = 16.dp)
            .width(200.dp)
            .height(240.dp)
            .clickable { onPressItem.invoke(bookModel.book_name) },

        elevation = CardDefaults.elevatedCardElevation(4.dp),
        shape = RoundedCornerShape(28.dp),
    ) {
        Column(
            horizontalAlignment = Alignment.Start,
            modifier = Modifier.width(
                screenWidth.dp - (spacing * 2)
            )
                .background(Color.White)
        ) {
            Row(
                horizontalArrangement = Arrangement.SpaceEvenly,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Image(
                    modifier = Modifier
                        .padding(4.dp)
                        .width(140.dp)
                        .height(120.dp),
                    painter = rememberImagePainter(data = R.drawable.ic_launcher_background),
                    contentDescription = null
                )
                Spacer(
                    modifier = Modifier.width(12.dp)
                )
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Image(
                        imageVector = Icons.Default.Star,
                        contentDescription = null
                    )
                    BookListedRating(ratingScore = 2.3)
                }
            }
            Spacer(
                modifier = Modifier
                    .height(12.dp)
            )
            BookListedInfo(bookModel = bookModel)
            Row(
                horizontalArrangement = Arrangement.End,
                modifier = Modifier
                    .padding(end = 4.dp)
                    .fillMaxWidth()
            ) {
                BookListedActivity(
                    label = "Not Started",
                    onPress = {}
                )
            }
        }
    }
}

@Composable
fun BookListedRating(ratingScore: Double) {
    Surface(
        modifier = Modifier
            .padding(4.dp)
            .heightIn(min = 70.dp),
        shape = RoundedCornerShape(24.dp),
        tonalElevation = 4.dp,
        color = Color.Gray
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier
                .padding(4.dp)
        ) {
            Image(
                imageVector = Icons.Default.Star,
                contentDescription = null
            )
            Text(
                text = ratingScore.toString(),
                style = TextStyle(
                    color = Color.Black
                )
            )
        }
    }
}

@Composable
fun BookListedInfo(bookModel: BookModel) {
    Column {
        Text(
            modifier = Modifier
                .padding(start = 16.dp, top = 4.dp),
            text = bookModel.book_name,
            style = TextStyle(
                color = Color.Gray,
                fontSize = MaterialTheme.typography.titleLarge.fontSize
            )
        )
        Text(
            modifier = Modifier
                .padding(start = 16.dp, top = 4.dp),
            text = bookModel.book_Author,
            style = TextStyle(
                color = Color.Gray,
                fontSize = MaterialTheme.typography.titleSmall.fontSize
            )
        )
    }
}

@Composable
fun BookListedActivity(
    label: String,
    onPress: () -> Unit
) {
    Surface(
        modifier = Modifier
            .padding(bottom = 2.dp, end = 2.dp)
            .clip(RoundedCornerShape(topStart = 20.dp, bottomEnd = 20.dp)),
        border = BorderStroke(1.dp, color = Color.Yellow)
    ) {
        Column(
            modifier = Modifier
                .width(88.dp)
                .heightIn(60.dp)
                .background(Color.LightGray)
                .clickable { onPress.invoke() },
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = label,
                style = TextStyle(
                    color = Color.Gray,
                    fontSize = MaterialTheme.typography.titleMedium.fontSize
                ),
                textAlign = TextAlign.Center
            )
        }
    }
}
