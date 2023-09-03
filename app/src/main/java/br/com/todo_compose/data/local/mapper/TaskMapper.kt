package br.com.todo_compose.data.local.mapper

import br.com.todo_compose.data.local.entity.TaskEntity
import br.com.todo_compose.domain.models.TaskDomain

fun List<TaskEntity>.toListTaskDomain() = map { tasks ->
    TaskDomain(
        id = tasks.id,
        title = tasks.title,
        description = tasks.description,
        time = tasks.time,
        date = tasks.date,
        status = tasks.status
    )
}