package com.khlafawi.todoroom.helpers

import com.khlafawi.todoroom.model.ToDoItem

class OnToDoItemClickedListener(val clickListener: (todoItem: ToDoItem) -> Unit) {
    fun onClick(todoItem: ToDoItem) = clickListener(todoItem)
}