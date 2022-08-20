# Save data in a local database using Room

Room is a part of the Android Jetpack, this tutorial is a Todo list android application to show how
to setup Room, how to create a new DAO and how to deal with local database

![alt text](https://miro.medium.com/max/600/1*jT94pc71uD_A2TPN_E2ulg.png)

## Installation

Add Room dependency

```groovy
    def room_version = "2.5.0-alpha02"

implementation "androidx.room:room-ktx:$room_version"
implementation "androidx.room:room-runtime:$room_version"
kapt "androidx.room:room-compiler:$room_version"
```

## Setting up

Create ToDoDatabaseDao (Database Access Object)

```kotlin
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
```

And then create Database class

```kotlin
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
```