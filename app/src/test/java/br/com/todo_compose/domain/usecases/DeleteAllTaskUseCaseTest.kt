package br.com.todo_compose.domain.usecases

import br.com.todo_compose.domain.repository.ToDoComposeRepository
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

class DeleteAllTaskUseCaseTest : BaseTest() {

    @RelaxedMockK
    private lateinit var repository: ToDoComposeRepository
    private lateinit var deleteAllTaskUseCase: DeleteAllTaskUseCase

    @Before
    fun setup() {
        deleteAllTaskUseCase = DeleteAllTaskUseCase(repository)
    }

    @Test
    fun `should delete task by id when it is called`() = runBlocking {

        //Arrange
        coEvery {
            repository.deleteTaskById(ID)
        } just Runs

        //Act
        deleteAllTaskUseCase.invoke(ID)

        //Assert
        coVerify(exactly = 1) {
            repository.deleteTaskById(ID)
        }
        confirmVerified(repository)
    }

    companion object {
        private const val ID = 1
    }

}