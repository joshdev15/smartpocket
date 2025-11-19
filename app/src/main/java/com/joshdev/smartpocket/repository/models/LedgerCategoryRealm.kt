package com.joshdev.smartpocket.repository.models

import com.joshdev.smartpocket.domain.models.LedgerCategory
import com.joshdev.smartpocket.repository.interfaces.ToData
import io.realm.kotlin.types.RealmObject
import org.mongodb.kbson.ObjectId

class LedgerCategoryRealm() : RealmObject, ToData<LedgerCategory> {
    var id: ObjectId = ObjectId()
    var name: String = ""
    var level: Int = 0
    var description: String = ""
    var color: String = ""

    override fun toData(): LedgerCategory {
        return LedgerCategory(
            id = id.toHexString(),
            name = name,
            level = level,
            description = description,
            color = color
        )
    }
}
