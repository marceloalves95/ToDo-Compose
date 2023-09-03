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

class AddTaskUseCaseTest : BaseTest() {

    @RelaxedMockK
    private lateinit var repository: ToDoComposeRepository
    private lateinit var addTaskUseCase: AddTaskUseCase

    @Before
    fun setup() {
        addTaskUseCase = AddTaskUseCase(repository)
    }

    @Test
    fun `should add task when it is called`() = runBlocking {

        //Arrange
        coEvery {
            repository.insert(dummyTaskDomain)
        } just Runs

        //Act
        addTaskUseCase.invoke(dummyTaskDomain)

        //Assert
        coVerify(exactly = 1) {
            repository.insert(dummyTaskDomain)
        }
        confirmVerified(repository)
    }

}