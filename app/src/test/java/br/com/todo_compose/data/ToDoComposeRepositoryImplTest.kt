package br.com.todo_compose.data

import assertk.assertThat
import assertk.assertions.isEqualTo
import br.com.todo_compose.data.local.DataSourceLocalImpl
import br.com.todo_compose.data.local.dao.ToDoComposeDao
import br.com.todo_compose.data.model.dummyTaskEntity
import br.com.todo_compose.data.source.DataSource
import br.com.todo_compose.domain.models.TaskDomain
import br.com.todo_compose.presentation.ui.mock.dummyTaskDomain
import br.com.todo_compose.testing.BaseTest
import io.mockk.Runs
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.impl.annotations.RelaxedMockK
import io.mockk.just
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test

class ToDoComposeRepositoryImplTest : BaseTest() {

    private lateinit var repository: ToDoComposeRepositoryImpl
    private lateinit var dataSourceLocal: DataSource.Local

    @RelaxedMockK
    private lateinit var dao: ToDoComposeDao

    @Before
    fun setup() {
        dataSourceLocal = DataSourceLocalImpl(dao)
        repository = ToDoComposeRepositoryImpl(dataSourceLocal)
    }

    @Test
    fun `should insert task when is called`() = runBlocking {

        //Arrange
        coEvery {
            dataSourceLocal.insert(dummyTaskEntity)
        } just Runs

        //Act
        repository.insert(dummyTaskDomain)

        //Assert
        coVerify(exactly = 1) {
            dataSourceLocal.insert(dummyTaskEntity)
        }
    }

    @Test
    fun `should update task when is called`() = runBlocking {

        //Arrange
        coEvery {
            dataSourceLocal.update(dummyTaskEntity)
        } just Runs

        //Act
        repository.update(dummyTaskDomain)

        //Assert
        coVerify(exactly = 1) {
            dataSourceLocal.update(dummyTaskEntity)
        }
    }

    @Test
    fun `should get task all when is called`() = runBlocking {

        //Arrange
        val progressEmit: MutableList<List<TaskDomain>> = mutableListOf()

        //Act
        coEvery {
            dataSourceLocal.taskAll()
        } returns listOf(dummyTaskEntity)

        repository.taskAll().collect {
            progressEmit.add(it)
        }

        //Assert
        assertThat(progressEmit).isEqualTo(
            mutableListOf(
                listOf(dummyTaskDomain)
            )
        )
        coVerify(exactly = 1) {
            dataSourceLocal.taskAll()
        }
    }

    @Test
    fun `should delete task when is called`() = runBlocking {

        //Arrange
        val id = 1

        coEvery {
            dataSourceLocal.deleteTaskById(id)
        } just Runs

        //Act
        repository.deleteTaskById(id)

        //Assert
        coVerify(exactly = 1) {
            dataSourceLocal.deleteTaskById(id)
        }
    }

}