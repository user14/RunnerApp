package com.android.core.domain.util


sealed interface DataError: Error {
    enum class Network : DataError {
        REQUEST_TIMEOUT,
        UNAUTHORIZED,
        CONFLICT,
        PAYLOAD_TOO_LARGE,
        TOO_MANY_REQUESTS,
        NO_INTERNET,
        SERVER_ERROR,
        SERIALIZATION,
        UNKNOWN
    }

    enum class Local : DataError {
            DISK_FULL
    }
}