package com.andriidubovyk.easylex.presentation.screen.practice_flashcards

import android.speech.tts.TextToSpeech
import android.widget.Toast
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.andriidubovyk.easylex.R
import com.andriidubovyk.easylex.presentation.component.PracticeButton
import com.andriidubovyk.easylex.presentation.component.flashcard.FlashcardDisplay
import com.andriidubovyk.easylex.presentation.navigation.Screen
import com.andriidubovyk.easylex.presentation.screen.practice_flashcards.view_model.PracticeFlashcardEvent
import com.andriidubovyk.easylex.presentation.screen.practice_flashcards.view_model.PracticeFlashcardsViewModel
import com.andriidubovyk.easylex.presentation.screen.practice_flashcards.view_model.UserAnswerRating
import java.util.Locale

@Composable
fun PracticeFlashcardScreen(
    modifier: Modifier = Modifier,
    navController: NavController,
    viewModel: PracticeFlashcardsViewModel = hiltViewModel()
) {
    val state by viewModel.state.collectAsState()
    val context = LocalContext.current

    LaunchedEffect(state.isWordPronounced) {
        if (state.isWordPronounced) {
            lateinit var textToSpeech: TextToSpeech
            textToSpeech = TextToSpeech(context) { result ->
                if (result == TextToSpeech.SUCCESS) {
                    textToSpeech.language = Locale.US
                    textToSpeech.setSpeechRate(1f)
                    state.flashcard?.let {
                        textToSpeech.speak(it.word, TextToSpeech.QUEUE_FLUSH, null, null)
                    }
                } else {
                    Toast.makeText(
                        context,
                        context.getString(R.string.unable_to_pronounce_the_word),
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
            viewModel.onEvent(PracticeFlashcardEvent.StopPronouncingWord)
        }
    }

    LaunchedEffect(state.isGoingToNextFlashcard) {
        if (state.isGoingToNextFlashcard) {
            viewModel.onEvent(PracticeFlashcardEvent.StopGoingToNext)
            navController.navigate(Screen.PRACTICE_FLASHCARD.route)
        }
    }


    Column(modifier = modifier) {
        FlashcardDisplay(
            modifier = Modifier.weight(1f),
            flashcard = state.flashcard,
            onPronounceWord = { viewModel.onEvent(PracticeFlashcardEvent.StartPronouncingWord) },
            isAnswerVisible = state.isAnswerVisible,
        )

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(80.dp)
        ) {
            if (state.isAnswerVisible && state.userAnswerRating == UserAnswerRating.NONE) {
                PracticeButton(
                    modifier = Modifier.weight(1f),
                    onClick = {
                        viewModel.onEvent(
                            PracticeFlashcardEvent.SelectAnswerRating(UserAnswerRating.BAD)
                        )
                    },
                    text = stringResource(R.string.bad),
                    color = Color.Red
                )
                PracticeButton(
                    modifier = Modifier.weight(1f),
                    onClick = {
                        viewModel.onEvent(
                            PracticeFlashcardEvent.SelectAnswerRating(UserAnswerRating.OK)
                        )
                    },
                    text = stringResource(R.string.ok),
                    color = Color.LightGray
                )
                PracticeButton(
                    modifier = Modifier.weight(1f),
                    onClick = {
                        viewModel.onEvent(
                            PracticeFlashcardEvent.SelectAnswerRating(UserAnswerRating.GOOD)
                        )
                    },
                    text = stringResource(R.string.good),
                    color = Color.Green
                )
            } else if (state.userAnswerRating == UserAnswerRating.NONE) {
                PracticeButton(
                    modifier = Modifier.weight(1f),
                    onClick = { viewModel.onEvent(PracticeFlashcardEvent.ShowAnswer) },
                    text = stringResource(R.string.show_answer),
                    color = Color.LightGray
                )
            } else {
                PracticeButton(
                    modifier = Modifier.weight(1f),
                    onClick = { viewModel.onEvent(PracticeFlashcardEvent.GoToNext) },
                    text = stringResource(R.string.next),
                    color = Color.LightGray
                )
            }
        }
    }

}