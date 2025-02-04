package com.andriidubovyk.easylex.domain.use_case.account

import com.andriidubovyk.easylex.domain.model.UserData
import com.andriidubovyk.easylex.domain.repository.AccountRepository

class GetCurrentUserData(
    private val repository: AccountRepository
) {

    suspend operator fun invoke(): UserData? {
        return repository.getCurrentUserData()
    }
}