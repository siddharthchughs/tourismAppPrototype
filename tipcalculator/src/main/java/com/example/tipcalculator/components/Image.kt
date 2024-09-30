package com.example.tipcalculator.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.tipcalculator.R

@Composable
fun SplittingIntoMorePersonIcon(
    increasePerPerson: () -> Unit,
    modifier: Modifier = Modifier,
) {
    IconButton(
        onClick = { increasePerPerson() }
    ) {
        Image(
            painter = painterResource(R.drawable.add_more),
            contentDescription = null,
            contentScale = ContentScale.Fit,
            modifier = modifier
                .size(48.dp)
                .padding(2.dp)
        )
    }
}

@Composable
fun SplittingIntoLessPersonIcon(
    subtractFromCount: () -> Unit,
    modifier: Modifier = Modifier
) {
    IconButton(
        onClick = {
            subtractFromCount()
        }
    ) {
        Image(
            painter = painterResource(R.drawable.ic_minus),
            contentDescription = null,
            contentScale = ContentScale.Fit,
            modifier = modifier
                .size(48.dp)
                .padding(2.dp)
        )
    }
}

@Preview
@Composable
fun DefautPreviewOfSplitAmoungUsers() {
    Column {
    }
}
