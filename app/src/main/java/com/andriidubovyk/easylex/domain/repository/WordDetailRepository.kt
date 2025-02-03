package com.andriidubovyk.easylex.domain.repository

import com.andriidubovyk.easylex.data.remote.dto.WordDetailDto

interface WordDetailRepository {
    suspend fun getWordDetail(word: String): WordDetailDto
}