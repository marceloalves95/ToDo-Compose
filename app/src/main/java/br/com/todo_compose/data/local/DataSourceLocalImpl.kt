package br.com.todo_compose.data.local

import br.com.todo_compose.data.local.dao.ToDoComposeDao
import br.com.todo_compose.data.local.entity.Task
import br.com.todo_compose.data.source.DataSource

class DataSourceLocalImpl(
    private val dao: ToDoComposeDao
) : DataSource.Local {
    override suspend fun insert(task: Task) = dao.insert(task)
    override suspend fun update(task: Task) = dao.update(task)
    override suspend fun taskAll(): List<Task> = dao.taskAll()
    override suspend fun deleteTaskById(id: Int) = dao.deleteTaskById(id)
}