package br.com.todo_compose.domain.mapper

import br.com.todo_compose.data.local.entity.TaskEntity
import br.com.todo_compose.domain.models.TaskDomain
import br.com.todo_compose.presentation.model.TaskDomainModel

internal fun TaskDomain.toTaskEntity() = TaskEntity(
    id = id,
    title = title,
    description = description,
    time = time,
    date = date,
    status = status
)

internal fun TaskDomain.toTaskDomainModel() = TaskDomainModel(
    id = id,
    title = title,
    description = description,
    time = time,
    date = date,
    status = status
)