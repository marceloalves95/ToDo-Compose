package br.com.todo_compose.data

import br.com.todo_compose.data.local.mapper.toListTaskDomain
import br.com.todo_compose.data.source.DataSource
import br.com.todo_compose.domain.mapper.toTaskEntity
import br.com.todo_compose.domain.models.TaskDomain
import br.com.todo_compose.domain.repository.ToDoComposeRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class ToDoComposeRepositoryImpl(
    private val dataSourceLocal: DataSource.Local
) : ToDoComposeRepository {

    override suspend fun insert(task: TaskDomain) {
        dataSourceLocal.insert(task.toTaskEntity())
    }

    override suspend fun update(task: TaskDomain) {
        dataSourceLocal.update(task.toTaskEntity())
    }

    override fun taskAll(): Flow<List<TaskDomain>> = flow {
        emit(dataSourceLocal.taskAll().toListTaskDomain())
    }

    override suspend fun deleteTaskById(id: Int) {
        dataSourceLocal.deleteTaskById(id)
    }
}