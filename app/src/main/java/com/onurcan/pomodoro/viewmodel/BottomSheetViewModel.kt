package com.onurcan.pomodoro.viewmodel

import android.app.Application
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.onurcan.pomodoro.helper.PomodoroDatabase
import com.onurcan.pomodoro.model.Session
import com.onurcan.pomodoro.model.TaskSum
import kotlinx.coroutines.launch

class BottomSheetViewModel(application:Application): CoroutinesBase(application )  {

    private val _message= MutableLiveData(com.onurcan.pomodoro.R.string.uready)
    var message: LiveData<Int> = _message

    val _totalPomodoro= MutableLiveData(0)
    var totalPomodoro: LiveData<Int> = _totalPomodoro

    val _currentTask= MutableLiveData("")
    var currentTask: LiveData<String> = _currentTask

    private val _sessionWithTask= MutableLiveData(listOf(TaskSum("all tasks",0)))
    var sessionWithTask: LiveData<List<TaskSum>> = _sessionWithTask



    fun deleteTaskVithSessions(taskName:String){
        launch {
            val dao = PomodoroDatabase(getApplication()).pomodoroDao().deleteAllsessions(taskName)
            Toast.makeText(getApplication(), taskName+" was deleted", Toast.LENGTH_SHORT).show()
        }
    }

    fun getTasksWithPomodoros(){
        launch {
            val dao = PomodoroDatabase(getApplication()).pomodoroDao().getTasksWithPomodoros()
            if(dao.size!=0){
                _totalPomodoro.value=0
                _sessionWithTask.value=dao
                dao.forEach {
                    _totalPomodoro.value=it.pomodoros+ _totalPomodoro.value!!
                }
                if(_currentTask.value==""){
                    _currentTask.value=dao.last().name
                }
            }
            else{
                _currentTask.value="temporary task"
                _sessionWithTask.value= listOf(TaskSum("temporary task",0))
            }

        }
    }
    fun insertSession(session: Session){
        launch {
            val dao = PomodoroDatabase(getApplication()).pomodoroDao().insertSession(session)
        }
    }

}