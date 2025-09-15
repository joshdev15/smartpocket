package com.joshdev.smartpocket.domain.models

import io.realm.kotlin.types.RealmObject
import io.realm.kotlin.types.annotations.PrimaryKey
import org.mongodb.kbson.ObjectId

data class Invoice(
    val id: String = "",
    val recordId: String = "",
    val name: String,
    val author: String,
    val creationDate: Long,
    val modificationDate: Long,
    var total: Double?
)

class InvoiceRealm() : RealmObject {
    @PrimaryKey
    var id: ObjectId = ObjectId.invoke()
    var recordId: String = ""
    var name: String = ""
    var author: String = ""
    var creationDate: Long = 0L
    var modificationDate: Long = 0L
    var total: Double? = null

    fun toInvoice(): Invoice {
        return Invoice(
            id = id.toHexString(),
            recordId = recordId,
            name = name,
            author = author,
            creationDate = creationDate,
            modificationDate = modificationDate,
            total = total
        )
    }
}
