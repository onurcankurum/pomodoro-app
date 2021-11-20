package com.onurcan.pomodoro.helper


import androidx.room.*
import com.onurcan.pomodoro.model.Session

import com.onurcan.pomodoro.model.TaskSum


@Dao
interface PomodoroDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertSession(session: Session)

    @Transaction
    @Query("SELECT name,SUM(pomodoro) AS pomodoros FROM Session GROUP BY name")
    suspend fun getTasksWithPomodoros(): List<TaskSum>

    @Query("DELETE FROM session WHERE name = :taskName")
   suspend fun deleteAllsessions(taskName: String)


}