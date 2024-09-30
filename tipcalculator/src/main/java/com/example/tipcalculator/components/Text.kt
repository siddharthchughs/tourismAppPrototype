package com.example.tipcalculator.components

import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.tipcalculator.R

@Composable
fun TotalSplitLabelText(modifier: Modifier = Modifier) {
    Text(
        text = stringResource(R.string.header_label_perperson),
        modifier = modifier.wrapContentWidth().height(IntrinsicSize.Min),
        style = TextStyle(
            color = MaterialTheme.colorScheme.surface,
            fontSize = MaterialTheme.typography.titleMedium.fontSize,
            fontWeight = FontWeight.Bold
        ),
        textAlign = TextAlign.Center
    )
}

@Composable
fun UpdatedAmountPerPersonText(
    totalAmountPerPersom: Double,
    modifier: Modifier = Modifier) {
    val totalPerPerson = "%.2f".format(totalAmountPerPersom)
    Text(
        text = "$${totalPerPerson}",
        modifier = modifier.wrapContentWidth().height(IntrinsicSize.Min),
        style = TextStyle(
            color = MaterialTheme.colorScheme.surface,
            fontSize = MaterialTheme.typography.titleLarge.fontSize,
            fontWeight = FontWeight.Bold
        ),
        textAlign = TextAlign.Center
    )
}

@Composable
fun SplitLabelText(modifier: Modifier = Modifier) {
    Text(
        text = stringResource(R.string.text_split_label),
        modifier = modifier.wrapContentWidth().height(IntrinsicSize.Min),
        style = TextStyle(
            color = MaterialTheme.colorScheme.onBackground,
            fontSize = MaterialTheme.typography.titleLarge.fontSize,
            fontWeight = FontWeight.Medium
        ),
        textAlign = TextAlign.Start
    )
}

@Composable
fun DisplayCountOfPersonText(no_of_split: Int, modifier: Modifier = Modifier) {
    Text(
        text = no_of_split.toString(),
        modifier = modifier.wrapContentWidth().height(IntrinsicSize.Min).padding(2.dp),
        style = TextStyle(
            color = MaterialTheme.colorScheme.primary,
            fontSize = MaterialTheme.typography.titleLarge.fontSize,
            fontWeight = FontWeight.Medium
        ),
        textAlign = TextAlign.Center
    )
}

@Composable
fun TipLabelText(modifier: Modifier = Modifier) {
    Text(
        text = stringResource(R.string.text_tip_label),
        modifier = modifier.wrapContentWidth().height(IntrinsicSize.Min),
        style = TextStyle(
            color = MaterialTheme.colorScheme.onBackground,
            fontSize = MaterialTheme.typography.titleLarge.fontSize,
            fontWeight = FontWeight.Medium
        ),
        textAlign = TextAlign.Start
    )
}

@Composable
fun TotalTipDisplayText(tip:String,modifier: Modifier = Modifier) {
    Text(
        text = tip,
        modifier = modifier
            .padding(start = 8.dp, top = 10.dp,8.dp, bottom = 10.dp)
            .wrapContentWidth().height(IntrinsicSize.Min),
        style = TextStyle(
            color = MaterialTheme.colorScheme.onBackground,
            fontSize = MaterialTheme.typography.titleLarge.fontSize,
            fontWeight = FontWeight.Medium
        ),
        textAlign = TextAlign.Center
    )
}


@Composable
fun TipUpdatePercentageText() {

}

@Composable
fun UpdateSliderPercentageText() {

}



