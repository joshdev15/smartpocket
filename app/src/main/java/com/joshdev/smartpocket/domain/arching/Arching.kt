package com.joshdev.smartpocket.domain.arching

import com.joshdev.smartpocket.repository.mappers.ToRealm
import com.joshdev.smartpocket.repository.entities.arching.ArchingRealm
import org.mongodb.kbson.ObjectId

data class Arching(
    val id: String = "",
    val name: String,
    val creationDate: Long,
) : ToRealm<ArchingRealm> {
    override fun toRealm(): ArchingRealm {
        return ArchingRealm().apply {
            id = if (this@Arching.id != "") ObjectId(this@Arching.id) else ObjectId()
            name = this@Arching.name
            creationDate = this@Arching.creationDate
        }
    }
}