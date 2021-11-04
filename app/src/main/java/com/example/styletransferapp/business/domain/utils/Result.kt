package com.example.styletransferapp.business.domain.utils

/**
 * A generic class that used as single data response to any query
 * which is not complex and doesn't need flows, hence doesn't present any states.
 * For complex stuff DataState class must be used.
 * @param <T>
 */
sealed class Result<out T : Any> {

    data class Success<out T : Any>(val data: T?) : Result<T>()
    data class Error(val exception: Exception) : Result<Nothing>()

    override fun toString(): String {
        return when (this) {
            is Success<*> -> "Success[data=$data]"
            is Error -> "Error[exception=$exception]"
        }
    }
}