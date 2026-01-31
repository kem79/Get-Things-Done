package com.marcal.getthingsdone.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [Task::class], version = 1)
abstract class GetThingsDoneDatabase : RoomDatabase() {
    abstract fun getTaskDao(): TaskDao

    companion object {
        fun createDatabase(context: Context): GetThingsDoneDatabase {
            return Room.databaseBuilder(
                context,
                GetThingsDoneDatabase::class.java,
                "get-things-done-database"
            ).build()
        }
    }
}