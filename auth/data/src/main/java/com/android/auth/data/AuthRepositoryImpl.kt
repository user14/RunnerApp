package com.android.auth.data

import com.android.auth.domain.AuthRepository
import com.android.core.domain.util.DataError
import com.android.core.domain.util.EmptyDataResult
import io.ktor.client.HttpClient
import com.android.core.data.networking.post

class AuthRepositoryImpl(private val httpClient: HttpClient) : AuthRepository {
    override suspend fun register(
        email: String,
        password: String,
    ): EmptyDataResult<DataError.Network> {

        return httpClient.post<RegisterRequest, Unit>(
            route = "/register",
            body = RegisterRequest(
                email = email,
                password = password
            )
        )
    }
}