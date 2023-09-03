package br.com.todo_compose.extensions

import android.app.Activity
import android.widget.Toast
import androidx.activity.ComponentActivity

inline fun <reified T> Activity.extra(key: String): Lazy<T> = lazy {
    val value = intent.extras?.get(key)
    if (value is T) value else throw IllegalArgumentException("didn't find extra with key $key")
}

fun Activity.showToast(message: String, duration: Int = Toast.LENGTH_LONG) =
    Toast.makeText(this, message, duration).show()

fun ComponentActivity.onBackButtonPressed(){
    onBackPressedDispatcher.onBackPressed()
}