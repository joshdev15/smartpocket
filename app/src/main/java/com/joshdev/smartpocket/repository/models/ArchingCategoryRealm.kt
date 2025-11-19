package com.joshdev.smartpocket.repository.models

import com.joshdev.smartpocket.domain.models.ArchingCategory
import com.joshdev.smartpocket.repository.interfaces.ToData
import io.realm.kotlin.types.RealmObject
import org.mongodb.kbson.ObjectId

class ArchingCategoryRealm() : RealmObject, ToData<ArchingCategory> {
    var id: ObjectId = ObjectId()
    var name: String = ""
    var level: Int = 0
    var description: String = ""
    var color: String = ""

    override fun toData(): ArchingCategory {
        return ArchingCategory(
            id = id.toHexString(),
            name = name,
            level = level,
            description = description,
            color = color
        )
    }
}
