package com.joshdev.smartpocket.repository.entities

import com.joshdev.smartpocket.domain.models.ArchingRecord
import com.joshdev.smartpocket.repository.mappers.ToData
import io.realm.kotlin.types.RealmObject
import io.realm.kotlin.types.annotations.PrimaryKey
import org.mongodb.kbson.ObjectId

class ArchingRecordRealm : RealmObject, ToData<ArchingRecord> {
    @PrimaryKey
    var id: ObjectId = ObjectId()
    var archingId: ObjectId = ObjectId()
    var dayName: String = ""
    var weekOfYear: Int = 0
    var monthOfYear: Int = 0

    override fun toData(): ArchingRecord {
        return ArchingRecord(
            id = id.toHexString(),
            archingId = archingId.toHexString(),
            dayName = dayName,
            weekOfYear = weekOfYear,
            monthOfYear = monthOfYear
        )
    }
}
