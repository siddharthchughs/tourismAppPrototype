package com.example.readersapp.splash

import android.view.animation.OvershootInterpolator
import androidx.compose.animation.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.readersapp.R
import com.example.readersapp.navigation.ReaderRoute
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.delay

@Composable
fun SplashScreen(
    navController: NavController
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        SplashScreenStructure(navController = navController)
    }
}

@Composable
fun SplashScreenStructure(navController: NavController) {
    SplashImage(navController = navController)
}

@Composable
fun SplashImage(navController: NavController) {
    val scale = remember {
        androidx.compose.animation.core.Animatable(initialValue = 0f)
    }

    LaunchedEffect(
        key1 = true,
    ){
     scale.animateTo(targetValue = 0.9f,
         animationSpec = tween(
             durationMillis = 800,
             easing = {
                 OvershootInterpolator(0f)
                     .getInterpolation(it)
             }
         )
     )
        delay(1_000L)
        if(FirebaseAuth.getInstance().currentUser?.email.isNullOrEmpty()){
            navController.navigate(ReaderRoute.LoginScreen.name){
                popUpTo(navController.graph.id){
                    inclusive = true
                }
            }
        }
        else{
            navController.navigate(ReaderRoute.ReaderHomeScreen.name){
                popUpTo(navController.graph.id){
                    inclusive = true
                }
            }
        }

//        navController.navigate(ReaderRoute.LoginScreen.name){
//            popUpTo(navController.graph.id){
//                inclusive = true
//            }
//        }

    }
    Surface(
        modifier = Modifier
            .padding(24.dp)
            .size(330.dp)
            .scale(scale = scale.value),
        shape = CircleShape,
        color = MaterialTheme.colorScheme.primary,
        border = BorderStroke(
            width = 2.dp,
            color = Color.Yellow
        )
    ) {
        Column(
            modifier = Modifier
                .padding(1.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            SingleHeaderText(label = stringResource(R.string.label_title))
            Spacer(
                modifier = Modifier
                    .height(16.dp)
            )
            SingleSubTitleText(label = stringResource(R.string.sub_title))
        }
    }
}

@Composable
fun SingleHeaderText(label: String) {
    Text(
        text = label,
        style = TextStyle(
            color = MaterialTheme.colorScheme.onBackground,
            fontSize = MaterialTheme.typography.displayMedium.fontSize,
        )
    )
}

@Composable
fun SingleSubTitleText(label: String) {
    Text(
        text = label,
        style = TextStyle(
            color = MaterialTheme.colorScheme.surface,
            fontSize = MaterialTheme.typography.titleLarge.fontSize,
        )
    )
}

@Preview(showBackground = true)
@Composable
fun DefaultSplashScreen(){
//    SplashScreen()
}
