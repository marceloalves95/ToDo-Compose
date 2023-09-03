package br.com.todo_compose.presentation.ui.addAndAlter

import androidx.lifecycle.ViewModel
import br.com.todo_compose.domain.models.TaskDomain
import br.com.todo_compose.domain.usecases.AddTaskUseCase
import br.com.todo_compose.domain.usecases.UpdateTaskUseCase
import br.com.todo_compose.extensions.launch

class AddAndAlterTaskViewModel(
    private val addTaskUseCase: AddTaskUseCase,
    private val updateTaskUseCase: UpdateTaskUseCase
) : ViewModel() {

    fun addTask(taskDomain: TaskDomain) = launch {
        addTaskUseCase.invoke(taskDomain)
    }

    fun updateTask(taskDomain: TaskDomain) = launch {
        updateTaskUseCase.invoke(taskDomain)
    }
}