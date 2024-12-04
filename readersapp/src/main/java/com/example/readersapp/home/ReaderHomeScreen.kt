package com.example.readersapp.home

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.Divider
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.readersapp.R
import com.example.readersapp.component.BooksListed
import com.example.readersapp.component.CurrentBooks
import com.example.readersapp.component.FabContent
import com.example.readersapp.component.SingleTextHeader
import com.example.readersapp.navigation.ReaderRoute
import com.google.firebase.auth.FirebaseAuth
import timber.log.Timber

@Composable
fun ReaderHomeScreen(
    navController: NavController = NavController(LocalContext.current)
) {
    val email = FirebaseAuth.getInstance().currentUser?.email
    val currentUser = if (!email.isNullOrBlank())
        FirebaseAuth.getInstance().currentUser?.email?.split("@")?.get(0)
    else
        "N/A"
    Scaffold(
        topBar = {
            ReaderTopAppBar(
                title = currentUser!!,
                navController = navController
            )
        },
        floatingActionButton = {
            FabContent(
                navController = navController,
                onTap = {}
            )
        }
    ) { initialPadding ->
        Surface(
            tonalElevation = 3.dp,
            modifier = Modifier
                .padding(initialPadding)
                .fillMaxSize()
        ) {
            ReaderHomeStructure(navController = navController)
        }
    }
}

@Composable
fun ReaderHomeStructure(navController: NavController) {
    Column(
        verticalArrangement = Arrangement.Top
    ) {
        SingleTextHeader(label = stringResource(R.string.label_header_current_books))
        ReaderCurrentBooksList(
            currentBooks = showBookList,
            navController = navController
        )
        Row(
            modifier = Modifier
                .padding(start = 24.dp, end = 24.dp)
                .fillMaxWidth()

        ) {

            Divider(
                modifier = Modifier
                    .fillMaxWidth(),
                thickness = 1.dp,
                color = Color.Gray
            )
        }

        SingleTextHeader(label = stringResource(R.string.label_header_books_listed))
        BooksListed(booksListed = showBookList)

    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ReaderTopAppBar(
    title: String,
    showProfile: Boolean = true,
    navController: NavController
) {
    val isVisible = remember { mutableStateOf(false) }
    TopAppBar(
        modifier = Modifier
            .background(Color.LightGray),
        title = {
            Text(
                text = title,
                modifier = Modifier
                    .padding(start = 8.dp)
            )
        },
        navigationIcon = {
            IconButton(onClick = {}) {
                Icon(
                    imageVector = Icons.Default.List,
                    contentDescription = stringResource(R.string.navigate_back)
                )
            }
        },
        actions = {
            IconButton(onClick = { isVisible.value = true }) {
                Icon(imageVector = Icons.Default.MoreVert, contentDescription = "")
                DropdownMenu(
                    expanded = isVisible.value,
                    onDismissRequest = {
                        isVisible.value = !isVisible.value
                    }
                ) {
                    DropdownMenuItem(onClick = {
                        navController.navigate(ReaderRoute.ReaderStatusScreen.name)
                    },
                        text = {
                            Text(text = stringResource(R.string.menu_item_profile))
                        }
                    )
                    DropdownMenuItem(onClick = {
                        navController.navigate(ReaderRoute.ReaderStatusScreen.name)
                    },
                        text = {
                            Text(text = stringResource(R.string.menu_itemlogout))
                        }
                    )
                }
            }
        }
    )
}

@Composable
fun ReaderCurrentBooksList(currentBooks: List<BookModel>, navController: NavController) {
    val context = LocalContext.current.applicationContext
    CurrentBooks(currentBooks = currentBooks, onPress = {
        Toast.makeText(context,"hello ${it}",Toast.LENGTH_SHORT).show()
        Timber.i("$it")
    })
}

@Composable
fun BooksListesd(listOfBooks: List<BookModel>){
     BooksListed(booksListed = listOfBooks)
}

val showBookList = listOf(
    BookModel(
        id="1",
        book_name = "Flutter",
        book_Author = "Eric",
        notes = "Flutter for beginners"
    )
)