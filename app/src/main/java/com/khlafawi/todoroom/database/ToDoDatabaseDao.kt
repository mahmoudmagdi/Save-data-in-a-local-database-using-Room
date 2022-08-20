package com.khlafawi.todoroom.database

import androidx.lifecycle.LiveData
import androidx.room.*
import com.khlafawi.todoroom.model.ToDoItem

//TODO: STEP(3) add ToDoDatabaseDao (Database Access Object)
@Dao
interface ToDoDatabaseDao {

    @Insert
    fun insert(todoItem: ToDoItem)

    @Query("SELECT * FROM todo_items ORDER BY id DESC")
    fun getAllTodos(): LiveData<List<ToDoItem>>

    @Query("SELECT * FROM todo_items ORDER BY priority ASC")
    fun getAllTodosByPriority(): LiveData<List<ToDoItem>>

    @Update
    fun update(todoItem: ToDoItem)

    @Query("SELECT * FROM todo_items WHERE id = :key")
    fun get(key: Long): ToDoItem?

    @Query("DELETE FROM todo_items")
    fun clear()

    @Delete
    fun delete(todoItem: ToDoItem)
}
//TODO: STEP(3) add ToDoDatabaseDao (Database Access Object)