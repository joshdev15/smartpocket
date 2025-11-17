package com.joshdev.smartpocket.domain.models

import com.joshdev.smartpocket.repository.interfaces.ToRealm
import com.joshdev.smartpocket.repository.models.ArchingRealm

data class Arching(
    val id: String = "",
    val name: String,
    val creationDate: Long
) : ToRealm<ArchingRealm> {
    override fun toRealm(): ArchingRealm {
        return ArchingRealm().apply {
            name = this@Arching.name
            creationDate = this@Arching.creationDate
        }
    }
}