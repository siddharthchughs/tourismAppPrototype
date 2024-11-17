package com.example.movieapp.component

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.movieapp.R

@Composable
fun MovieImage(modifier: Modifier = Modifier){
    val modifier = modifier
        .size(80.dp)
        .clip(shape = RoundedCornerShape(corner = CornerSize(12.dp)))
        .border(border = BorderStroke(width = 2.dp, color = MaterialTheme.colorScheme.primary))
        Icon(
        painter = painterResource(R.drawable.ic_launcher_background),
        contentDescription = "",
        modifier = modifier
    )
}