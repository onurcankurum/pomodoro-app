package com.onurcan.pomodoro.views

import android.app.Application
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*

import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.onurcan.pomodoro.R
import com.onurcan.pomodoro.utils.Utility
import com.onurcan.pomodoro.utils.Utility.formatTime
import com.onurcan.pomodoro.view.components.CountDownIndicator
import com.onurcan.pomodoro.view.components.ShowCelebration
import com.onurcan.pomodoro.viewmodel.MainViewModel
import com.google.accompanist.systemuicontroller.rememberSystemUiController

import com.onurcan.pomodoro.views.CountDownView as CountDownView

@Composable
fun CountDownView(viewModel: MainViewModel = viewModel() ) {



    CountDownView(
        viewModel=viewModel,
        optionSelected = viewModel::handleCountDownTimer,

    )

}



@OptIn(ExperimentalMaterialApi::class)

@Composable
fun CountDownView(
    viewModel: MainViewModel,
    optionSelected: () -> Unit,

    ) {


    val bottomSheetScaffoldState = rememberBottomSheetScaffoldState(
        bottomSheetState = rememberBottomSheetState(
            BottomSheetValue.Collapsed

        )
    )

    BottomSheetScaffold(

        sheetBackgroundColor=Color.Transparent,
        scaffoldState = bottomSheetScaffoldState,
        sheetElevation=0.dp,
        sheetContent = {
           BottomSheet(
               mainViewModel = viewModel,
               bottomSheetState = bottomSheetScaffoldState.bottomSheetState,
           )
                       },

        sheetPeekHeight = 100.dp


        ) {
        val colorForegroundR by viewModel.colorForeground.observeAsState( com.onurcan.pomodoro.R.color.ligthpurple)
        val colorBackgroundR by viewModel.colorBackground.observeAsState( com.onurcan.pomodoro.R.color.darkpurple)
        val message by viewModel.message.observeAsState(com.onurcan.pomodoro.R.string.uready)

        val colorBackground by animateColorAsState(targetValue = colorResource(id = colorBackgroundR), tween(
            durationMillis = 500
        ))
        val colorForeground by animateColorAsState(targetValue = colorResource(id = colorForegroundR), tween(
            durationMillis = 500
        ))

        val systemUiController = rememberSystemUiController()

        systemUiController.setSystemBarsColor(
            color = colorBackground
        )
        val color : Color =colorBackground
        SideEffect {
            systemUiController.setNavigationBarColor(
                color =color,

            )
        }

        Column(


            modifier = Modifier
                .fillMaxSize()
                .background(colorBackground),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {



            Text(
                text = stringResource(id = message),
                color = androidx.compose.ui.graphics.Color.White,
                fontSize = 30.sp,
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 50.dp, bottom = 30.dp),

                fontFamily =FontFamily(Font(R.font.player))

            )




Row() {
    Spacer( modifier = Modifier.weight(2f))

        CountDownIndicator(
            viewModel,
            Modifier
                .padding(top = 50.dp)
                .weight(14f),

       optionSelected= optionSelected,
        colorBackground= colorBackground ,
        colorForeground= colorForeground,
        stroke = 12,

            )

    Spacer( modifier = Modifier.weight(2f))

}

            }
        }
    }
