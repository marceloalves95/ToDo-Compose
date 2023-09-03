package br.com.todo_compose.domain.usecases

import br.com.todo_compose.domain.repository.ToDoComposeRepository
import br.com.todo_compose.presentation.ui.mock.dummyTaskDomain
import br.com.todo_compose.testing.BaseTest
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.impl.annotations.RelaxedMockK
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test

class TaskAllUseCaseTest : BaseTest() {

    @RelaxedMockK
    private lateinit var repository: ToDoComposeRepository
    private lateinit var taskAllUseCase: TaskAllUseCase

    @Before
    fun setup() {
        taskAllUseCase = TaskAllUseCase(repository)
    }

    @Test
    fun `should task all when it is called`() = runBlocking {

        //Arrange
        coEvery {
            repository.taskAll()
        } returns flow {
            emit(listOf(dummyTaskDomain))
        }

        //Act
        taskAllUseCase.invoke().collect {}

        //Assert
        coVerify(exactly = 1) {
            repository.taskAll()
        }
    }

}