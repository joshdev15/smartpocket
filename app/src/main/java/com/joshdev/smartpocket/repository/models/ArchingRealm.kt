package com.joshdev.smartpocket.repository.models

import com.joshdev.smartpocket.domain.models.Arching
import com.joshdev.smartpocket.repository.interfaces.ToData
import io.realm.kotlin.types.RealmObject
import io.realm.kotlin.types.annotations.PrimaryKey
import org.mongodb.kbson.ObjectId

class ArchingRealm() : RealmObject, ToData<Arching> {
    @PrimaryKey
    var id: ObjectId = ObjectId.invoke()
    var name: String = ""
    var creationDate: Long = 0L

    override fun toData(): Arching {
        return Arching(
            id = id.toHexString(),
            name = name,
            creationDate = creationDate
        )
    }
}