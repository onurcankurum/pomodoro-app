package com.onurcan.pomodoro.viewmodel

import android.app.Application
import android.media.MediaPlayer
import android.os.CountDownTimer
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.onurcan.pomodoro.R
import com.onurcan.pomodoro.helper.PomodoroDatabase
import com.onurcan.pomodoro.model.Session
import com.onurcan.pomodoro.utils.Utility
import com.onurcan.pomodoro.utils.Utility.formatTime
import kotlinx.coroutines.launch
import kotlin.random.Random


class MainViewModel(application:Application): CoroutinesBase(application ) {

    //region Properties
    private var countDownTimer: CountDownTimer? = null

    private val breakMessages = listOf<Int>(com.onurcan.pomodoro.R.string.rest,
        com.onurcan.pomodoro.R.string.rest2,
        com.onurcan.pomodoro.R.string.rest3,
        com.onurcan.pomodoro.R.string.rest4)

    private val focusMessages = listOf<Int>(com.onurcan.pomodoro.R.string.work,
        com.onurcan.pomodoro.R.string.work2,
        com.onurcan.pomodoro.R.string.work3,
        com.onurcan.pomodoro.R.string.work4)

    private val beforeFocusMessages = listOf<Int>(com.onurcan.pomodoro.R.string.uready,
        com.onurcan.pomodoro.R.string.uready2,
        com.onurcan.pomodoro.R.string.uready3,
        com.onurcan.pomodoro.R.string.uready4)




    val _currentTask = MutableLiveData("no name task")
    val currentTask: LiveData<String> = _currentTask

    private val _time = MutableLiveData(Utility.TIME_COUNTDOWNLONG.formatTime())
    val time: LiveData<String> = _time

    private val
            _message= MutableLiveData(com.onurcan.pomodoro.R.string.uready)
    val message: LiveData<Int> = _message



    private val _isBreak = MutableLiveData(false)
    val isBreak: LiveData<Boolean> = _isBreak

    private val _progress = MutableLiveData(1.00F)
    val progress: LiveData<Float> = _progress

    private val _isPlaying = MutableLiveData(false)
    val isPlaying: LiveData<Boolean> = _isPlaying


    private var remainingTime:Long? = null
    private var _progressValue: Float=1f

    private val _colorBackground = MutableLiveData( com.onurcan.pomodoro.R.color.darkpurple)
    val colorBackground: LiveData<Int> = _colorBackground

    private val _colorDarker = MutableLiveData( com.onurcan.pomodoro.R.color.darkerpurple)
    val colorDarker: LiveData<Int> = _colorDarker

    private val _colorForeground = MutableLiveData( com.onurcan.pomodoro.R.color.ligthpurple)
    val colorForeground: LiveData<Int> = _colorForeground
    //endregion

    //region Public methods
    fun turnBreak(){
       if(_isBreak.value ==false){
            _isPlaying.value=false
            _isBreak.value =true
            _colorBackground.value=com.onurcan.pomodoro.R.color.darkteal
            _colorForeground.value=com.onurcan.pomodoro.R.color.lightteal
            _colorDarker.value=com.onurcan.pomodoro.R.color.darkerteal
            _message.value= breakMessages[Random.nextInt(1,4)]
            countDownTimer?.cancel()
            remainingTime=Utility.TIME_COUNTDOWNSHORT
            handleTimerValues(false, Utility.TIME_COUNTDOWNSHORT.formatTime(), 1.0F)
        }else{
            _isPlaying.value=false
            _colorBackground.value=com.onurcan.pomodoro.R.color.darkpurple
            _colorForeground.value=com.onurcan.pomodoro.R.color.ligthpurple
            _colorDarker.value=com.onurcan.pomodoro.R.color.darkerpurple
            _message.value= beforeFocusMessages[Random.nextInt(0,4)]
            countDownTimer?.cancel()
            remainingTime=Utility.TIME_COUNTDOWNLONG
            _isBreak.value =false
            handleTimerValues(false, Utility.TIME_COUNTDOWNLONG.formatTime(), 1.0F)

        }

   }
    fun handleCountDownTimer() {
        if (isPlaying.value == true) {
            pauseTimer()
        } else {
            if(_isBreak.value==false){

            }
            startTimer()
        }
    }


     fun pauseTimer() {
       _isPlaying.value=false
        countDownTimer?.cancel()
        if(isBreak.value==true){
            _progressValue = remainingTime!!.toFloat() / Utility.TIME_COUNTDOWNSHORT
        }
        else{
            _progressValue = (remainingTime!!.toFloat() / Utility.TIME_COUNTDOWNLONG)
        }
        handleTimerValues(false, remainingTime!!.formatTime(), _progressValue!!.toFloat())
    }

     fun startTimer() {
        _isPlaying.value = true
        val remaining :Long?
        if(remainingTime==null){
            if(isBreak.value==true){
                remaining = Utility.TIME_COUNTDOWNSHORT
            }else{
                remaining = Utility.TIME_COUNTDOWNLONG
            }
        }
         else{
             remaining = remainingTime
        }
         if(isBreak.value==false){
             _message.value=focusMessages[Random.nextInt(0,4)]
         }
        countDownTimer = object : CountDownTimer(remaining!!, 1000) {

            override fun onTick(millisRemaining: Long) {
                var progressValue :Float?=null
                if(isBreak.value==true){
                     progressValue = millisRemaining.toFloat() / Utility.TIME_COUNTDOWNSHORT
                }else{
                     progressValue = millisRemaining.toFloat() / Utility.TIME_COUNTDOWNLONG
                }
                remainingTime = millisRemaining
                handleTimerValues(true, millisRemaining.formatTime(), progressValue!!)
            }

            override fun onFinish() {

                var med = MediaPlayer.create(getApplication(), R.raw.long_bell)
                med.start()
                if(isBreak.value==false){
                    launch {
                        val dao = PomodoroDatabase(getApplication()).pomodoroDao().insertSession(
                            Session(_currentTask.value!!,1)
                        )
                    }
                }
                turnBreak()
                pauseTimer()
            }
        }.start()
    }
    //region private methods
    private fun handleTimerValues(isPlaying: Boolean, text: String, progress: Float) {
        _isPlaying.value = isPlaying
        _time.value = text
        _progress.value = progress
    }

}