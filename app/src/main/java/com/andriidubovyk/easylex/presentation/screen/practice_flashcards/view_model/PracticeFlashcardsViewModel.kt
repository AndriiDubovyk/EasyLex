package com.andriidubovyk.easylex.presentation.screen.practice_flashcards.view_model

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.andriidubovyk.easylex.domain.use_case.FlashcardUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PracticeFlashcardsViewModel @Inject constructor(
    private val flashcardUseCases: FlashcardUseCases,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val _state = MutableStateFlow(PracticeFlashcardState())
    val state = _state.asStateFlow()


    init {
        savedStateHandle.get<Int>("flashcardId")?.let {
            initFlashcard(it)
        }
    }

    fun onEvent(event: PracticeFlashcardEvent) {
        when (event) {
            is PracticeFlashcardEvent.ShowAnswer -> processShowAnswer()
            is PracticeFlashcardEvent.SelectAnswerRating -> processSelectAnswerRating(event.value)
            is PracticeFlashcardEvent.GoToNext -> processGoToNext()
            is PracticeFlashcardEvent.StopGoingToNext -> processStopGoingToNext()
            is PracticeFlashcardEvent.StartPronouncingWord -> processStartPronouncingWord()
            is PracticeFlashcardEvent.StopPronouncingWord -> processStopPronouncingWord()
        }
    }

    private fun initFlashcard(id: Int) {
        viewModelScope.launch {
            if (id != -1) {
                _state.update { it.copy(flashcard = flashcardUseCases.getFlashcard(id)) }
            } else {
                val flashcardsToStudy = flashcardUseCases.getLowestScoreFlashcards()
                if (flashcardsToStudy.isEmpty()) return@launch
                _state.update {
                    it.copy(flashcard = flashcardsToStudy.random())
                }
            }
        }
    }

    private fun processShowAnswer() = viewModelScope.launch {
        _state.update {
            it.copy(isAnswerVisible = true)
        }
    }

    private fun processGoToNext() = viewModelScope.launch {
        _state.update { it.copy(isGoingToNextFlashcard = true) }
    }

    private fun processStopGoingToNext() = viewModelScope.launch {
        _state.update { it.copy(isGoingToNextFlashcard = false) }
    }

    private fun processSelectAnswerRating(rating: UserAnswerRating) = viewModelScope.launch {
        _state.update {
            it.copy(userAnswerRating = rating)
        }
        // Update score according to the answer
        state.value.flashcard?.let {
            viewModelScope.launch {
                flashcardUseCases.addFlashcard(
                    flashcard = it.copy(
                        score = getNewFlashcardScoreByAnswer(
                            prevScore = it.score,
                            answerRating = rating
                        )
                    )
                )
            }
        }
    }

    private fun processStartPronouncingWord() = viewModelScope.launch {
        _state.update { it.copy(isWordPronounced = true) }
    }

    private fun processStopPronouncingWord() = viewModelScope.launch {
        _state.update { it.copy(isWordPronounced = false) }
    }


    private fun getNewFlashcardScoreByAnswer(
        prevScore: Int,
        answerRating: UserAnswerRating
    ): Int {
        return when (answerRating) {
            UserAnswerRating.BAD -> if (prevScore < 1) 0 else prevScore - 1
            UserAnswerRating.GOOD -> prevScore + 1
            else -> prevScore
        }
    }
}