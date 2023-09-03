package br.com.todo_compose.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import br.com.todo_compose.data.local.entity.Task

@Dao
interface ToDoComposeDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(task: Task)

    @Update
    suspend fun update(task: Task)

    @Query("SELECT * FROM task")
    suspend fun taskAll(): List<Task>

    @Query("DELETE FROM task WHERE id = :id")
    suspend fun deleteTaskById(id: Int)
}