package com.example.tourismappmockup

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.AnimatedVisibility
import com.example.tourismappmockup.animationConcept.AnimateBoxTransition
import com.example.tourismappmockup.animationConcept.AnimateImageScalingInOrOut
import com.example.tourismappmockup.animationConcept.AnimateTextExpandable
import com.example.tourismappmockup.animationConcept.AnimationByTransitionStateScreen
import com.example.tourismappmockup.animationConcept.AnimationScreenExpandableToolbar
import com.example.tourismappmockup.animationConcept.AnimationVisibilityChanges
import com.example.tourismappmockup.animationConcept.CustomColorByBlendMode
import com.example.tourismappmockup.animationConcept.CustomIMageBlur
import com.example.tourismappmockup.animationConcept.CustomIMageByBlendModeMatrix
import com.example.tourismappmockup.animationConcept.CustomImageByAspectRatio
import com.example.tourismappmockup.animationConcept.ImageClip
import com.example.tourismappmockup.animationConcept.ImageColorByBrush
import com.example.tourismappmockup.animationConcept.ImageContentScaling
import com.example.tourismappmockup.bottomnavigation.MainScreen
import com.example.tourismappmockup.ui.theme.TourismAppMockUpTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            TourismAppMockUpTheme {
                MainScreen()
            }
        }
    }
}
