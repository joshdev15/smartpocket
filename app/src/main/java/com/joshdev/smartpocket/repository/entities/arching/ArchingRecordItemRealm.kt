package com.joshdev.smartpocket.repository.entities.arching

import com.joshdev.smartpocket.domain.arching.RecordItem
import com.joshdev.smartpocket.repository.mappers.ToData
import io.realm.kotlin.types.RealmObject
import io.realm.kotlin.types.annotations.PrimaryKey
import org.mongodb.kbson.ObjectId

class ArchingRecordItemRealm : RealmObject, ToData<RecordItem> {
    @PrimaryKey
    var id: ObjectId = ObjectId()
    var product: ArchingProductRealm? = null
    var quantity: Int = 0

    override fun toData(): RecordItem {
        return RecordItem(
            id = id.toHexString(),
            product = product?.toData(),
            quantity = quantity
        )
    }
}