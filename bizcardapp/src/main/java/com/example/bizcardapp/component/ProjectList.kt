package com.example.bizcardapp.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.unit.dp
import com.example.bizcardapp.viewmodel.MyProfileUIState

@Composable
fun PortfolioProjectList(list: List<MyProfileUIState.ProjectUIState>) {
    LazyColumn(
        modifier = Modifier.imePadding()
    ) {
        items(items = list, key = {
            it.id
        }) {
            Card(
                modifier = Modifier
                    .padding(4.dp)
                    .fillMaxWidth()
                    .padding(start = 4.dp, end = 4.dp)
                    .height(72.dp),
                elevation = CardDefaults.cardElevation(4.dp),
                shape = RectangleShape
            ) {
                ProjectListItem(projectUIState = it)
            }
        }
    }
}

@Composable
fun ProjectListItem(projectUIState: MyProfileUIState.ProjectUIState) {
    Row(horizontalArrangement = Arrangement.Start) {
        ProfileImage(
            modifier = Modifier
                .size(100.dp)
                .weight(0.4f)
        )
        Spacer(
            modifier = Modifier
                .width(8.dp)
        )
        Divider(
            modifier = Modifier
                .width(1.dp)
                .fillMaxHeight()
                .background(
                    MaterialTheme.colorScheme.primary
                )
        )
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.padding(8.dp)
                .weight(1f)
                .align(Alignment.CenterVertically)
        ) {
            ProjectSubTitleText(projectUIState.name)
        }
    }
}