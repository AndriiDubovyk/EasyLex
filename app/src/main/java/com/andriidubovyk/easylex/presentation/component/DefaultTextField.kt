package com.andriidubovyk.easylex.presentation.component

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun DefaultTextField(
    modifier: Modifier = Modifier,
    value: String,
    lines: Int,
    onValueChange: (String) -> Unit,
    placeholderText: String
) {
    TextField(
        modifier = modifier,
        value = value,
        onValueChange = { onValueChange(it) },
        textStyle = MaterialTheme.typography.bodyLarge,
        minLines = lines,
        maxLines = lines,
        singleLine = lines == 1,
        placeholder = {
            Text(
                text = placeholderText,
                style = MaterialTheme.typography.bodyLarge
            )
        },
        shape = RoundedCornerShape(25.dp),
        colors = TextFieldDefaults.colors(
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent,
            disabledIndicatorColor = Color.Transparent
        )
    )
}