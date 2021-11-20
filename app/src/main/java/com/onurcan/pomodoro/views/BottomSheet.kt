package com.onurcan.pomodoro.views


import android.app.Application
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusModifier
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.graphics.ColorUtils
import com.onurcan.pomodoro.R
import com.onurcan.pomodoro.view.components.Dialog
import com.onurcan.pomodoro.view.components.Player
import com.onurcan.pomodoro.view.components.TaskItem
import com.onurcan.pomodoro.viewmodel.BottomSheetViewModel
import com.onurcan.pomodoro.viewmodel.MainViewModel
import kotlinx.coroutines.launch
import java.lang.Exception

fun colorBlender(colorDark:Color, colorLight:Color, fraction :Float): Int {

    return ColorUtils.blendARGB(colorDark.toArgb(), colorLight.toArgb(), fraction)

}



@OptIn(ExperimentalMaterialApi::class)
fun fractionHandler(bottomSheetState: BottomSheetState):Float{
    try {
        if(bottomSheetState.progress.from==BottomSheetValue.Collapsed&&bottomSheetState.progress.to==BottomSheetValue.Expanded){
            if(bottomSheetState.progress.fraction==1f){
                return 0f
            }
            else{
                return bottomSheetState.progress.fraction
            }

        }
        if(bottomSheetState.progress.from==BottomSheetValue.Expanded&&bottomSheetState.progress.to==BottomSheetValue.Collapsed){
            if(bottomSheetState.progress.fraction==1f){
                return 1f
            }
            else{
                return 1-bottomSheetState.progress.fraction
            }

        }
        if(bottomSheetState.progress.from==BottomSheetValue.Expanded&&bottomSheetState.progress.to==BottomSheetValue.Expanded) {

            return 1f
        }
        return 0f
    }catch (
        e:Exception
    ){
        return 1f
    }


}



