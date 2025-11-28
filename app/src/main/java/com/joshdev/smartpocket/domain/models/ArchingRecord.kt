package com.joshdev.smartpocket.domain.models

import com.joshdev.smartpocket.repository.entities.ArchingRecordRealm
import com.joshdev.smartpocket.repository.mappers.ToRealm
import org.mongodb.kbson.ObjectId

data class ArchingRecord(
    val id: String = "",
    val archingId: String = "",
    val dayName: String,
    val weekOfYear: Int,
    val monthOfYear: Int
): ToRealm<ArchingRecordRealm> {
    override fun toRealm(): ArchingRecordRealm {
        return ArchingRecordRealm().apply {
            id = if (this@ArchingRecord.id != "") ObjectId(this@ArchingRecord.id) else  ObjectId()
            archingId = ObjectId(this@ArchingRecord.archingId)
            dayName = this@ArchingRecord.dayName
            weekOfYear = this@ArchingRecord.weekOfYear
            monthOfYear = this@ArchingRecord.monthOfYear
        }
    }
}
