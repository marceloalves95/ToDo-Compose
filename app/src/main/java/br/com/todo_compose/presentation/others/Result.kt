package br.com.todo_compose.presentation.others

sealed class Result<out T> {
    data class Add<T>(val data: T) : Result<T>()
    data class Update<T>(val data: T) : Result<T>()
    data class Delete<T>(val data: Unit) : Result<T>()
    data class All<T>(val data: List<T>) : Result<T>()

    companion object {
        fun <T> add(data: T): Result<T> = Add(data)
        fun <T> update(data: T): Result<T> = Update(data)
        fun <T> delete(data: Unit): Result<T> = Delete(data)
        fun <T> all(data: List<T>): Result<T> = All(data)
    }
}