package com.andriidubovyk.easylex.data.repository

import com.andriidubovyk.easylex.data.remote.DictionaryApi
import com.andriidubovyk.easylex.data.remote.dto.WordDetailDto
import com.andriidubovyk.easylex.domain.repository.WordDetailRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class WordDetailRepositoryImpl @Inject constructor(
    private val api: DictionaryApi
) : WordDetailRepository {

    override suspend fun getWordDetail(word: String): WordDetailDto {
        return withContext(Dispatchers.IO) {
            val response = api.getWordDetail(word)
            if (!response.isSuccessful) throw Error("Unsuccessful response from Dictionary API")
            response.body() ?: throw Error("Error in Dictionary API response")
        }
    }
}