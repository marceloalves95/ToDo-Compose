package br.com.todo_compose.domain.models

typealias TaskDomain = Task

data class Task(
    val id: Int,
    val title: String,
    val description: String,
    val time: String,
    val date: String,
    val status: String
)
