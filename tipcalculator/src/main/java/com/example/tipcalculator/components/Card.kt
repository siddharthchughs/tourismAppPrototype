package com.example.tipcalculator.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp

@Composable
fun CardDisplayTotalPerPerson(
    totalAmountPerPersom: Double,
    modifier: Modifier = Modifier
) {
    Surface(
        modifier.fillMaxWidth()
            .height(150.dp)
            .padding(4.dp)
            .clip(shape = RoundedCornerShape(corner = CornerSize(12.dp))),
        color = MaterialTheme.colorScheme.primary
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = modifier
                .padding(
                    12.dp
                )
        ) {
            TotalSplitLabelText()
            UpdatedAmountPerPersonText(
                totalAmountPerPersom = totalAmountPerPersom,
            )
        }
    }
}


//@Preview(showBackground = true)
//@Composable
//fun DefaultCardDisplayTotalPerPerson() {
//    CardDisplayTotalPerPerson()
//}