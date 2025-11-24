package com.joshdev.smartpocket.repository.mappers

interface ToData<T> {
    fun toData(): T
}