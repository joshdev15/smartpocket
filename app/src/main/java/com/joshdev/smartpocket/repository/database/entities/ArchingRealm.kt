package com.joshdev.smartpocket.repository.database.entities

import com.joshdev.smartpocket.domain.models.Arching
import com.joshdev.smartpocket.repository.mappers.ToData
import io.realm.kotlin.ext.realmListOf
import io.realm.kotlin.types.RealmList
import io.realm.kotlin.types.RealmObject
import io.realm.kotlin.types.annotations.PrimaryKey
import org.mongodb.kbson.ObjectId

class ArchingRealm() : RealmObject, ToData<Arching> {
    @PrimaryKey
    var id: ObjectId = ObjectId()
    var name: String = ""
    var creationDate: Long = 0L
    var records: RealmList<ArchingRecordRealm> = realmListOf()

    override fun toData(): Arching {
        return Arching(
            id = id.toHexString(),
            name = name,
            creationDate = creationDate,
            records = records.map { it.toData() }
        )
    }
}