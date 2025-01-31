package com.andriidubovyk.easylex.presentation.screen.study.view_model

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.andriidubovyk.easylex.domain.use_case.flashcard.FlashcardUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class StudyViewModel @Inject constructor(
    private val flashcardUseCases: FlashcardUseCases
) : ViewModel() {

    private val _state = MutableStateFlow(StudyState())
    val state = _state.asStateFlow()

    init {
        viewModelScope.launch {
            _state.value = state.value.copy(
                totalScore = flashcardUseCases.getTotalScore()
            )
        }
    }

}