package br.com.todo_compose.domain.usecases

import br.com.todo_compose.domain.models.TaskDomain
import br.com.todo_compose.domain.repository.ToDoComposeRepository
import kotlinx.coroutines.flow.Flow

class TaskAllUseCase(
    private val repository: ToDoComposeRepository
) {
    fun invoke(): Flow<List<TaskDomain>> = repository.taskAll()
}