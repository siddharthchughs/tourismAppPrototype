package com.example.movieapp

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp

@Composable
fun MovieDescriptionText(movieDescription:String){
    Text(
        text = movieDescription,
        textAlign = TextAlign.Start,
        style = TextStyle(
            color = Color.Blue,
            fontSize = 14.sp,
            fontWeight = FontWeight.Medium
        )
    )
}