package br.com.todo_compose.presentation.ui.main

import androidx.lifecycle.ViewModel
import br.com.todo_compose.domain.models.TaskDomain
import br.com.todo_compose.domain.usecases.DeleteAllTaskUseCase
import br.com.todo_compose.domain.usecases.TaskAllUseCase
import br.com.todo_compose.extensions.launch
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class MainActivityViewModel(
    private val taskAllUseCase: TaskAllUseCase,
    private val deleteAllTaskUseCase: DeleteAllTaskUseCase
) : ViewModel() {

    private val _state = MutableStateFlow<List<TaskDomain>>(emptyList())
    val state: StateFlow<List<TaskDomain>> get() = _state

    fun taskAll() = launch {
        taskAllUseCase.invoke().collect { result ->
            _state.value = result
        }
    }

    fun deleteTaskById(id: Int) = launch {
        deleteAllTaskUseCase.invoke(id)
    }

}