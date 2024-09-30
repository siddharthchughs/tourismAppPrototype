//package com.example.tourismappmockup.align_body_elements
//
//import androidx.compose.foundation.BorderStroke
//import androidx.compose.foundation.Image
//import androidx.compose.foundation.border
//import androidx.compose.foundation.layout.Arrangement
//import androidx.compose.foundation.layout.Row
//import androidx.compose.foundation.layout.padding
//import androidx.compose.foundation.shape.RoundedCornerShape
//import androidx.compose.material.icons.Icons
//import androidx.compose.material.icons.filled.Star
//import androidx.compose.material3.MaterialTheme
//import androidx.compose.material3.Text
//import androidx.compose.runtime.Composable
//import androidx.compose.ui.Alignment
//import androidx.compose.ui.Modifier
//import androidx.compose.ui.graphics.Color
//import androidx.compose.ui.text.TextStyle
//import androidx.compose.ui.unit.dp
//
//@Composable
//fun CustomListingElements(modifier: Modifier = Modifier){
//    Row(verticalAlignment = Alignment.CenterVertically,
//        horizontalArrangement = Arrangement.SpaceEvenly,
//        modifier = modifier
//            .border(
//                BorderStroke(2.dp, color = Color.Green),
//                shape = RoundedCornerShape(2.dp)
//            )
//            .weight(1f,fill = true)
//            .padding(horizontal = 12.dp, vertical = 12.dp)
//    ) {
//        Image(
//            modifier = modifier
//                .padding(start = 4.dp),
//            imageVector = Icons.Filled.Star,
//            contentDescription = null
//        )
//        Text(
//            modifier = modifier
//                .padding(start = 8.dp),
//            text = "4.5",
//            style = TextStyle(
//                color = MaterialTheme.colorScheme.secondary,
//                fontSize = MaterialTheme.typography.titleMedium.fontSize
//            )
//        )
//    }
//
//}