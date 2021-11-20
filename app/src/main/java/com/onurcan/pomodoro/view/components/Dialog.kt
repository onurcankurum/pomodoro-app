package com.onurcan.pomodoro.view.components

import android.annotation.SuppressLint
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.sp
import com.onurcan.pomodoro.R
import com.onurcan.pomodoro.model.Session
import com.onurcan.pomodoro.viewmodel.BottomSheetViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@SuppressLint("CoroutineCreationDuringComposition")
@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun Dialog(viewModel:BottomSheetViewModel,onCancel:()->Unit,onSave:()->Unit,backgroundColor:Color,borderColor:Color,saveButtonColor:Color){
    val coroutineScope = rememberCoroutineScope()
    var textFieldText by remember { mutableStateOf("") }
    val keyboardController = LocalSoftwareKeyboardController.current
    val focusRequester = remember { FocusRequester() }

    AlertDialog(
        backgroundColor=backgroundColor,
        onDismissRequest = onCancel,
        title = {

            OutlinedTextField(

                textStyle= TextStyle(color = Color.White,fontSize = 18.sp),
                value = textFieldText,

                onValueChange = {
                    textFieldText = it
                },
                label = { Text("enter your task",color =
                borderColor

                ) },
                colors = TextFieldDefaults.textFieldColors(
                    focusedIndicatorColor = borderColor, //hide the indicator
                    unfocusedIndicatorColor = borderColor,
                    cursorColor=borderColor,
                ),
                modifier= Modifier.focusRequester(focusRequester ) .onFocusChanged {
                    if (it.isFocused) {
                        keyboardController?.show()
                    }
                }

            )

        },
        text = {
            Text(
                text = "short and concise please",

                ) },

        confirmButton = {

            Button(
                colors = ButtonDefaults.buttonColors(
                    backgroundColor =saveButtonColor,
                    contentColor = Color.White),
                onClick = {

                    viewModel.insertSession(Session(textFieldText,0))
                    viewModel.getTasksWithPomodoros()
                    textFieldText=""
            onSave()



                }) {
                Text(text = "Save")
            }
        },

        dismissButton = {


            Button(

                onClick = onCancel,
                colors = ButtonDefaults.buttonColors(
                    backgroundColor = colorResource(id = R.color.dissmiss_button),
                    contentColor = Color.White),
            ) {
                Text(text = "Cancel")
            }
        }
    )
    coroutineScope.launch {
        delay(500)
        println("55555555555555555")
        focusRequester.requestFocus()
    }
    DisposableEffect(Unit) {


        onDispose {

        }
    }


}