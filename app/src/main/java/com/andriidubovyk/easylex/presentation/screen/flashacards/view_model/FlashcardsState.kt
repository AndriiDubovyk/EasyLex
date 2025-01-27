package com.andriidubovyk.easylex.presentation.screen.flashacards.view_model

import com.andriidubovyk.easylex.domain.model.Flashcard
import com.andriidubovyk.easylex.domain.utils.FlashcardOrder
import com.andriidubovyk.easylex.domain.utils.OrderType

data class FlashcardsState(
    val flashcards: List<Flashcard> = emptyList(),
    val flashcardOrder: FlashcardOrder = FlashcardOrder.Date(OrderType.DESCENDING),
    val isOrderSectionVisible: Boolean = false,
    val searchText: String = "",
    val isLoading: Boolean = true
)
