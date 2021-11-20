package com.onurcan.pomodoro.view.components


import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Icon
import androidx.compose.material.IconButton

import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState

import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.ViewModel
import com.onurcan.pomodoro.R
import com.onurcan.pomodoro.viewmodel.MainViewModel

@Composable
fun Player(
    mainViewModel: MainViewModel,
    startTimer: () -> Unit,
    pauseTimer: () -> Unit,
    turnBreak:()-> Unit,
    colorDarker:Color,
    taskName:String,

   // isPlaying:Boolean,

){
    val isPlaying by mainViewModel.isPlaying.observeAsState(false)
    var playPause by remember { mutableStateOf(R.drawable.play) }
    mainViewModel._currentTask.value=taskName
    if(isPlaying==true){
        playPause=R.drawable.pause
    }else{
        playPause=R.drawable.play
    }

    Box(modifier = Modifier
        .height(100.dp)
        .fillMaxWidth()) {
        Row(verticalAlignment = Alignment.CenterVertically,horizontalArrangement = Arrangement.SpaceAround,modifier = Modifier.fillMaxSize()) {
            Text(taskName, fontFamily=  FontFamily(
                Font(
                R.font.player
            )
            ),fontSize =30.sp ,color =colorDarker,textAlign = TextAlign.Center,modifier = Modifier.padding(bottom = 2.dp).width(200.dp))
            IconButton(  onClick = {
                if(isPlaying==true){
                    playPause=R.drawable.play
                    pauseTimer()
                }else{
                    playPause=R.drawable.pause
                    startTimer()
                }



                 },){
                Icon(

                    painter = painterResource(id =playPause),
                    tint= colorDarker,
                    contentDescription = null // decorative element
                )


            }
            IconButton(  onClick =
            {
                playPause=R.drawable.play
                turnBreak()
            } ,){
                Icon(
                    tint= colorDarker,
                    modifier=Modifier.rotate(180f),
                    painter = painterResource(id = R.drawable.next),
                    contentDescription = null // decorative element
                )


            }


        }
    }
}