package com.andriidubovyk.easylex.presentation.component.flashcard

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Speaker
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.andriidubovyk.easylex.R
import com.andriidubovyk.easylex.domain.model.Flashcard
import com.andriidubovyk.easylex.presentation.component.DefaultTextField
import com.andriidubovyk.easylex.presentation.component.DescriptionText

@Composable
fun FlashcardDisplay(
    modifier: Modifier = Modifier,
    flashcard: Flashcard?,
    onPronounceWord: () -> Unit,
    isAnswerVisible: Boolean,
) {
    Column(
        modifier = modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        IconButton(
            modifier = Modifier
                .background(
                    color = MaterialTheme.colorScheme.secondaryContainer,
                    shape = CircleShape
                ),
            onClick = { onPronounceWord() }
        ) {
            Icon(
                modifier = Modifier.size(200.dp),
                imageVector = Icons.Default.Speaker,
                contentDescription = stringResource(R.string.pronounce_the_word),
                tint = MaterialTheme.colorScheme.secondary
            )
        }
        Spacer(modifier = Modifier.height(15.dp))
        DescriptionText(
            descName = stringResource(R.string.word),
            descValue = flashcard?.word ?: stringResource(R.string.no_words_to_study),
            textAlign = TextAlign.Center
        )
        Spacer(modifier = Modifier.height(15.dp))
        if (isAnswerVisible) {
            flashcard?.definition?.let {
                DescriptionText(
                    descName = stringResource(R.string.definition),
                    descValue = it,
                    textAlign = TextAlign.Center
                )
                Spacer(modifier = Modifier.height(15.dp))
            }
            flashcard?.translation?.let {
                DescriptionText(
                    descName = stringResource(R.string.translation),
                    descValue = it,
                    textAlign = TextAlign.Center
                )
            }
        } else {
            var text by remember { mutableStateOf("") }
            DefaultTextField(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp),
                value = text,
                onValueChange = { text = it },
                lines = 1,
                placeholderText = stringResource(R.string.flashcard_practice_type_hint),
            )
        }
    }
}