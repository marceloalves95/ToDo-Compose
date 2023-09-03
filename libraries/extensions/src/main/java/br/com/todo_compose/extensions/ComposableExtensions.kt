package br.com.todo_compose.extensions

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.platform.LocalContext
import kotlinx.coroutines.flow.StateFlow

@Composable
inline fun <reified T> statesComposable(
    state: StateFlow<T>,
    crossinline observe: @Composable (state: T) -> Unit
){
    observe.invoke(state.collectAsState().value)
}