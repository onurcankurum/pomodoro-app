package com.onurcan.pomodoro.view.components

import androidx.compose.material.DropdownMenuItem
import androidx.compose.material.Text
import androidx.compose.runtime.*

@Composable
fun MyDropdownMenu(isExapanded:Boolean,deletFun:()->Unit,dismissFun:()->Unit) {

    androidx.compose.material.DropdownMenu(

        expanded = isExapanded,
        onDismissRequest = dismissFun,
    ) {
        val menuItems: List<String> = listOf("delete this")
        menuItems.forEachIndexed { index, item ->
            DropdownMenuItem(onClick = {deletFun()}) {
                Text(text = item)
            }
        }
    }
}