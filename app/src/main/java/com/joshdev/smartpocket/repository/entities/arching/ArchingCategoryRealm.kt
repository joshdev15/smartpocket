package com.joshdev.smartpocket.repository.entities.arching

import com.joshdev.smartpocket.domain.arching.Category
import com.joshdev.smartpocket.repository.mappers.ToData
import io.realm.kotlin.types.RealmObject
import org.mongodb.kbson.ObjectId

class ArchingCategoryRealm() : RealmObject, ToData<Category> {
    var id: ObjectId = ObjectId()
    var name: String = ""
    var level: Int = 0
    var description: String = ""
    var color: String = ""

    override fun toData(): Category {
        return Category(
            id = id.toHexString(),
            name = name,
            level = level,
            description = description,
            color = color
        )
    }
}
