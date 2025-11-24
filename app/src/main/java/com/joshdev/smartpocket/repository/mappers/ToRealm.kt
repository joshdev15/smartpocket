package com.joshdev.smartpocket.repository.mappers

interface ToRealm<T> {
    fun toRealm(): T
}