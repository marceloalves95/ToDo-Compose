package br.com.todo_compose.domain.model

import br.com.todo_compose.domain.models.TaskDomain

val dummyTaskDomain = TaskDomain(
    id = 1,
    title = "Academia",
    description = "Ir para a academia",
    time = "03:00",
    date = "26/08/2023",
    status = "To Do"
)