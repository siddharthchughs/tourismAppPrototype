package com.example.readersapp.component

import androidx.compose.foundation.gestures.ScrollableDefaults
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.readersapp.home.BookModel

@Composable
fun SearchBooks(bookList: List<BookModel>, onPress:(String)-> Unit) {
    LazyColumn(
        modifier = Modifier.fillMaxWidth().padding(8.dp),
        state = rememberLazyListState(),
        contentPadding = PaddingValues(8.dp),
        reverseLayout = false,
        horizontalAlignment = Alignment.CenterHorizontally,
        flingBehavior = ScrollableDefaults.flingBehavior(),
        userScrollEnabled = true
    ) {
        items(items = bookList, key = {
            it.id
        }) { it ->
            CardBookItem(bookModel = it, onPressItem = {
                onPress(it)
            })
        }
    }
}
