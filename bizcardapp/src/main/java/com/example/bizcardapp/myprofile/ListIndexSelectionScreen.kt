package com.example.bizcardapp.myprofile

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel

@Composable
fun SelectionScreen() {

    val selectionNameViewModel: SelectionNameViewModel = hiltViewModel()

    Column(
        horizontalAlignment = Alignment
            .CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = Modifier
            .fillMaxSize()
    ) {
        SelectionStructure(
            indexCount = selectionNameViewModel.indexCount.value,
            incrementIndexPosition = selectionNameViewModel::incrementIndexPosition,
            listOfname = names
        )
    }
}

@Composable
fun SelectionStructure(
    indexCount: Int,
    incrementIndexPosition: () -> Unit,
    listOfname: List<String>
) {
    val highLightIndex = remember {
        mutableStateOf(indexCount)
    }
    val indexTopUp = remember {
        mutableStateOf(0)
    }

    LazyColumn {
        itemsIndexed(items = listOfname) { index, item ->

                        Text(
                            modifier = Modifier
                                .padding(8.dp)
                                .fillMaxSize(),
                            color = Color.Red,
                            text = "index position name [${index}]  for $item"
                        )
        }
    }
    Button(
        onClick = {
            incrementIndexPosition()
            indexTopUp.value += 1
        }
    ) {
        Text(
            text = "Click"
        )
    }

}

@Composable
fun SelectionListOfName() {

}

@Composable
fun SelectionSingleItemText() {

}