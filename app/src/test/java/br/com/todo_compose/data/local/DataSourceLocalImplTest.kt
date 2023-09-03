package br.com.todo_compose.data.local

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import assertk.assertThat
import assertk.assertions.contains
import assertk.assertions.isEqualTo
import br.com.todo_compose.data.local.dao.ToDoComposeDao
import br.com.todo_compose.data.local.database.ToDoComposeDatabase
import br.com.todo_compose.data.model.dummyTaskEntity
import br.com.todo_compose.domain.mapper.toTaskEntity
import br.com.todo_compose.domain.model.dummyTaskDomain
import br.com.todo_compose.testing.BaseTest
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner

@RunWith(RobolectricTestRunner::class)
class DataSourceLocalImplTest : BaseTest() {

    private lateinit var dataSourceLocal: DataSourceLocalImpl
    private lateinit var database: ToDoComposeDatabase
    private lateinit var dao: ToDoComposeDao

    @Before
    fun setup() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        database = Room.inMemoryDatabaseBuilder(context, ToDoComposeDatabase::class.java)
            .allowMainThreadQueries().build()
        dao = database.taskDao()
        dataSourceLocal = DataSourceLocalImpl(dao)
    }

    @After
    fun tearDown() {
        database.close()
    }

    @Test
    fun `should insert task when it is called`() = runBlocking {
        dataSourceLocal.insert(dummyTaskDomain.toTaskEntity())
        assertThat(dataSourceLocal.taskAll()).contains(dummyTaskEntity)
    }

    @Test
    fun `should update task when it is called`() = runBlocking {
        dataSourceLocal.insert(dummyTaskDomain.toTaskEntity())
        dataSourceLocal.update(dummyTaskEntity)
        assertThat(dataSourceLocal.taskAll()).contains(dummyTaskEntity)
    }


    @Test
    fun `should delete task when it is called`() = runBlocking {
        dataSourceLocal.insert(dummyTaskEntity)
        dataSourceLocal.deleteTaskById(dummyTaskEntity.id)
        assertThat(dataSourceLocal.taskAll()).isEqualTo(emptyList())
    }

}