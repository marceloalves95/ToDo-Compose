package br.com.todo_compose.presentation.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import br.com.todo_compose.compose.others.UIModePreviews
import br.com.todo_compose.compose.theme.ThemeApp
import br.com.todo_compose.domain.models.TaskDomain
import br.com.todo_compose.extensions.reducedDate
import br.com.todo_compose.presentation.ui.mock.dummyTaskDomain

@Composable
fun ContentCard(taskDomain: TaskDomain) {
    ConstraintLayout(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
    ) {
        val (taskTitleText, taskDescriptionText, taskDataText, taskTimeText) = createRefs()

        Text(
            text = taskDomain.title,
            modifier = Modifier
                .wrapContentHeight()
                .padding(
                    top = 8.dp,
                    start = 8.dp,
                    end = 8.dp
                )
                .constrainAs(taskTitleText) {
                    top.linkTo(parent.top)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                    width = Dimension.fillToConstraints
                }
        )

        Text(
            text = taskDomain.description,
            modifier = Modifier
                .wrapContentHeight()
                .padding(end = 8.dp, start = 8.dp)
                .constrainAs(taskDescriptionText) {
                    top.linkTo(taskTitleText.bottom)
                    start.linkTo(taskTitleText.start)
                    end.linkTo(parent.end)
                    width = Dimension.fillToConstraints
                }
        )

        Text(
            text = taskDomain.date.reducedDate(),
            modifier = Modifier
                .wrapContentHeight()
                .wrapContentWidth()
                .padding(end = 8.dp, bottom = 8.dp)
                .constrainAs(taskDataText) {
                    end.linkTo(parent.end)
                    bottom.linkTo(parent.bottom)
                }
        )

        Text(
            text = taskDomain.time,
            modifier = Modifier
                .wrapContentHeight()
                .wrapContentWidth()
                .padding(top = 8.dp, bottom = 8.dp, start = 8.dp)
                .constrainAs(taskTimeText) {
                    top.linkTo(taskDescriptionText.bottom)
                    start.linkTo(taskDescriptionText.start)
                    bottom.linkTo(parent.bottom)
                }
        )
    }
}

@UIModePreviews
@Composable
fun CardTaskItemPreview() {
    val color = MaterialTheme.colorScheme.primary.copy(alpha = 0.6f)
    ThemeApp {
        Column(
            modifier = Modifier
                .wrapContentWidth()
                .wrapContentHeight()
                .background(color = MaterialTheme.colorScheme.background),
        ) {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight()
                    .padding(10.dp),
                shape = CardDefaults.shape,
                border = BorderStroke(1.dp, color),
                elevation = CardDefaults.cardElevation(
                    defaultElevation = 3.dp
                )
            ) {
                ContentCard(taskDomain = dummyTaskDomain)
            }
        }
    }
}