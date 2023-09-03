package br.com.todo_compose.compose.others

import android.content.res.Configuration.UI_MODE_NIGHT_NO
import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.compose.ui.tooling.preview.Preview

@Preview(
    name = "Mode Night",
    group = "Mode Night Group",
    uiMode = UI_MODE_NIGHT_YES
)
@Preview(
    name = "Mode Light",
    group = "Mode Light Group",
    uiMode = UI_MODE_NIGHT_NO
)
annotation class UIModePreviews