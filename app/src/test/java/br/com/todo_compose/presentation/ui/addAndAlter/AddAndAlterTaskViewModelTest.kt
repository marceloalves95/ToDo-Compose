package br.com.todo_compose.presentation.ui.addAndAlter

import br.com.todo_compose.domain.usecases.AddTaskUseCase
import br.com.todo_compose.domain.usecases.UpdateTaskUseCase
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

class AddAndAlterTaskViewModelTest : BaseTest() {

    private lateinit var viewModel: AddAndAlterTaskViewModel

    @RelaxedMockK
    private lateinit var addTaskUseCase: AddTaskUseCase

    @RelaxedMockK
    private lateinit var updateTaskUseCase: UpdateTaskUseCase

    @Before
    fun setup() {
        viewModel = AddAndAlterTaskViewModel(
            addTaskUseCase = addTaskUseCase,
            updateTaskUseCase = updateTaskUseCase
        )
    }

    @Test
    fun `should add task when it is called`() = runBlocking {

        //Arrange
        coEvery {
            addTaskUseCase.invoke(dummyTaskDomain)
        } just Runs

        //Act
        viewModel.addTask(dummyTaskDomain)

        //Assert
        coVerify(exactly = 1) {
            addTaskUseCase.invoke(dummyTaskDomain)
        }
        confirmVerified(addTaskUseCase)
    }

    @Test
    fun `should update task when it is called`() = runBlocking {

        //Arrange
        coEvery {
            updateTaskUseCase.invoke(dummyTaskDomain)
        } just Runs

        //Act
        viewModel.updateTask(dummyTaskDomain)

        //Assert
        coVerify(exactly = 1) {
            updateTaskUseCase.invoke(dummyTaskDomain)
        }
        confirmVerified(updateTaskUseCase)
    }

}