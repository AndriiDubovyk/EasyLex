package com.andriidubovyk.easylex.domain.use_case.account

import com.andriidubovyk.easylex.domain.model.SignInResult
import com.andriidubovyk.easylex.domain.repository.AccountRepository

class SignIn(
    private val repository: AccountRepository
) {

    suspend operator fun invoke(token: String?): SignInResult {
        return repository.signIn(token)
    }
}