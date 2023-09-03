package br.com.todo_compose.presentation.ui.main

import android.content.Context
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material.icons.rounded.Delete
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import br.com.todo_compose.R
import br.com.todo_compose.compose.components.FloatingActionButtonApp
import br.com.todo_compose.compose.components.ScaffoldApp
import br.com.todo_compose.compose.others.UIModePreviews
import br.com.todo_compose.compose.theme.ThemeApp
import br.com.todo_compose.domain.mapper.toTaskDomainModel
import br.com.todo_compose.presentation.components.ContentCard
import br.com.todo_compose.presentation.model.TaskDomainModel
import br.com.todo_compose.presentation.ui.addAndAlter.AddAndAlterTaskActivity
import br.com.todo_compose.presentation.ui.mock.dummyTaskDomain
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : ComponentActivity() {

    private lateinit var context: Context
    private val viewModel: MainActivityViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.taskAll()
        setContent {
            ThemeApp {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    context = LocalContext.current
                    ListAllTask()
                }
            }
        }
    }

    @OptIn(ExperimentalFoundationApi::class)
    @Composable
    fun ListAllTask() {

        var allTask = viewModel.state.collectAsState().value
        var isAddOrAlterClicked by remember { mutableStateOf(true) }
        var selectedIndex by remember { mutableIntStateOf(-1) }
        var itemId by remember { mutableIntStateOf(0) }
        var dialog by remember { mutableStateOf(false) }

        ScaffoldApp(
            title = stringResource(id = R.string.app_name),
            floatingAction = {
                FloatingActionButton(
                    onClick = {
                        if (isAddOrAlterClicked) {
                            openTask()
                        } else {
                            dialog = true
                        }
                    },
                    containerColor = MaterialTheme.colorScheme.primary,
                    shape = RoundedCornerShape(16.dp)
                ) {
                    Icon(
                        imageVector = isAddOrAlterClicked.verifyIcon(),
                        contentDescription = null,
                        tint = Color.White,
                    )
                }

                if (dialog) {
                    AlertDialog(
                        onDismissRequest = {
                            dialog = false
                        },
                        title = {
                            Text(text = stringResource(id = R.string.dialog_title))
                        },
                        text = {
                            Text(text = stringResource(id = R.string.dialog_description))
                        },
                        confirmButton = {
                            TextButton(
                                onClick = {
                                    dialog = false
                                    viewModel.deleteTaskById(itemId)
                                    isAddOrAlterClicked = true
                                    viewModel.taskAll()
                                }
                            ) {
                                Text(stringResource(id = R.string.dialog_yes))
                            }
                            allTask = viewModel.state.collectAsState().value
                        },
                        dismissButton = {
                            TextButton(
                                onClick = {
                                    dialog = false
                                }
                            ) {
                                Text(stringResource(id = R.string.dialog_no))
                            }
                        }
                    )
                }
            },
            content = {
                if (allTask.isEmpty()) {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = stringResource(id = R.string.empty_list),
                            textAlign = TextAlign.Center
                        )
                    }
                } else {
                    LazyVerticalGrid(columns = GridCells.Fixed(2), content = {
                        items(
                            items = allTask,
                            itemContent = { content ->
                                itemId = selectedIndex
                                val isSelected = content.id == selectedIndex
                                Card(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .wrapContentHeight()
                                        .padding(10.dp)
                                        .combinedClickable(
                                            onClick = {
                                                openTask(content.toTaskDomainModel())
                                            },
                                            onLongClick = {
                                                isAddOrAlterClicked = !isAddOrAlterClicked
                                                selectedIndex =
                                                    if (selectedIndex != content.id) content.id else -1
                                            }
                                        ),
                                    border = BorderStroke(
                                        width = 1.dp,
                                        color = isSelected.setColorBorder()
                                    ),
                                    shape = CardDefaults.shape,
                                    elevation = CardDefaults.cardElevation(
                                        defaultElevation = 3.dp
                                    )
                                ) {
                                    ContentCard(taskDomain = content)
                                }
                            }
                        )
                    })
                }
            }
        )
    }

    override fun onStart() {
        super.onStart()
        viewModel.taskAll()
    }

    private fun openTask(taskDomainModel: TaskDomainModel? = null) {
        val intent = AddAndAlterTaskActivity.newInstance(context, taskDomainModel)
        startActivity(intent)
    }

    private fun Boolean.verifyIcon(): ImageVector =
        if (this) Icons.Rounded.Add else Icons.Rounded.Delete

    @Composable
    private fun Boolean.setColorBorder(): Color =
        if (this) MaterialTheme.colorScheme.primary.copy(alpha = 0.6f) else Color.Transparent

    @UIModePreviews
    @Composable
    fun MainActivityPreview() {

        val list = listOf(dummyTaskDomain, dummyTaskDomain)

        ThemeApp {
            Surface(
                modifier = Modifier.fillMaxSize(),
                color = MaterialTheme.colorScheme.background
            ) {
                ScaffoldApp(
                    title = stringResource(id = R.string.app_name),
                    content = {
                        LazyVerticalGrid(columns = GridCells.Fixed(2), content = {
                            items(
                                items = list,
                                itemContent = {
                                    Card(
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .wrapContentHeight()
                                            .padding(10.dp),
                                        shape = CardDefaults.shape,
                                        elevation = CardDefaults.cardElevation(
                                            defaultElevation = 3.dp
                                        )
                                    ) {
                                        ContentCard(taskDomain = dummyTaskDomain)
                                    }
                                }
                            )
                        })
                    },
                    floatingAction = {
                        FloatingActionButtonApp(isInitialButton = false, false) {

                        }
                    }
                )
            }
        }
    }
}

