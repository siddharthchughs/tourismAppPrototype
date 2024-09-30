package com.example.tourismappmockup.animationConcept

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.BlurredEdgeTreatment
import androidx.compose.ui.draw.blur
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.ColorMatrix
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.tourismappmockup.R

@Composable
fun ImageContentScaling(){
    Column (horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = Modifier
            .fillMaxSize()
    ) {
        val imageModifier = Modifier
            .size(150.dp)
            .border(BorderStroke(2.dp, Color.Red))
            .background(Color.Yellow)
        Image(
            painter = painterResource(R.drawable.ic_launcher_foreground),
            contentDescription = stringResource(R.string.detail_description),
            contentScale = ContentScale.FillWidth,
            modifier = imageModifier
        )
    }
}

@Composable
fun ImageClip(){
    Column (horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = Modifier
            .fillMaxSize()
    ) {
        val imageModifier = Modifier
            .size(150.dp)
            .border(BorderStroke(2.dp, color = Color.Magenta),
                shape = RectangleShape)
            .padding(4.dp)
            .clip(RoundedCornerShape(30.dp))
            .background(Color.Green)
        Image(
            painter = painterResource(R.drawable.ic_launcher_foreground),
            contentDescription = stringResource(R.string.detail_description),
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .size(150.dp)
                .border(BorderStroke(2.dp, color = Color.Magenta),
                    shape = RoundedCornerShape(20)
                )
                .padding(4.dp)
                .clip(RoundedCornerShape(30.dp))
                .background(Color.Green)
        )
    }
}

@Composable
fun ImageColorByBrush(){
    Column (horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = Modifier
            .fillMaxSize()
    ) {
//        val imageModifier = Modifier
//            .size(150.dp)
//            .border(BorderStroke(2.dp, color = Color.Magenta),
//                shape = RectangleShape)
//            .padding(4.dp)
//            .clip(RoundedCornerShape(30.dp))
//            .background(Color.Green)
        val rainbowBrush = remember {
            Brush.sweepGradient(
                listOf(
                    Color.Green,
                    Color.Yellow,
                    Color.Magenta,
                    Color.Blue
                )
            )
        }
        Image(
            painter = painterResource(R.drawable.ic_launcher_foreground),
            contentDescription = stringResource(R.string.detail_description),
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .size(150.dp)
                .border(BorderStroke(2.dp, brush = rainbowBrush),
                    shape = RoundedCornerShape(20)
                )
                .padding(4.dp)
                .clip(RoundedCornerShape(30.dp))
                .background(Color.Green)
        )
    }
}

@Composable
fun CustomImageByAspectRatio(){
    Column (

    ) {
        Spacer(modifier = Modifier
            .heightIn(150.dp))
//        val imageModifier = Modifier
//            .size(150.dp)
//            .border(BorderStroke(2.dp, color = Color.Magenta),
//                shape = RectangleShape)
//            .padding(4.dp)
//            .clip(RoundedCornerShape(30.dp))
//            .background(Color.Green)
        val rainbowBrush = remember {
            Brush.sweepGradient(
                listOf(
                    Color.Green,
                    Color.Yellow,
                    Color.Magenta,
                    Color.Blue
                )
            )
        }
        Image(
            painter = painterResource(R.drawable.ic_launcher_foreground),
            contentDescription = stringResource(R.string.detail_description),
            colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.primary)
//            modifier = Modifier
//                .border(BorderStroke(2.dp, brush = rainbowBrush),
//                    shape = RoundedCornerShape(20)
//                )
//                .padding(4.dp)
//                .clip(RoundedCornerShape(30.dp))
//                .background(Color.Green)
        )
    }
}

@Composable
fun CustomColorByBlendMode(){
    Column {
        Spacer(modifier = Modifier
            .heightIn(150.dp))
//        val imageModifier = Modifier
//            .size(150.dp)
//            .border(BorderStroke(2.dp, color = Color.Magenta),
//                shape = RectangleShape)
//            .padding(4.dp)
//            .clip(RoundedCornerShape(30.dp))
//            .background(Color.Green)
        val rainbowBrush = remember {
            Brush.sweepGradient(
                listOf(
                    Color.Green,
                    Color.Yellow,
                    Color.Magenta,
                    Color.Blue
                )
            )
        }
        Image(
            painter = painterResource(R.drawable.ic_launcher_foreground),
            contentDescription = stringResource(R.string.detail_description),
            colorFilter = ColorFilter.tint(color = Color.Magenta, BlendMode.ColorDodge)
//            modifier = Modifier
//                .border(BorderStroke(2.dp, brush = rainbowBrush),
//                    shape = RoundedCornerShape(20)
//                )
//                .padding(4.dp)
//                .clip(RoundedCornerShape(30.dp))
//                .background(Color.Green)
        )
    }
}

@Composable
fun CustomIMageByBlendModeMatrix(){
    Column {
        Spacer(modifier = Modifier
            .heightIn(150.dp))
//        val imageModifier = Modifier
//            .size(150.dp)
//            .border(BorderStroke(2.dp, color = Color.Magenta),
//                shape = RectangleShape)
//            .padding(4.dp)
//            .clip(RoundedCornerShape(30.dp))
//            .background(Color.Green)
        val rainbowBrush = remember {
            Brush.sweepGradient(
                listOf(
                    Color.Green,
                    Color.Yellow,
                    Color.Magenta,
                    Color.Blue
                )
            )
        }
        val contrast = 2f // 0f..10f (1 should be default)
        val brightness = -180f // -255f..255f (0 should be default)
        val colorMatrix = floatArrayOf(
            contrast, 0f, 0f, 0f, brightness,
            0f, contrast, 0f, 0f, brightness,
            0f, 0f, contrast, 0f, brightness,
            0f, 0f, 0f, 1f,0f)
        Image(
            painter = painterResource(R.drawable.ic_launcher_foreground),
            contentDescription = stringResource(R.string.detail_description),
            colorFilter = ColorFilter.colorMatrix(ColorMatrix(colorMatrix))
//            modifier = Modifier
//                .border(BorderStroke(2.dp, brush = rainbowBrush),
//                    shape = RoundedCornerShape(20)
//                )
//                .padding(4.dp)
//                .clip(RoundedCornerShape(30.dp))
        )
    }
}

@Composable
fun CustomIMageBlur(){
    Column {
        Spacer(modifier = Modifier
            .heightIn(150.dp))
//        val imageModifier = Modifier
//            .size(150.dp)
//            .border(BorderStroke(2.dp, color = Color.Magenta),
//                shape = RectangleShape)
//            .padding(4.dp)
//            .clip(RoundedCornerShape(30.dp))
//            .background(Color.Green)
        val rainbowBrush = remember {
            Brush.sweepGradient(
                listOf(
                    Color.Green,
                    Color.Yellow,
                    Color.Magenta,
                    Color.Blue
                )
            )
        }
        val contrast = 2f // 0f..10f (1 should be default)
        val brightness = -180f // -255f..255f (0 should be default)
        Image(
            painter = painterResource(R.drawable.baseline_account_balance),
            contentDescription = stringResource(R.string.detail_description),
            colorFilter = ColorFilter.tint(Color.Green, BlendMode.Hue),
           contentScale = ContentScale.Crop,
            modifier = Modifier
                .size(150.dp)
                .blur(
                    radiusX = 40.dp,
                    radiusY = 20.dp,
                    edgeTreatment = BlurredEdgeTreatment.Unbounded
                )
                .clip(RoundedCornerShape(9.dp))
        )
    }
}