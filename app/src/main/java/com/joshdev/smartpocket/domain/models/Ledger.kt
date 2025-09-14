package com.joshdev.smartpocket.domain.models

import io.realm.kotlin.types.RealmObject
import io.realm.kotlin.types.annotations.PrimaryKey
import org.mongodb.kbson.ObjectId

data class Ledger(
    val id: Int = 0,
    val name: String,
    val year: Int,
    val month: Int,
    val author: String,
    val creationDate: Long
)

class LedgerRealm() : RealmObject {
    @PrimaryKey
    var id: ObjectId = ObjectId.invoke()
    var name: String = ""
    var year: Int = 0
    var month: Int = 0
    var author: String = ""
    var creationDate: Long = 0L

    constructor(name: String, year: Int, month: Int, author: String, creationDate: Long) : this() {
        this.name = name
        this.year = year
        this.month = month
        this.author = author
        this.creationDate = creationDate
    }
}
