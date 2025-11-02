package com.joshdev.smartpocket.repository.interfaces

interface ToRealm<T> {
    fun toRealm(): T
}