package com.andriidubovyk.easylex.domain.use_case.account

import com.andriidubovyk.easylex.domain.repository.AccountRepository

class SignOut(
    private val repository: AccountRepository
) {

    suspend operator fun invoke() {
        return repository.signOut()
    }
}