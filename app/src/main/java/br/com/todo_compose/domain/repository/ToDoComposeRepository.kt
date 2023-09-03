package br.com.todo_compose.domain.repository

import br.com.todo_compose.domain.models.TaskDomain
import kotlinx.coroutines.flow.Flow

interface ToDoComposeRepository {
    suspend fun insert(task: TaskDomain)
    suspend fun update(task: TaskDomain)
    fun taskAll(): Flow<List<TaskDomain>>
    suspend fun deleteTaskById(id: Int)
}