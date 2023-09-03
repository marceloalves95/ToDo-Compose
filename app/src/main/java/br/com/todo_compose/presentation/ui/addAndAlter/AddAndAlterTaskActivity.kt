package br.com.todo_compose.presentation.ui.addAndAlter

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.CutCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import br.com.todo_compose.R
import br.com.todo_compose.compose.components.ScaffoldApp
import br.com.todo_compose.compose.theme.ThemeApp
import br.com.todo_compose.domain.models.TaskDomain
import br.com.todo_compose.extensions.extra
import br.com.todo_compose.extensions.onBackButtonPressed
import br.com.todo_compose.presentation.model.TaskDomainModel
import com.vanpra.composematerialdialogs.MaterialDialog
import com.vanpra.composematerialdialogs.datetime.date.datepicker
import com.vanpra.composematerialdialogs.datetime.time.timepicker
import com.vanpra.composematerialdialogs.rememberMaterialDialogState
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.time.LocalDate
import java.time.LocalTime
import java.time.format.DateTimeFormatter

class AddAndAlterTaskActivity : ComponentActivity() {

    private val addAndAlterTaskViewModel: AddAndAlterTaskViewModel by viewModel()
    private lateinit var context: Context
    private val taskDomain by extra<TaskDomainModel?>(TASK_DOMAIN_MODEL)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            TitleApp()
        }
    }

    @Composable
    fun TitleApp() {
        context = LocalContext.current
        ThemeApp {
            // A surface container using the 'background' color from the theme
            Surface(
                modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background
            ) {

                val title =
                    if (taskDomain != null) {
                        stringResource(id = R.string.update_task)
                    } else {
                        stringResource(id = R.string.create_task)
                    }

                ScaffoldApp(
                    title = title,
                    content = {
                        Content()
                    },
                    navigationIcon = {
                        IconButton(
                            onClick = {
                                onBackButtonPressed()
                            }
                        ) {
                            Icon(
                                imageVector = Icons.Filled.ArrowBack,
                                contentDescription = "Back",
                                tint = Color.White
                            )
                        }
                    }
                )
            }
        }
    }

    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    fun Content() {
        ConstraintLayout(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight()
        ) {
            val (fieldTitle, fieldDescription, fieldDate, buttonDate, fieldTime, buttonTime, fieldStatus, createButton) = createRefs()

            val valueTitle = if (taskDomain?.title != null) taskDomain?.title ?: "" else ""
            var textTitleValue by remember { mutableStateOf(valueTitle) }
            val rightGuideline = createGuidelineFromStart(0.6f)

            OutlinedTextField(value = textTitleValue,
                onValueChange = { textTitleValue = it },
                modifier = Modifier
                    .padding(start = 16.dp, top = 8.dp, end = 16.dp)
                    .wrapContentHeight()
                    .constrainAs(fieldTitle) {
                        width = Dimension.fillToConstraints
                        top.linkTo(parent.top)
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                    },
                label = { Text(text = stringResource(id = R.string.title)) },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = MaterialTheme.colorScheme.primary,
                    unfocusedBorderColor = MaterialTheme.colorScheme.secondary,
                ),
                leadingIcon = {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_title),
                        contentDescription = null
                    )
                },
                trailingIcon = {
                    when {
                        textTitleValue.isNotEmpty() -> IconButton(onClick = {
                            textTitleValue = ""
                        }) {
                            Icon(
                                imageVector = Icons.Filled.Clear, contentDescription = "Clear"
                            )
                        }
                    }
                })

            val valueDescription =
                if (taskDomain?.description != null) taskDomain?.description ?: "" else ""
            var textDescriptionValue by remember { mutableStateOf(valueDescription) }

            OutlinedTextField(value = textDescriptionValue,
                onValueChange = { textDescriptionValue = it },
                modifier = Modifier
                    .padding(start = 16.dp, top = 8.dp, end = 16.dp)
                    .wrapContentHeight()
                    .constrainAs(fieldDescription) {
                        width = Dimension.fillToConstraints
                        top.linkTo(fieldTitle.bottom)
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                    },
                label = { Text(text = stringResource(id = R.string.description)) },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = MaterialTheme.colorScheme.primary,
                    unfocusedBorderColor = MaterialTheme.colorScheme.secondary,
                ),
                leadingIcon = {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_subtitles),
                        contentDescription = null
                    )
                },
                trailingIcon = {
                    when {
                        textDescriptionValue.isNotEmpty() -> IconButton(onClick = {
                            textDescriptionValue = ""
                        }) {
                            Icon(
                                imageVector = Icons.Filled.Clear, contentDescription = "Clear"
                            )
                        }
                    }
                })

            val valueDate = if (taskDomain?.date != null) taskDomain?.date ?: "" else ""
            var textDateValue by remember { mutableStateOf(valueDate) }

            OutlinedTextField(value = textDateValue,
                onValueChange = { textDateValue = it },
                modifier = Modifier
                    .padding(start = 16.dp, top = 8.dp, end = 16.dp)
                    .wrapContentHeight()
                    .constrainAs(fieldDate) {
                        width = Dimension.fillToConstraints
                        top.linkTo(fieldDescription.bottom)
                        start.linkTo(parent.start)
                        end.linkTo(rightGuideline)
                    },
                enabled = false,
                label = { Text(text = stringResource(id = R.string.date)) },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = MaterialTheme.colorScheme.primary,
                    unfocusedBorderColor = MaterialTheme.colorScheme.secondary,
                ),
                leadingIcon = {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_event),
                        contentDescription = null
                    )
                },
                trailingIcon = {
                    when {
                        textDateValue.isNotEmpty() -> IconButton(onClick = {
                            textDateValue = ""
                        }) {
                            Icon(
                                imageVector = Icons.Filled.Clear, contentDescription = "Clear"
                            )
                        }
                    }
                })

            var pickedDate by remember {
                mutableStateOf(LocalDate.now())
            }
            val formattedDate by remember {
                derivedStateOf {
                    DateTimeFormatter
                        .ofPattern("dd/MM/yyyy")
                        .format(pickedDate)
                }
            }
            val dateDialogState = rememberMaterialDialogState()

            Button(
                onClick = {
                    dateDialogState.show()
                },
                modifier = Modifier
                    .padding(end = 16.dp, start = 8.dp, top = 16.dp)
                    .constrainAs(buttonDate) {
                        width = Dimension.fillToConstraints
                        height = Dimension.fillToConstraints
                        top.linkTo(fieldDate.top)
                        start.linkTo(rightGuideline)
                        end.linkTo(parent.end)
                        bottom.linkTo(fieldDate.bottom)
                    },
                shape = CutCornerShape(5)
            ) {
                Icon(
                    painterResource(id = R.drawable.ic_event),
                    contentDescription = null,
                    modifier = Modifier.size(20.dp)
                )

                Text(text = stringResource(id = R.string.open), Modifier.padding(start = 10.dp))
            }

            MaterialDialog(
                dialogState = dateDialogState,
                buttons = {
                    positiveButton(text = "Ok") {
                        textDateValue = formattedDate
                    }
                    negativeButton(text = "Cancel")
                }
            ) {
                datepicker(
                    initialDate = LocalDate.now(),
                    title = "Pick a date"
                ) {
                    pickedDate = it
                }
            }

            val valueTime = if (taskDomain?.time != null) taskDomain?.time ?: "" else ""
            var textTimeValue by remember { mutableStateOf(valueTime) }

            OutlinedTextField(value = textTimeValue,
                onValueChange = { textTimeValue = it },
                modifier = Modifier
                    .padding(start = 16.dp, top = 8.dp, end = 16.dp)
                    .wrapContentHeight()
                    .constrainAs(fieldTime) {
                        width = Dimension.fillToConstraints
                        top.linkTo(fieldDate.bottom)
                        start.linkTo(parent.start)
                        end.linkTo(rightGuideline)
                    },
                enabled = false,
                label = { Text(text = stringResource(id = R.string.time)) },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = MaterialTheme.colorScheme.primary,
                    unfocusedBorderColor = MaterialTheme.colorScheme.secondary,
                ),
                leadingIcon = {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_timer),
                        contentDescription = null
                    )
                },
                trailingIcon = {
                    when {
                        textTimeValue.isNotEmpty() -> IconButton(onClick = {
                            textTimeValue = ""
                        }) {
                            Icon(
                                imageVector = Icons.Filled.Clear, contentDescription = "Clear"
                            )
                        }
                    }
                })

            var pickedTime by remember {
                mutableStateOf(LocalTime.NOON)
            }
            val formattedTime by remember {
                derivedStateOf {
                    DateTimeFormatter
                        .ofPattern("HH:mm")
                        .format(pickedTime)
                }
            }
            val timeDialogState = rememberMaterialDialogState()

            Button(
                onClick = { timeDialogState.show() },
                modifier = Modifier
                    .padding(end = 16.dp, start = 8.dp, top = 16.dp)
                    .constrainAs(buttonTime) {
                        width = Dimension.fillToConstraints
                        height = Dimension.fillToConstraints
                        top.linkTo(fieldTime.top)
                        start.linkTo(rightGuideline)
                        end.linkTo(parent.end)
                        bottom.linkTo(fieldTime.bottom)
                    },
                shape = CutCornerShape(5)
            ) {
                Icon(
                    painterResource(id = R.drawable.ic_timer),
                    contentDescription = null,
                    modifier = Modifier.size(20.dp)
                )
                Text(text = stringResource(id = R.string.open), Modifier.padding(start = 10.dp))
            }

            MaterialDialog(
                dialogState = timeDialogState,
                buttons = {
                    positiveButton(text = "Ok") {
                        textTimeValue = formattedTime
                    }
                    negativeButton(text = "Cancel")
                }
            ) {
                timepicker(
                    initialTime = LocalTime.NOON,
                    title = "Pick a time",
                    timeRange = LocalTime.MIDNIGHT..LocalTime.NOON
                ) {
                    pickedTime = it
                }
            }

            var expanded by remember { mutableStateOf(false) }
            val listStatus: List<String> = resources.getStringArray(R.array.status).toList()
            val valueListStatus =
                if (taskDomain?.status != null) taskDomain?.status ?: "" else listStatus[0]
            var selectedListText by remember { mutableStateOf(valueListStatus) }

            ExposedDropdownMenuBox(expanded = expanded,
                modifier = Modifier
                    .padding(start = 16.dp, top = 16.dp, end = 16.dp)
                    .wrapContentHeight()
                    .constrainAs(fieldStatus) {
                        width = Dimension.fillToConstraints
                        top.linkTo(fieldTime.bottom)
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                    },
                onExpandedChange = {
                    expanded = !expanded
                }) {

                OutlinedTextField(
                    modifier = Modifier
                        .fillMaxWidth()
                        .menuAnchor(),
                    value = selectedListText,
                    onValueChange = {},
                    readOnly = false,
                    leadingIcon = {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_task),
                            contentDescription = null
                        )
                    },
                    trailingIcon = {
                        ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded)
                    },
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = MaterialTheme.colorScheme.primary,
                        unfocusedBorderColor = MaterialTheme.colorScheme.secondary,
                    )
                )

                ExposedDropdownMenu(expanded = expanded, onDismissRequest = {
                    expanded = false
                }) {
                    listStatus.forEach { items ->
                        DropdownMenuItem(text = {
                            Text(text = items)
                        }, onClick = {
                            selectedListText = items
                            expanded = false
                        }, contentPadding = ExposedDropdownMenuDefaults.ItemContentPadding
                        )
                    }
                }
            }

            Button(
                onClick = {
                    val model = TaskDomain(
                        id = taskDomain?.id ?: 0,
                        title = textTitleValue,
                        description = textDescriptionValue,
                        date = textDateValue,
                        time = textTimeValue,
                        status = selectedListText
                    )
                    if (taskDomain != null) {
                        updateTask(model)
                        onBackButtonPressed()
                    } else {
                        saveTask(model)
                        onBackButtonPressed()
                    }
                },
                modifier = Modifier
                    .wrapContentHeight()
                    .padding(end = 16.dp, start = 16.dp, bottom = 16.dp)
                    .constrainAs(createButton) {
                        width = Dimension.fillToConstraints
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                        bottom.linkTo(parent.bottom)
                    },
                shape = CutCornerShape(5)
            ) {
                if (taskDomain != null) {
                    Icon(
                        painterResource(id = R.drawable.ic_task_alter),
                        contentDescription = null,
                        modifier = Modifier.size(20.dp)
                    )

                    Text(
                        text = stringResource(id = R.string.update_task),
                        Modifier.padding(start = 10.dp)
                    )
                } else {
                    Icon(
                        painterResource(id = R.drawable.ic_add_task),
                        contentDescription = null,
                        modifier = Modifier.size(20.dp)
                    )

                    Text(
                        text = stringResource(id = R.string.create_task),
                        Modifier.padding(start = 10.dp)
                    )
                }
            }
        }
    }

    private fun saveTask(taskDomain: TaskDomain) = addAndAlterTaskViewModel.addTask(taskDomain)

    private fun updateTask(taskDomain: TaskDomain) = addAndAlterTaskViewModel.updateTask(taskDomain)

    @Preview(showBackground = true)
    @Composable
    fun ContentPreview() {
        ScaffoldApp(
            title = stringResource(id = R.string.app_name),
            content = {},
            navigationIcon = {
                IconButton(onClick = {}) {
                    Icon(
                        imageVector = Icons.Filled.ArrowBack,
                        contentDescription = "Back",
                        tint = Color.White
                    )
                }
            }
        )
    }

    companion object {
        private const val TASK_DOMAIN_MODEL = "task_domain_model"
        fun newInstance(
            context: Context,
            model: TaskDomainModel? = null
        ): Intent {
            val intent = Intent(context, AddAndAlterTaskActivity::class.java).apply {
                putExtra(TASK_DOMAIN_MODEL, model)
            }
            return intent
        }
    }
}