package com.joshdev.smartpocket.domain.ledger

import com.joshdev.smartpocket.repository.mappers.ToRealm
import com.joshdev.smartpocket.repository.entities.ledger.LedgerCategoryRealm
import io.realm.kotlin.types.annotations.PrimaryKey
import org.mongodb.kbson.ObjectId

data class Category(
    @PrimaryKey
    val id: String = "",
    val name: String,
    val level: Int,
    val description: String,
    val color: String
) : ToRealm<LedgerCategoryRealm> {
    override fun toRealm(): LedgerCategoryRealm {
        return LedgerCategoryRealm().apply {
            id = if (this@Category.id != "") ObjectId(this@Category.id) else ObjectId()
            name = this@Category.name
            level = this@Category.level
            description = this@Category.description
            color = this@Category.color
        }
    }
}