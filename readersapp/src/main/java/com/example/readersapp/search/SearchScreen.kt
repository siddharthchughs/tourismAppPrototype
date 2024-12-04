package com.example.readersapp.search

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.example.readersapp.R
import com.example.readersapp.component.BookItem
import com.example.readersapp.component.TextInputField

@Composable
fun SearchScreen(
    navController: NavController
) {
    val searchViewModel: SearchViewModel = hiltViewModel()
    val searchBooksUIState =
        searchViewModel.searchBookUIState.collectAsStateWithLifecycle(SearchBookUI.Loading).value
    Scaffold(
        topBar = {
            SearchTopAppBar(
                title = stringResource(R.string.title_search_screen),
                onBackPress = { navController.navigateUp() }
            )
        },
        containerColor = Color.LightGray
    ) { initialPadding ->
        Surface(
            tonalElevation = 3.dp,
            modifier = Modifier
                .padding(initialPadding)
                .fillMaxSize()
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                SearchScreenStructure(
                    searchBookUI = searchBooksUIState,
                    searchBookInput = searchViewModel.searchBook.value,
                    onSearchUpdateChange = searchViewModel::onSearchUpdateChange,
                    search = searchViewModel::search
                )
            }
        }
    }
}

@Composable
fun SearchScreenStructure(
    searchBookUI: SearchBookUI,
    searchBookInput: String,
    onSearchUpdateChange: (String) -> Unit,
    search: () -> Unit
) {
    Column(verticalArrangement = Arrangement.Center) {

    }
    when (searchBookUI) {
        SearchBookUI.Idle -> {
            SearchScreenUserInputField(
                searchBookInput = searchBookInput,
                onSearchUpdateChange = onSearchUpdateChange,
                search = search
            )
        }

        SearchBookUI.Loading -> {
//            Column(modifier = Modifier
//                .fillMaxSize(),
//                verticalArrangement = Arrangement.Center) {
//                SearchScreenUserInputField(
//                    searchBookInput = searchBookInput,
//                    onSearchUpdateChange = onSearchUpdateChange,
//                    search = search
//                )

                Progressbar()

//            }
        }

        is SearchBookUI.BooksLoaded -> {

            SearchForm(
                searchBookInput = searchBookInput,
                onSearchUpdateChange = onSearchUpdateChange,
                search = search,
                searchBooksState = searchBookUI.booksInfo
            )
        }

        is SearchBookUI.NotAvailable -> {
            TerminalError(error = searchBookUI.messsage)
        }

    }
}


@Composable
fun SearchForm(
    searchBookInput: String,
    onSearchUpdateChange: (String) -> Unit,
    search: () -> Unit,
    searchBooksState: SearchBooksState

) {
    SearchScreenUserInputField(
        searchBookInput = searchBookInput,
        onSearchUpdateChange = onSearchUpdateChange,
        search = search
    )
    SearchForBooks(searchBooksState = searchBooksState)
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchTopAppBar(

    title: String,
    onBackPress: () -> Unit = {}
) {
    TopAppBar(
        title = {
            Text(text = title)
        },

        navigationIcon = {
            IconButton(onClick = {
                onBackPress()
            }) {
                Icon(
                    imageVector = Icons.Default.ArrowBack,
                    contentDescription = stringResource(R.string.navigate_back)
                )
            }
        },

        actions = {
            IconButton(
                onClick = {}
            ) {
                Icon(painter = painterResource(R.drawable.ic_logout), contentDescription = null)
            }
        }
    )
}

@Composable
fun SearchScreenUserInputField(
    searchBookInput: String,
    onSearchUpdateChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    search: () -> Unit
) {
    val keyboardController = LocalSoftwareKeyboardController.current
    val textSearch = remember { mutableStateOf(searchBookInput) }

    TextInputField(
        searchText = textSearch.value,
        onSearchUpdateChange = {
            onSearchUpdateChange(it)
            textSearch.value = it
        },
        placeholder = {
            Text(
                text = stringResource(R.string.label_search)
            )
        },
        keyboardOptions = KeyboardOptions.Default.copy(
            keyboardType = KeyboardType.Text,
            imeAction = ImeAction.Done
        ),
        keyboardActions = KeyboardActions(onDone = {
            search()
            keyboardController?.hide()
        }),
        trailingIcon = {
            if (textSearch.value.isNotEmpty())
                IconButton(onClick = {
                    textSearch.value = ""
                }) {
                    Icon(
                        imageVector = Icons.Default.Close,
                        contentDescription = null
                    )
                }
        },
        singleLine = true,
        modifier = modifier
            .fillMaxWidth()
            .heightIn(48.dp),
        textStyle = TextStyle(
            color = Color.Black,
            fontSize = MaterialTheme.typography.labelLarge.fontSize
        )
    )
}

@Composable
fun SearchForBooks(searchBooksState: SearchBooksState) {
    LazyColumn(
        modifier = Modifier
            .padding(top = 8.dp)
            .fillMaxWidth()
            .wrapContentHeight()
    ) {
        items(items = searchBooksState.items, key = {
            it.id
        }) {
            BookItem(
                bookModel = it,
                onBookItem = {}
            )
        }
    }
}


@Composable
fun Progressbar(modifier: Modifier = Modifier) {
    Column(
        verticalArrangement = Arrangement.Center,
        modifier = modifier.size(48.dp)
    ) {
        CircularProgressIndicator(
            modifier = modifier
                .fillMaxWidth()
                .heightIn(min = 4.dp)
                .background(Color.Green)
        )

    }
}

@Composable
fun TerminalError(error: String) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = Modifier
            .padding(4.dp)
            .fillMaxSize()
    ) {
        Text(
            modifier = Modifier
                .padding(horizontal = 8.dp),
            text = error,
            style = TextStyle(
                color = MaterialTheme.colorScheme.onBackground,
                fontSize = MaterialTheme.typography.titleMedium.fontSize,
                fontWeight = FontWeight.Medium
            )
        )
    }
}


