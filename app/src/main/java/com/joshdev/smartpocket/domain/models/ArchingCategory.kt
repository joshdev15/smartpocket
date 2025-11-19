package com.joshdev.smartpocket.domain.models

import com.joshdev.smartpocket.repository.interfaces.ToRealm
import com.joshdev.smartpocket.repository.models.ArchingCategoryRealm
import io.realm.kotlin.types.annotations.PrimaryKey
import org.mongodb.kbson.ObjectId

class ArchingCategory(
    @PrimaryKey
    val id: String = "",
    val name: String,
    val level: Int,
    val description: String,
    val color: String
) : ToRealm<ArchingCategoryRealm> {
    override fun toRealm(): ArchingCategoryRealm {
        return ArchingCategoryRealm().apply {
            id = if (this@ArchingCategory.id != "") {
                ObjectId(this@ArchingCategory.id)
            } else {
                ObjectId()
            }

            name = this@ArchingCategory.name
            level = this@ArchingCategory.level
            description = this@ArchingCategory.description
            color = this@ArchingCategory.color
        }
    }
}