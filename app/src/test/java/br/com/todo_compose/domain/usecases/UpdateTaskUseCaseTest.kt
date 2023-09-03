package br.com.todo_compose.domain.usecases

import br.com.todo_compose.domain.repository.ToDoComposeRepository
import br.com.todo_compose.presentation.ui.mock.dummyTaskDomain
import br.com.todo_compose.testing.BaseTest
import io.mockk.Runs
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.confirmVerified
import io.mockk.impl.annotations.RelaxedMockK
import io.mockk.just
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test

class UpdateTaskUseCaseTest : BaseTest() {

    @RelaxedMockK
    private lateinit var repository: ToDoComposeRepository
    private lateinit var updateTaskUseCase: UpdateTaskUseCase

    @Before
    fun setup() {
        updateTaskUseCase = UpdateTaskUseCase(repository)
    }

    @Test
    fun `should update task when it is called`() = runBlocking {

        //Arrange
        coEvery {
            repository.update(dummyTaskDomain)
        } just Runs

        //Act
        updateTaskUseCase.invoke(dummyTaskDomain)

        //Assert
        coVerify(exactly = 1) {
            repository.update(dummyTaskDomain)
        }
        confirmVerified(repository)
    }

}