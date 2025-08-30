package com.joshdev.smartpocket.repository.utils

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

object FlowUtils {
    sealed class Statuses<T>(val data: T? = null) {
        class Fulfilled<T>(data: T? = null) : Statuses<T>(data)
        class Error<T>(data: T? = null) : Statuses<T>(data)
        class Loading<T>(data: T? = null) : Statuses<T>(data)
    }

    fun <T> response(operation: suspend () -> T): Flow<Statuses<T>> = flow {
        try {
            emit(Statuses.Loading())
            emit(Statuses.Fulfilled(operation()))
        } catch (e: Exception) {
            emit(Statuses.Error<T>())
        }
    }
}