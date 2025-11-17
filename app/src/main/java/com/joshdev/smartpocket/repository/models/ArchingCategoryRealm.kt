package com.joshdev.smartpocket.repository.models

import com.joshdev.smartpocket.domain.models.ArchingCategory
import com.joshdev.smartpocket.domain.models.LedgerCategory
import com.joshdev.smartpocket.repository.interfaces.ToData
import io.realm.kotlin.types.RealmObject

class ArchingCategoryRealm() : RealmObject, ToData<ArchingCategory> {
    var id: String = ""
    var name: String = ""
    var level: Int = 0
    var description: String = ""
    var color: String = ""

    override fun toData(): ArchingCategory {
        return ArchingCategory(
            id = id,
            name = name,
            level = level,
            description = description,
            color = color
        )
    }
}
