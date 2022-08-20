package com.khlafawi.todoroom

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.khlafawi.todoroom.database.ToDoDatabase
import com.khlafawi.todoroom.model.ToDoItem

class MainViewModel(application: Application) : AndroidViewModel(application) {

    private val tag = MainViewModel::class.java.simpleName
    private val database by lazy { ToDoDatabase.getInstance(application).todoDataBaseDao }
    val allItemsSorted = database.getAllTodosByPriority()

    private val _navigateToTodoItemDetails = MutableLiveData<ToDoItem?>()
    val navigateToTodoItemDetails
        get() = _navigateToTodoItemDetails

    init {
        Log.i(tag, "init()")
    }

    override fun onCleared() {
        Log.i(tag, "onCleared()")
        super.onCleared()
    }

    fun addNewTodoItem(title: String, priority: Int) {
        Log.i(tag, "addNewTodoItem($title, $priority)")
        Thread {
            database.insert(ToDoItem(title = title, priority = priority))
        }.start()
    }

    fun updateCurrentItem(id: Long, newTitle: String, newPriority: Int) {
        Log.i(tag, "updateCurrentItem($id, $newTitle, $newPriority)")
        Thread {
            database.update(ToDoItem(id = id, title = newTitle, priority = newPriority))
        }.start()
    }

    fun onToDoItemClicked(todoItem: ToDoItem) {
        _navigateToTodoItemDetails.value = todoItem
    }

    fun onToDoDetailsDetailsNavigated() {
        _navigateToTodoItemDetails.value = null
    }

    fun deleteCurrentItem(todoItem: ToDoItem) {
        Thread {
            database.delete(todoItem)
        }.start()
    }
}