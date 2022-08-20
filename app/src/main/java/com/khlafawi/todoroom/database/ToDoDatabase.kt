package com.khlafawi.todoroom.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.khlafawi.todoroom.model.ToDoItem

//TODO: STEP(4) add ToDoDatabase (the database class)
@Database(entities = [ToDoItem::class], version = 1, exportSchema = false)
abstract class ToDoDatabase : RoomDatabase() {
    abstract val todoDataBaseDao: ToDoDatabaseDao

    companion object {

        @Volatile
        private var INSTANCE: ToDoDatabase? = null

        fun getInstance(context: Context): ToDoDatabase {
            synchronized(this) {
                var instance = INSTANCE

                if (instance == null) {
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        ToDoDatabase::class.java,
                        "todos_database"
                    ).fallbackToDestructiveMigration().build()
                    INSTANCE = instance
                }
                return instance
            }
        }
    }
}
//TODO: STEP(4) add ToDoDatabase (the database class)