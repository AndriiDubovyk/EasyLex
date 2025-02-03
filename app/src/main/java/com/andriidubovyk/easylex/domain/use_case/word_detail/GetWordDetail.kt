package com.andriidubovyk.easylex.domain.use_case.word_detail

import com.andriidubovyk.easylex.common.Resource
import com.andriidubovyk.easylex.data.remote.dto.toWordDetail
import com.andriidubovyk.easylex.domain.model.WordDetail
import com.andriidubovyk.easylex.domain.repository.WordDetailRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.io.IOException

class GetWordDetail(
    private val repository: WordDetailRepository
) {

    operator fun invoke(word: String): Flow<Resource<WordDetail>> = flow {
        try {
            emit(Resource.Loading())
            val wordDetail = repository.getWordDetail(word).toWordDetail()
            emit(Resource.Success(wordDetail))
        } catch (e: IOException) {
            emit(Resource.Error("Couldn't reach server. Check your internet connection."))
        } catch (e: Error) {
            emit(Resource.Error(e.localizedMessage ?: "An unexpected error occurred."))
        }
    }
}