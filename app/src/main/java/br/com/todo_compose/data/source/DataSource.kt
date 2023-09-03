package br.com.todo_compose.data.source

import br.com.todo_compose.data.local.entity.TaskEntity

sealed interface DataSource {
    interface Local : DataSource {
        suspend fun insert(task: TaskEntity)
        suspend fun update(task: TaskEntity)
        suspend fun taskAll(): List<TaskEntity>
        suspend fun deleteTaskById(id: Int)
    }
}