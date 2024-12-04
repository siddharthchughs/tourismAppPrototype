package com.example.readersapp.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import com.example.readersapp.R

@Composable
fun SingleTextHeader(label:String,modifier: Modifier = Modifier){
    Surface(
        modifier = modifier
            .padding(start = 16.dp, top = 16.dp)
    ) {
        Column(
            horizontalAlignment = Alignment.Start,
            verticalArrangement = Arrangement.SpaceEvenly,
            modifier = modifier
                .padding(start = 16.dp, top = 8.dp, end = 16.dp)
                .fillMaxWidth()
                .height(48.dp)
        ) {
            Text(modifier = modifier
                .padding(start = 8.dp),
                text = label,
                style = TextStyle(
                    color = Color.Black,
                    fontSize = MaterialTheme.typography.displaySmall.fontSize
                )
            )
        }
    }
}
