package com.andriidubovyk.easylex.domain.utils

sealed class FlashcardOrder(val orderType: OrderType) {
    class Word(orderType: OrderType) : FlashcardOrder(orderType)
    class Definition(orderType: OrderType) : FlashcardOrder(orderType)
    class Translation(orderType: OrderType) : FlashcardOrder(orderType)
    class Score(orderType: OrderType) : FlashcardOrder(orderType)
    class Date(orderType: OrderType) : FlashcardOrder(orderType)

    fun copy(orderType: OrderType): FlashcardOrder {
        return when (this) {
            is Word -> Word(orderType)
            is Definition -> Definition(orderType)
            is Translation -> Translation(orderType)
            is Score -> Score(orderType)
            is Date -> Date(orderType)
        }
    }
}
