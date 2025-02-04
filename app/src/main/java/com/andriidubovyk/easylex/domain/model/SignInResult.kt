package com.andriidubovyk.easylex.domain.model

data class SignInResult(
    val data: UserData?,
    val errorMessage: String?
)