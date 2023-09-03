package br.com.todo_compose.domain.usecases

import br.com.todo_compose.domain.models.TaskDomain
import br.com.todo_compose.domain.repository.ToDoComposeRepository

class UpdateTaskUseCase(
    private val repository: ToDoComposeRepository
) {
    suspend operator fun invoke(taskDomain: TaskDomain) = repository.update(taskDomain)
}