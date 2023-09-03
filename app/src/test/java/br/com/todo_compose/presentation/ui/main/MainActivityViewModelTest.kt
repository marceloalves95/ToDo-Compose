package br.com.todo_compose.presentation.ui.main

import assertk.assertThat
import assertk.assertions.isEqualTo
import br.com.todo_compose.domain.model.dummyTaskDomain
import br.com.todo_compose.domain.models.TaskDomain
import br.com.todo_compose.domain.usecases.DeleteAllTaskUseCase
import br.com.todo_compose.domain.usecases.TaskAllUseCase
import br.com.todo_compose.testing.BaseTest
import io.mockk.Runs
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.confirmVerified
import io.mockk.impl.annotations.RelaxedMockK
import io.mockk.just
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.yield
import org.junit.Before
import org.junit.Test

class MainActivityViewModelTest : BaseTest() {

    private lateinit var viewModel: MainActivityViewModel

    @RelaxedMockK
    private lateinit var taskAllUseCase: TaskAllUseCase

    @RelaxedMockK
    private lateinit var deleteAllTaskUseCase: DeleteAllTaskUseCase

    @Before
    fun setup() {
        viewModel = MainActivityViewModel(
            taskAllUseCase = taskAllUseCase,
            deleteAllTaskUseCase = deleteAllTaskUseCase
        )
    }

    @Test
    fun `should load all tasks when it is called`() = runBlocking {

        //Arrange
        val state = MutableStateFlow<List<TaskDomain>>(emptyList())
        val result = mutableListOf<TaskDomain>()
        val emitJob: Job = launch {
            state.emit(result)
        }

        launch {
            state.tryEmit(emptyList())
            state.tryEmit(listOf(dummyTaskDomain))
            yield()
            emitJob.cancel()
        }

        coEvery {
            taskAllUseCase.invoke()
        } returns flow {
            emit(listOf(dummyTaskDomain))
        }

        //Act
        viewModel.taskAll()

        //Assert
        assertThat(viewModel.state.value).isEqualTo(listOf(dummyTaskDomain))
        coVerify(exactly = 1) {
            taskAllUseCase.invoke()
        }
        confirmVerified(taskAllUseCase)
    }

    @Test
    fun `should delete all tasks when it is called`() = runBlocking {

        //Arrange
        coEvery {
            deleteAllTaskUseCase.invoke(ID)
        } just Runs

        //Act
        viewModel.deleteTaskById(ID)

        //Assert
        coVerify(exactly = 1) {
            deleteAllTaskUseCase.invoke(ID)
        }
        confirmVerified(deleteAllTaskUseCase)
    }

    companion object {
        private const val ID = 1
    }
}