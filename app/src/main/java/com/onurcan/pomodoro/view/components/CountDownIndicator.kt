package com.onurcan.pomodoro.view.components

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color

import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.onurcan.pomodoro.utils.Utility
import com.onurcan.pomodoro.utils.Utility.formatTime
import com.onurcan.pomodoro.viewmodel.MainViewModel

@Composable
fun CountDownIndicator(
    viewModel: MainViewModel,
    modifier: Modifier = Modifier,
    optionSelected: () -> Unit,
     stroke: Int,
     colorForeground:Color,
    colorBackground:Color,
){
    val time by viewModel.time.observeAsState(Utility.TIME_COUNTDOWNLONG.formatTime())
    val progress by viewModel.progress.observeAsState(1.00F)
    val animatedProgress by animateFloatAsState(
        targetValue = progress,
        animationSpec = ProgressIndicatorDefaults.ProgressAnimationSpec,
    )

    Column(modifier = modifier) {
        Box(   contentAlignment = Alignment.Center,) {
            Button(   colors = ButtonDefaults.buttonColors(
                backgroundColor = colorForeground,
                contentColor = Color.White), elevation = ButtonDefaults.elevation(
                defaultElevation = 5.dp,
                pressedElevation = 1.dp,
                disabledElevation = 0.dp
            ) ,onClick = {optionSelected()  },
                shape = RoundedCornerShape(50),
                modifier = Modifier
                    .fillMaxWidth(1f)
                    .aspectRatio(1f).padding(1.dp) ) {

            }

//

            CircularProgressIndicator(

                progress = animatedProgress,
                modifier = Modifier
                    .fillMaxWidth()
                    .aspectRatio(1f),//.background(color =colorResource(R.color.teal_200),shape = CircleShape),
                color = colorBackground,
                strokeWidth = stroke.dp,
            )

            Column(modifier = Modifier.align(Alignment.Center)) {
                Text(
                    text = time,
                    color = Color.White,


                    style = MaterialTheme.typography.h2,
                    modifier = Modifier.align(Alignment.CenterHorizontally),
                    fontWeight = FontWeight.Bold
                )
            }

        }
    }
}


