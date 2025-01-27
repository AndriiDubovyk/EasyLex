package com.andriidubovyk.easylex.presentation.component

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.andriidubovyk.easylex.R
import com.andriidubovyk.easylex.domain.model.Flashcard

@Composable
fun FlashcardComponent(
    flashcard: Flashcard,
    modifier: Modifier = Modifier,
    onClick: () -> Unit,
    onDeleteClick: () -> Unit
) {
    val shape = RoundedCornerShape(25.dp)
    Card(
        modifier = modifier.fillMaxWidth(),
        shape = shape,
        elevation = CardDefaults.elevatedCardElevation(5.dp),
        onClick = onClick
    ) {
        Box {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(20.dp)
            ) {
                DescriptionText(
                    descName = stringResource(R.string.word),
                    descValue = flashcard.word
                )
                flashcard.definition?.let {
                    DescriptionText(descName = stringResource(R.string.definition), descValue = it)
                }
                flashcard.translation?.let {
                    DescriptionText(descName = stringResource(R.string.translation), descValue = it)
                }
                DescriptionText(
                    descName = stringResource(R.string.score),
                    descValue = flashcard.score.toString()
                )
            }
            IconButton(
                modifier = Modifier.align(Alignment.BottomEnd),
                onClick = onDeleteClick,
            ) {
                Icon(
                    imageVector = Icons.Default.Delete,
                    contentDescription = stringResource(R.string.delete_flashcard),
                    tint = Color.Red
                )
            }
        }
    }
}