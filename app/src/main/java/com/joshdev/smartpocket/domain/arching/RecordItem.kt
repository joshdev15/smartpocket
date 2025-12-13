package com.joshdev.smartpocket.domain.arching

import com.joshdev.smartpocket.repository.entities.arching.ArchingRecordItemRealm
import com.joshdev.smartpocket.repository.mappers.ToRealm
import org.mongodb.kbson.ObjectId

data class RecordItem(
    val id: String = "",
    val recordId: String = "",
    val productId: String = "",
    val quantity: Int,
) : ToRealm<ArchingRecordItemRealm> {
    override fun toRealm(): ArchingRecordItemRealm {
        return ArchingRecordItemRealm().apply {
            id = if (this@RecordItem.id != "") ObjectId(this@RecordItem.id) else ObjectId()
            recordId = if (this@RecordItem.recordId != "") ObjectId(this@RecordItem.recordId) else ObjectId()
            productId = if (this@RecordItem.productId != "") ObjectId(this@RecordItem.productId) else ObjectId()
            quantity = this@RecordItem.quantity
        }
    }
}