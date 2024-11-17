package com.example.readersapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.readersapp.navigation.NavigationManager
import com.example.readersapp.ui.theme.TourismAppPrototypeTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
//        val firebaseApp = FirebaseFirestore.getInstance()
//        val user: MutableMap<String, String> = HashMap()
//        user["first_name"] = "joe"
//        user["last_name"] = "wit"
//
//        firebaseApp.collection("user")
//            .add(user)
//            .addOnSuccessListener {
//                Timber.i("FB: Data Stores :: ${it.id}")
//            }
//            .addOnFailureListener() {
//                Timber.i("FB: Data Stores :: ${it.message}")
//            }
        setContent {
            TourismAppPrototypeTheme {
                ReaderApp()
            }
        }
    }
}

@Composable
fun ReaderApp(){
    Surface(modifier = Modifier.fillMaxSize()) {
        NavigationManager()
    }
}

//@Composable
//fun Greeting(name: String, modifier: Modifier = Modifier) {
//    Text(
//        text = "Hello $name!",
//        modifier = modifier
//    )
//}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    TourismAppPrototypeTheme {
    }
}