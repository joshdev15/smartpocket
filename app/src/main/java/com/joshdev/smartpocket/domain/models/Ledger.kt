package com.joshdev.smartpocket.domain.models

import io.realm.kotlin.types.RealmObject
import io.realm.kotlin.types.annotations.PrimaryKey
import org.mongodb.kbson.ObjectId

data class Ledger(
    val id: String = "",
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

    fun toLedger(): Ledger {
        return Ledger(
            id = id.toHexString(),
            name = name,
            year = year,
            month = month,
            author = author,
            creationDate = creationDate
        )
    }
}
