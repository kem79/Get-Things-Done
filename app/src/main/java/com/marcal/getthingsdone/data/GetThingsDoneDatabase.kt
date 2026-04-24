package com.marcal.getthingsdone.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [Task::class], version = 3)
abstract class GetThingsDoneDatabase : RoomDatabase() {
    abstract fun getTaskDao(): TaskDao


    companion object {
        private var DATABASE_INSTANCE: GetThingsDoneDatabase? = null

        fun getDatabase(context: Context): GetThingsDoneDatabase {

            return DATABASE_INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context,
                    GetThingsDoneDatabase::class.java,
                    "get-things-done-database"
                )
                    .fallbackToDestructiveMigration(true)
                    .build()
                DATABASE_INSTANCE = instance
                instance
            }
        }
    }
}