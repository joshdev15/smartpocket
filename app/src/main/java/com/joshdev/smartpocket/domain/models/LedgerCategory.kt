package com.joshdev.smartpocket.domain.models

import com.joshdev.smartpocket.repository.interfaces.ToRealm
import com.joshdev.smartpocket.repository.models.LedgerCategoryRealm
import io.realm.kotlin.types.annotations.PrimaryKey

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
            name = this@LedgerCategory.name
            level = this@LedgerCategory.level
            description = this@LedgerCategory.description
            color = this@LedgerCategory.color
        }
    }
}