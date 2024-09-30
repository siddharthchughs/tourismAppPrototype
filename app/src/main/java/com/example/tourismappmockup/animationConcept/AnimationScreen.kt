package com.example.tourismappmockup.animationConcept

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateColor
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.animateDp
import androidx.compose.animation.core.updateTransition
import androidx.compose.animation.expandVertically
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MediumTopAppBar
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.Blue
import androidx.compose.ui.graphics.Color.Companion.LightGray
import androidx.compose.ui.graphics.Color.Companion.Red
import androidx.compose.ui.graphics.Color.Companion.Yellow
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.example.tourismappmockup.R
import com.example.tourismappmockup.ui.theme.Pink40


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AnimationScreenExpandableToolbar(){
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior(rememberTopAppBarState())

    var showGreen by remember { mutableStateOf(true) }

    Scaffold(
        modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            MediumTopAppBar(
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.primary,
                ),
                title = {
                    Text(
                        "Medium Top App Bar",
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                },
                navigationIcon = {
                    IconButton(onClick = { /* do something */ }) {
                        Icon(
                            imageVector = Icons.Filled.ArrowBack,
                            contentDescription = "Localized description"
                        )
                    }
                },
                actions = {
                    IconButton(onClick = { /* do something */ }) {
                        Icon(
                            imageVector = Icons.Filled.Menu,
                            contentDescription = "Localized description"
                        )
                    }
//                    ,
//                    IconButton(onClick = {
//                        showGreen = !showGreen
//
//                    }){
//                        AnimatedVisibility(
//                            visible = showGreen,
//                            // By Default, `scaleIn` uses the center as its pivot point. When used with a vertical
//                            // expansion from the vertical center, the content will be growing from the center of
//                            // the vertically expanding layout.
//                            enter = scaleIn() + expandVertically(expandFrom = Alignment.CenterVertically),
//                            // By Default, `scaleOut` uses the center as its pivot point. When used with an
//                            // ExitTransition that shrinks towards the center, the content will be shrinking both
//                            // in terms of scale and layout size towards the center.
//                            exit = scaleOut() + shrinkVertically(shrinkTowards = Alignment.CenterVertically)
//                        ) {
//                            Box(
//                                Modifier.size(100.dp)
//                                    .background(color = Color.Green, shape = RoundedCornerShape(20.dp))
//                            )
//                        }

//                    }

                },
                scrollBehavior = scrollBehavior
            )
        },
    ) { innerPadding ->

        Text(text = "Hello this is collapseable toolbar", modifier = Modifier.padding(innerPadding))
    }
}
@Composable
fun AnimationScreen() {
    var blueColor by remember {
        mutableStateOf(true)
    }
    val color by animateColorAsState(if (blueColor) Blue else Pink40)
//    val color = if(blueColor) Blue else Pink40
    Column() {
        Spacer(
            modifier = Modifier
                .heightIn(56.dp)
        )
        Button(
            onClick = { blueColor = !blueColor }
        ) {
            Text(text = "Click")
        }

        Spacer(
            modifier = Modifier
                .heightIn(56.dp)
        )

        Box(
            modifier = Modifier
                .size(200.dp)
                .background(color)
        )
    }
}

enum class TypeBox {
    Small,
    Large
}

@Composable
fun AnimationByStateScreen() {
    var boxType by remember { mutableStateOf(TypeBox.Small) }
    val color = when (boxType) {
        TypeBox.Small -> Blue
        TypeBox.Large -> Pink40
    }
    val size = when (boxType) {
        TypeBox.Small -> 70.dp
        TypeBox.Large -> 100.dp
    }

    Column() {
        Spacer(
            modifier = Modifier
                .heightIn(56.dp)
        )
        Button(
            onClick = {
                boxType = when (boxType) {
                    TypeBox.Small -> TypeBox.Large
                    TypeBox.Large -> TypeBox.Small
                }
            }
        ) {
            Text(text = "Click")
        }

        Spacer(
            modifier = Modifier
                .heightIn(56.dp)
        )

        Box(
            modifier = Modifier
                .size(size)
                .background(color)
        )
    }
}

@Composable
fun AnimationByTransitionStateScreen() {
    var boxType by remember { mutableStateOf(TypeBox.Small) }
    val transition = updateTransition(targetState = boxType)
    val color by transition.animateColor() { stateChange ->
        when (stateChange) {
            TypeBox.Small -> Blue
            TypeBox.Large -> Pink40
        }
    }
    val size by transition.animateDp() { sizeChange ->
        when (sizeChange) {
            TypeBox.Small -> 70.dp
            TypeBox.Large -> 100.dp
        }
    }

    Column() {
        Spacer(
            modifier = Modifier
                .heightIn(56.dp)
        )
        Button(
            onClick = {
                boxType = when (boxType) {
                    TypeBox.Small -> TypeBox.Large
                    TypeBox.Large -> TypeBox.Small
                }
            }
        ) {
            Text(text = "Click")
        }

        Spacer(
            modifier = Modifier
                .heightIn(56.dp)
        )

        Box(
            modifier = Modifier
                .size(size)
                .background(color)
        )
    }
}

