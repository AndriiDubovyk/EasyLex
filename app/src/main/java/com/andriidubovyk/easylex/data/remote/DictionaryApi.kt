package com.andriidubovyk.easylex.data.remote

import com.andriidubovyk.easylex.common.DictionaryApiConstants
import com.andriidubovyk.easylex.data.remote.dto.WordDetailDto
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface DictionaryApi {
    @GET("/api/v2/entries/en/{${DictionaryApiConstants.PARAM_WORD}}")
    suspend fun getWordDetail(@Path(DictionaryApiConstants.PARAM_WORD) word: String): Response<WordDetailDto>
}