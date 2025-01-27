package com.andriidubovyk.easylex.presentation.component

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle

@Composable
fun DescriptionText(
    modifier: Modifier = Modifier,
    descName: String,
    descValue: String,
    textAlign: TextAlign = TextAlign.Left
) {
    Text(
        modifier = modifier,
        text = buildAnnotatedString {
            withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)) {
                append(descName)
            }
            withStyle(style = SpanStyle(fontStyle = FontStyle.Italic)) {
                append(": $descValue")
            }
        },
        style = MaterialTheme.typography.titleMedium,
        textAlign = textAlign
    )
}