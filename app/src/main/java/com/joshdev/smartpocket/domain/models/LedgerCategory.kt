package com.joshdev.smartpocket.domain.models

import com.joshdev.smartpocket.repository.mappers.ToRealm
import com.joshdev.smartpocket.repository.database.entities.LedgerCategoryRealm
import io.realm.kotlin.types.annotations.PrimaryKey
import org.mongodb.kbson.ObjectId

data class LedgerCategory(
    @PrimaryKey
    val id: String = "",
    val name: String,
    val level: Int,
    val description: String,
    val color: String
) : ToRealm<LedgerCategoryRealm> {
    override fun toRealm(): LedgerCategoryRealm {
        return LedgerCategoryRealm().apply {
            id = if (this@LedgerCategory.id != "") ObjectId(this@LedgerCategory.id) else ObjectId()
            name = this@LedgerCategory.name
            level = this@LedgerCategory.level
            description = this@LedgerCategory.description
            color = this@LedgerCategory.color
        }
    }
}