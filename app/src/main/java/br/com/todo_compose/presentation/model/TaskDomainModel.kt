package br.com.todo_compose.presentation.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class TaskDomainModel(
    val id: Int = 0,
    val title: String,
    val description: String,
    val time: String,
    val date: String,
    val status: String
) : Parcelable
