package com.joshdev.smartpocket.domain.models

import io.realm.kotlin.types.RealmObject
import io.realm.kotlin.types.annotations.PrimaryKey

data class Category(
    @PrimaryKey
    val id: Int = 0,
    val name: String,
    val level: Int,
    val description: String,
    val color: String
)

class CategoryRealms() : RealmObject {
    var id: Int = 0
    var name: String = ""
    var level: Int = 0
    var description: String = ""
    var color: String = ""

    constructor(id: Int, name: String, level: Int, description: String, color: String) : this() {
        this.id = id
        this.name = name
        this.level = level
        this.description = description
        this.color = color
    }
}
