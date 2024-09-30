package com.example.bizcardapp.component

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.bizcardapp.R

@Composable
fun ProjectTitleText(id:Int, modifier: Modifier = Modifier) {
    Text(
        modifier = modifier
            .wrapContentWidth()
            .wrapContentHeight()
            .padding(vertical = 4.dp),
        style = TextStyle(
            color = MaterialTheme.colorScheme.primary,
            fontSize = MaterialTheme.typography.titleMedium.fontSize
        ),
        text = id.toString()
    )
}

@Composable
fun ProjectSubTitleText(name:String,modifier: Modifier = Modifier) {
    Text(
        modifier = modifier
            .wrapContentWidth()
            .wrapContentHeight()
            .padding(vertical = 4.dp),
        style = TextStyle(
            color = MaterialTheme.colorScheme.onPrimary,
            fontSize = MaterialTheme.typography.titleSmall.fontSize
        ),
        text = name
    )
}

@Composable
fun SingleHeaderText(modifier: Modifier) {
    Text(
        modifier = modifier
            .wrapContentWidth()
            .wrapContentHeight()
            .padding(vertical = 4.dp),
        style = TextStyle(
            color = MaterialTheme.colorScheme.primary,
            fontSize = MaterialTheme.typography.titleLarge.fontSize,
            textAlign = TextAlign.Center
        ),
        text = stringResource(R.string.profile_name)
    )
}

@Composable
fun SingleSubTitleText(modifier: Modifier) {
    Text(
        modifier = modifier
            .wrapContentWidth()
            .wrapContentHeight(),
        style = TextStyle(
            color = MaterialTheme.colorScheme.surfaceVariant,
            fontSize = MaterialTheme.typography.titleMedium.fontSize,
            textAlign = TextAlign.Center
        ),
        text = stringResource(R.string.profile_email_address)
    )
}