@Composable
fun AnimationVisibilityChanges() {

    var visibility by remember { mutableStateOf(true) }
    var color = if (visibility) Blue else Yellow
    Column {
        Spacer(
            modifier = Modifier
                .heightIn(min = 48.dp)
        )

        Button(onClick = {
            visibility = !visibility
        }) {
            Text(
                text = if (visibility) "Hide" else "Show"
            )
        }

        Spacer(
            modifier = Modifier
                .heightIn(min = 56.dp)
        )
        AnimatedVisibility(visibility) {
            Box(
                modifier = Modifier
                    .size(60.dp)
                    .background(color)
            )
        }
    }
}

@Composable
fun AnimateTextExpandable(){
    var isExpanded by remember { mutableStateOf(true) }
    Column {
        Spacer(
            modifier = Modifier
                .heightIn(min = 48.dp)
        )

        Button(onClick = {
            isExpanded = !isExpanded
        }) {
            Text(
                text = if (isExpanded) "Hide" else "Show"
            )
        }

        Spacer(
            modifier = Modifier
                .heightIn(min = 56.dp)
        )
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight()
                    .animateContentSize()
                    .padding(horizontal = 16.dp)
                    .background(LightGray)
            ){

                Text(
                    text = stringResource(R.string.detail_description),
                    maxLines = if(isExpanded) Int.MAX_VALUE else 2
                )
            }
    }
}

@Composable
fun AnimateImageScalingInOrOut(){
    var isVisible by remember {
        mutableStateOf(true)
    }
    val color = if(isVisible) Yellow else Red

    Column {
        Spacer(modifier = Modifier
            .widthIn(20.dp)
            .heightIn(100.dp)
        )

        Button(onClick = {
            isVisible = !isVisible
        }){
            Text(text = if(isVisible) "Change to Red" else "Change to Yellow"  )
        }

        Spacer(modifier = Modifier
            .widthIn(20.dp)
            .heightIn(100.dp)
        )

        Box(
            modifier = Modifier
                .size(200.dp)
                .background(color)
        )
    }

}

enum class BoxShape{
    Small,
    Large
}


@Composable
fun AnimateBoxTransition(){
    var boxTypeShape by remember {
        mutableStateOf(BoxShape.Small)
    }
    var showRed by remember { mutableStateOf(true) }
    var showGreen by remember { mutableStateOf(true) }

//    val transition = updateTransition(boxTypeShape)
//    val color by transition.animateColor { coloState->
//             when(coloState){
//                 BoxShape.Small -> Yellow
//                 BoxShape.Large -> Red
//             }
//    }
//
//    val alterSize by transition.animateDp { alterDp->
//        when(alterDp){
//            BoxShape.Small -> 0.dp
//            BoxShape.Large -> 100.dp
//        }
//    }

    Column {
        Spacer(
            modifier = Modifier
                .widthIn(20.dp)
                .heightIn(100.dp)
        )

        Button(onClick = {
            showGreen = !showGreen
        }) {
            Text(text = "transition")
        }

        Spacer(
            modifier = Modifier
                .widthIn(20.dp)
                .heightIn(100.dp)
        )

//        Box(
//            modifier = Modifier
//                .size(alterSize)
//                .background(color)
//        ){
//            Image(
//                painter = painterResource(R.drawable.ic_launcher_background),
//                modifier = Modifier
//                    .size(alterSize),
//                contentDescription = ""
//            )
//        }
        AnimatedVisibility(
            visible = showGreen,
            // By Default, `scaleIn` uses the center as its pivot point. When used with a vertical
            // expansion from the vertical center, the content will be growing from the center of
            // the vertically expanding layout.
            enter = scaleIn() + expandVertically(expandFrom = Alignment.CenterVertically),
            // By Default, `scaleOut` uses the center as its pivot point. When used with an
            // ExitTransition that shrinks towards the center, the content will be shrinking both
            // in terms of scale and layout size towards the center.
            exit = scaleOut() + shrinkVertically(shrinkTowards = Alignment.CenterVertically)
        ) {
            Box(
                Modifier.size(100.dp)
                    .background(color = Color.Green, shape = RoundedCornerShape(20.dp))
            )
        }
    }
}