@OptIn(ExperimentalMaterialApi::class)
@Composable
fun BottomSheet(
    mainViewModel:MainViewModel,
    viewModel: BottomSheetViewModel  = BottomSheetViewModel(LocalContext.current.applicationContext as Application),
    bottomSheetState : BottomSheetState


) {



    val isPlaying by mainViewModel.isBreak.observeAsState()
     println(isPlaying.toString())
    viewModel.getTasksWithPomodoros()


    val openDialog = remember { mutableStateOf(false)}

    val coroutineScope = rememberCoroutineScope()
    val totalPomodoroCountColor:Color
    val totalString:Color
    val tableHeaderColor:Color
    val itemColor :Color
    val borderColor :Color


    if(mainViewModel.isBreak.value!!){
        borderColor=colorResource(id = R.color.title_border_teal)
        totalPomodoroCountColor= Color(0xffd6fff7)
        totalString = colorResource(id = R.color.lightteal)
        tableHeaderColor=colorResource(id = R.color.lighterDarkTeal)
        itemColor= colorResource(id = R.color.lighterTeal)

    }else{
        borderColor=colorResource(id = R.color.title_border_purp)
        totalPomodoroCountColor= Color(0xffffecfc)
        totalString = colorResource(id = R.color.ligthpurple)
        tableHeaderColor=colorResource(id = R.color.lighterDarkPuple)
        itemColor= colorResource(id = R.color.lighterPurple)
    }



    Box(
        Modifier
            .fillMaxSize()
            .padding(10.dp)
    ) {



        if(openDialog.value){

            Dialog(viewModel=viewModel,onCancel={openDialog.value=false},onSave={
                openDialog.value=false
            },backgroundColor =totalString,borderColor=borderColor,
            saveButtonColor = colorResource(id =mainViewModel.colorDarker.value!! ) )
        }


        Card(elevation = 15.dp, modifier = Modifier.fillMaxSize()) {
            val colorDarkerR by mainViewModel.colorDarker.observeAsState(R.color.darkerpurple)

            val colorForegroundR by mainViewModel.colorForeground.observeAsState(R.color.ligthpurple)
            val colorForeground by animateColorAsState(targetValue = colorResource(colorForegroundR), tween(
                durationMillis = 500
            )
            )
            val colorDarker by animateColorAsState(targetValue = colorResource(colorDarkerR), tween(
                durationMillis = 500
            )
            )

                Box(
                    modifier = Modifier.background(

                        color = Color(
                            colorBlender(
                                colorDark = colorForeground,
                                colorLight = colorDarker,
                                fraction = fractionHandler(bottomSheetState=bottomSheetState)
                            ))
                    )
                ) {

                    Box(
                        modifier = Modifier.absoluteOffset(
                            y = (-1000 * fractionHandler(
                                bottomSheetState
                            )).dp
                        )
                    ) {
                        //    val sessionWithTask by viewModel.sessionWithTask.observeAsState()
   val currentTask by viewModel.currentTask.observeAsState("temporary task")

                        Player(
                            taskName = currentTask,
                            mainViewModel = mainViewModel,
                            colorDarker = colorDarker,
                            startTimer ={ mainViewModel.startTimer()},
                            pauseTimer = { mainViewModel.pauseTimer()},
                            turnBreak = { mainViewModel.turnBreak()}
                        )
                    }


                }
                Box(modifier= Modifier
                    .fillMaxSize()
                    .absoluteOffset(
                        y = (800 * (1 - fractionHandler(
                            bottomSheetState
                        ))).dp
                    )
                    .background(Color.Transparent)){
                    Column(verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(10.dp)){
                        Row() {
                            Text(

                                text =viewModel.totalPomodoro.value.toString(), fontSize = 120.sp,
                                fontFamily=  FontFamily(Font(
                                    R.font.retro_numbers
                                )),
                                color =totalPomodoroCountColor
                                ,modifier = Modifier,

                                )
                            Spacer(modifier = Modifier.width(15.dp))
                            Column() {
                                Text(text = "pomodoro", fontSize = 55.sp,
                                    fontFamily=  FontFamily(Font(
                                        R.font.crush
                                    )),
                                    modifier = Modifier
                                    ,color =totalPomodoroCountColor
                                )
                                Text(text = "Total", fontSize = 30.sp,
                                    fontFamily=  FontFamily(Font(
                                        R.font.crush
                                    )),
                                    modifier = Modifier
                                    ,color = totalString
                                )

                            }


                        }


                        Spacer(modifier = Modifier.height(10.dp))
                        Divider()
                        Row(horizontalArrangement = Arrangement.SpaceEvenly,modifier = Modifier.fillMaxWidth()) {
                            Text(text = "tasks", fontSize = 35.sp,
                                fontFamily=  FontFamily(Font(
                                    R.font.crush
                                )),
                                modifier = Modifier
                                ,color = tableHeaderColor
                            )
                            Text(text = "pomodoros", fontSize = 35.sp,
                                fontFamily=  FontFamily(Font(
                                    R.font.crush
                                )),
                                modifier = Modifier
                                ,color = tableHeaderColor
                            )

                        }
                      val sessionWithTask by viewModel.sessionWithTask.observeAsState()

                        sessionWithTask?.let {

                            it.forEach {

                                TaskItem(taskName = it.name, pomodoro =it.pomodoros, itemColor,onLongPress = {
                                    viewModel.deleteTaskVithSessions(it.name)
                                    mainViewModel._currentTask.value=it.name
                                    viewModel.getTasksWithPomodoros()


                                },onClick = {
                                    viewModel._currentTask.value = it.name
                                    coroutineScope.launch {
                                        bottomSheetState.collapse()




                                    }
                                })
                            }
                        }


                        Box(modifier = Modifier
                            .fillMaxWidth()
                            .padding(20.dp)
                            .height(50.dp)){



                            val stroke = Stroke(width = 4f,
                                pathEffect = PathEffect.dashPathEffect(floatArrayOf(10f, 10f), 0f)
                            )

                            Box(
                                Modifier
                                    .fillMaxSize()
                                    .clickable(

                                        onClickLabel = "asdfsdfsdf"

                                    ) {
                                        openDialog.value = true


                                    },contentAlignment = Alignment.Center){
                                Canvas(modifier = Modifier.fillMaxSize()) {
                                    drawRoundRect(color = colorForeground,style = stroke)
                                }
                                Row(

                                ){
                                    Text(
                                        textAlign = TextAlign.Center,text = "Tap here to adding new task   ",color = colorForeground)
                                    Icon(painterResource(id = R.drawable.add),"",tint = colorForeground)

                                }
                                }

                        }








                    }

                }


            }





    }



}

