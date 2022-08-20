package com.khlafawi.todoroom.model

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize

//TODO: STEP(2) add ToDoItem Entity
@Entity(tableName = "todo_items")
@Parcelize
data class ToDoItem(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,

    @ColumnInfo(name = "title")
    var title: String = "",

    @ColumnInfo(name = "priority")
    var priority: Int = 0,
) : Parcelable
//TODO: STEP(2) add ToDoItem Entity