package com.example.readersapp.component

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.FloatingActionButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.readersapp.navigation.ReaderRoute

@Composable
fun FabContent(
    navController: NavController,
    onTap: (String) -> Unit
) {
    FloatingActionButton(
        onClick = {
            navController.navigate(ReaderRoute.SearchScreen.name)
            onTap("")
        },
        shape = RoundedCornerShape(48.dp),
        containerColor = FloatingActionButtonDefaults.containerColor,
        contentColor = Color.Magenta
    ) {
        Icon(
            Icons.Default.Add,
            contentDescription = "Add data"
        )
    }
}
