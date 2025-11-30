package com.joshdev.smartpocket.domain.arching

import com.joshdev.smartpocket.repository.entities.arching.ArchingRecordItemRealm
import com.joshdev.smartpocket.repository.mappers.ToRealm
import org.mongodb.kbson.ObjectId

data class RecordItem(
    val id: String = "",
    val product: Product?,
    val quantity: Int,
) : ToRealm<ArchingRecordItemRealm> {
    override fun toRealm(): ArchingRecordItemRealm {
        return ArchingRecordItemRealm().apply {
            id = if (this@RecordItem.id != "") ObjectId(this@RecordItem.id) else ObjectId()
            product = this@RecordItem.product?.toRealm()
            quantity = this@RecordItem.quantity
        }
    }
}