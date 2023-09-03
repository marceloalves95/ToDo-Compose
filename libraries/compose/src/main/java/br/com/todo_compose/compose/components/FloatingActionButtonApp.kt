package br.com.todo_compose.compose.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material.icons.rounded.Delete
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import br.com.todo_compose.compose.others.UIModePreviews
import br.com.todo_compose.compose.theme.ThemeApp

@Composable
fun FloatingActionButtonApp(
    isInitialButton: Boolean,
    showDialog: Boolean,
    floatingActionOnClick: () -> Unit
) {
    var dialog by remember { mutableStateOf(showDialog) }

    val icon = if (isInitialButton) {
        Icons.Rounded.Add
    } else {
        Icons.Rounded.Delete
    }

    FloatingActionButton(
        onClick = {
            floatingActionOnClick.invoke()
        },
        containerColor = MaterialTheme.colorScheme.primary,
        shape = RoundedCornerShape(16.dp),
        modifier = Modifier.clickable(
            onClick = {
                dialog = true
            }
        )
    ) {
        Icon(
            imageVector = icon,
            contentDescription = null,
            tint = Color.White,
        )
    }
}

@UIModePreviews
@Composable
fun FloatingActionButtonAppPreview() {
    ThemeApp {
        Column(
            modifier = Modifier
                .wrapContentWidth()
                .wrapContentHeight()
                .background(color = MaterialTheme.colorScheme.background),
        ) {
            FloatingActionButtonApp(isInitialButton = false, showDialog = false) {

            }
        }
    }
}