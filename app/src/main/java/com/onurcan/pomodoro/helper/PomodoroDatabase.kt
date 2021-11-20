package com.onurcan.pomodoro.helper



import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.onurcan.pomodoro.model.Session


@Database(entities = arrayOf(Session::class),version = 2)
abstract class PomodoroDatabase :RoomDatabase(){

    abstract fun pomodoroDao(): com.onurcan.pomodoro.helper.PomodoroDao


    companion object{
        @Volatile private var instance : com.onurcan.pomodoro.helper.PomodoroDatabase?=null

        private val lock = Any()

        operator fun invoke (context: Context)= com.onurcan.pomodoro.helper.PomodoroDatabase.Companion.instance
            ?: synchronized(com.onurcan.pomodoro.helper.PomodoroDatabase.Companion.lock){
            com.onurcan.pomodoro.helper.PomodoroDatabase.Companion.instance
                ?: com.onurcan.pomodoro.helper.PomodoroDatabase.Companion.makeDatabase(context).also{

                com.onurcan.pomodoro.helper.PomodoroDatabase.Companion.instance = it
            }
        }

        private fun makeDatabase(context: Context) = Room.databaseBuilder(
            context.applicationContext, com.onurcan.pomodoro.helper.PomodoroDatabase::class.java,"gameDatabase"
        ).build()


    }

}