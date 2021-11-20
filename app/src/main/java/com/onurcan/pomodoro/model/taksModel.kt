package com.onurcan.pomodoro.model

import androidx.room.*

@Entity
data class Session(
    @ColumnInfo(name = "name")
    val taskName:String,

    @ColumnInfo(name = "pomodoro")
    val pomodoro:Int,

){
    @PrimaryKey(autoGenerate = true)
    var id:Int=0
}

data class TaskSum ( val name:String,val pomodoros:Int,)

data class SessionWithTask(
    val sessions: List<Session>
)