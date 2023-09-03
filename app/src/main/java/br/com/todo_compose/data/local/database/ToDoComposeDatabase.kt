package br.com.todo_compose.data.local.database

import androidx.room.Database
import androidx.room.RoomDatabase
import br.com.todo_compose.data.local.dao.ToDoComposeDao
import br.com.todo_compose.data.local.entity.Task

@Database(entities = [Task::class], version = 1, exportSchema = false)
abstract class ToDoComposeDatabase : RoomDatabase() {

    abstract fun taskDao(): ToDoComposeDao

    companion object {
        const val DATABASE_NAME = "to-do-compose_db"
    }
}