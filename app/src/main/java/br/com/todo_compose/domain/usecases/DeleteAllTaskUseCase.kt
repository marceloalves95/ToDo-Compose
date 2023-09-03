package br.com.todo_compose.domain.usecases

import br.com.todo_compose.domain.repository.ToDoComposeRepository

class DeleteAllTaskUseCase(
    private val repository: ToDoComposeRepository
) {
    suspend fun invoke(id: Int) = repository.deleteTaskById(id)
}
