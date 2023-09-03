package br.com.todo_compose.data.model

import br.com.todo_compose.data.local.entity.TaskEntity

val dummyTaskEntity = TaskEntity(
    id = 1,
    title = "Academia",
    description = "Ir para a academia",
    time = "03:00",
    date = "26/08/2023",
    status = "To Do"
)