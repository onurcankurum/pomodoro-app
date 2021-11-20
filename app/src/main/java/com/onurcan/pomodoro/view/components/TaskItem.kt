package com.onurcan.pomodoro.view.components

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.onurcan.pomodoro.R


@OptIn(ExperimentalFoundationApi::class)
@Composable
fun TaskItem(taskName:String,pomodoro:Int,color: Color,onClick:()->Unit,onLongPress:()->Unit){


    var isDeleteMenuExpanded by remember { mutableStateOf(false) }

    Box(modifier = Modifier.combinedClickable(
        onClick = {

            onClick()
                  },
        onLongClick = {
            isDeleteMenuExpanded=true
         // onLongPress()
        }
            ))
            {
                MyDropdownMenu(isExapanded = isDeleteMenuExpanded,deletFun = onLongPress,dismissFun = {isDeleteMenuExpanded=false})




                Row(horizontalArrangement = Arrangement.SpaceEvenly,modifier = Modifier
                    .fillMaxWidth()
                    .padding(15.dp), verticalAlignment = Alignment.CenterVertically) {
                    Spacer(modifier = Modifier.weight(0.2f))
                    Box(modifier = Modifier.weight(1f)){
                        Text(text = taskName, fontSize = 25.sp,
                            fontFamily=  FontFamily(
                                Font(
                                    R.font.crush
                                )
                            ),
                            modifier = Modifier
                            ,color = color
                        )

                    }
                    Spacer(modifier = Modifier.weight(0.5f))

                    Box(modifier = Modifier.weight(1f)){
                        Text(text = pomodoro.toString(), fontSize = 25.sp,
                            fontFamily=  FontFamily(
                                Font(
                                    R.font.crush
                                )
                            ),
                            modifier = Modifier
                            ,color = color
                        )

                    }



                }

            }
        }



