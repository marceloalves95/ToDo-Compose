package br.com.todo_compose.compose.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import br.com.todo_compose.compose.others.UIModePreviews
import br.com.todo_compose.compose.theme.ThemeApp

@Composable
fun ScaffoldApp(
    title: String,
    navigationIcon: @Composable (() -> Unit)? = null,
    floatingAction: @Composable (() -> Unit)? = null,
    actions:@Composable (() -> Unit)? = null,
    content: @Composable () -> Unit
) {
    Scaffold(
        topBar = {
            TopBarApp(title = title, navigationIcon = navigationIcon, actions = actions)
        },
        content = { padding ->
            Column(modifier = Modifier.padding(padding)) {
                content()
            }
        },
        floatingActionButton = {
            floatingAction?.invoke()
        },
    )
}

@UIModePreviews
@Composable
fun ScaffoldAppPreview() {
    ThemeApp {
        ScaffoldApp(
            title = "Titulo",
            floatingAction = {
                FloatingActionButtonApp(isInitialButton = false, false) {

                }
            },
            content = {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "Tela Inicial",
                        textAlign = TextAlign.Center
                    )
                }
            },
            actions = {
                IconButton(
                    onClick = {

                    }
                ) {
                    Icon(
                        imageVector = Icons.Filled.Search,
                        contentDescription = null,
                        tint = Color.White
                    )
                }
            }
        )
    }
}