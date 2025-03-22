package com.android.auth.domain

import com.android.core.domain.util.DataError
import com.android.core.domain.util.EmptyDataResult
import com.android.core.domain.util.Error
import com.android.core.domain.util.Result

interface AuthRepository {
    suspend fun register(email : String, password: String) : EmptyDataResult<DataError.Network>
}