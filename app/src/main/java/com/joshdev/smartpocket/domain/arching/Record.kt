package com.joshdev.smartpocket.domain.arching

import com.joshdev.smartpocket.repository.entities.arching.ArchingRecordRealm
import com.joshdev.smartpocket.repository.mappers.ToRealm
import io.realm.kotlin.ext.toRealmList
import org.mongodb.kbson.ObjectId

data class Record(
    val id: String = "",
    val archingId: String = "",
    val dayName: String,
    val weekOfYear: Int,
    val monthOfYear: Int,
) : ToRealm<ArchingRecordRealm> {
    override fun toRealm(): ArchingRecordRealm {
        return ArchingRecordRealm().apply {
            id = if (this@Record.id != "") ObjectId(this@Record.id) else ObjectId()
            archingId = ObjectId(this@Record.archingId)
            dayName = this@Record.dayName
            weekOfYear = this@Record.weekOfYear
            monthOfYear = this@Record.monthOfYear
        }
    }
}
