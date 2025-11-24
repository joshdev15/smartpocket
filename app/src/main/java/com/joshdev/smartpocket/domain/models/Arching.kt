package com.joshdev.smartpocket.domain.models

import com.joshdev.smartpocket.repository.mappers.ToRealm
import com.joshdev.smartpocket.repository.database.entities.ArchingRealm
import com.joshdev.smartpocket.repository.database.entities.ArchingRecordRealm
import io.realm.kotlin.ext.toRealmList
import org.mongodb.kbson.ObjectId

data class Arching(
    val id: String = "",
    val name: String,
    val creationDate: Long,
    val records: List<ArchingRecord> = emptyList()
) : ToRealm<ArchingRealm> {
    override fun toRealm(): ArchingRealm {
        return ArchingRealm().apply {
            id = if (this@Arching.id != "") ObjectId(this@Arching.id) else ObjectId()
            name = this@Arching.name
            creationDate = this@Arching.creationDate
            records = this@Arching.records.map {
                ArchingRecordRealm().apply {
                    id = if (it.id.isNotEmpty()) ObjectId(it.id) else ObjectId()
                    dayName = it.dayName
                    weekOfYear = it.weekOfYear
                    monthOfYear = it.monthOfYear
                }
            }.toRealmList()
        }
    }
}