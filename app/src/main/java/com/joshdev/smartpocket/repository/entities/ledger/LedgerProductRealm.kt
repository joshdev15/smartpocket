package com.joshdev.smartpocket.repository.entities.ledger

import com.joshdev.smartpocket.domain.ledger.Product
import io.realm.kotlin.types.RealmObject
import io.realm.kotlin.types.annotations.PrimaryKey
import org.mongodb.kbson.ObjectId

class LedgerProductRealm : RealmObject {
    @PrimaryKey
    var id: ObjectId = ObjectId()
    var invoiceId: String = ""
    var name: String = ""
    var quantity: Int = 0
    var cost: Double = 0.0
    var currency: Int? = null
    var customRate: Double = 0.0
    var order: Int = 0
    var categoryId: Int? = null
    var subCategoryId: Int? = null
    var baseCost: Double = 0.0

    fun toData(): Product {
        return Product(
            id = id.toHexString(),
            invoiceId = invoiceId,
            name = name,
            quantity = quantity,
            cost = cost,
            currency = currency,
            customRate = customRate,
            order = order,
            categoryId = categoryId,
            subCategoryId = subCategoryId,
            baseCost = baseCost,
        )
    }
}
