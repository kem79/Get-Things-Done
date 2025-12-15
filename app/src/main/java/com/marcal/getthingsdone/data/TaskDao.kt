package com.marcal.getthingsdone.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

@Dao
interface TaskDao {

    @Insert
    fun createTask(task : Task)

    @Query("SELECT * from Task")
    fun getAllTasks(): List<Task>

    @Update
    fun updateTask(task : Task)

    @Delete
    fun deleteTask(task : Task)
}