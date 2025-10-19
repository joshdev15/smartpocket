package com.joshdev.smartpocket.domain.models

import io.realm.kotlin.types.RealmObject
import io.realm.kotlin.types.annotations.PrimaryKey
import org.mongodb.kbson.ObjectId

data class Product(
    val id: String = "",
    val invoiceId: String = "",
    val name: String,
    val quantity: Int,
    val cost: Double,
    val currency: Int?,
    val customRate: Double,
    val order: Int,
    val categoryId: Int? = null,
    val subCategoryId: Int? = null,
    val baseCost: Double,
) {
    fun toProductRealm(): ProductRealm {
        val productRealm = ProductRealm()
        productRealm.id = ObjectId(id)

        productRealm.invoiceId = invoiceId
        productRealm.name = name
        productRealm.quantity = quantity
        productRealm.cost = cost
        productRealm.currency = currency
        productRealm.customRate = customRate
        productRealm.order = order
        productRealm.categoryId = categoryId
        productRealm.subCategoryId = subCategoryId
        productRealm.baseCost = baseCost

        productRealm
        return productRealm
    }
}

class ProductRealm : RealmObject {
    @PrimaryKey
    var id: ObjectId = ObjectId.invoke()
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

    fun toProduct(): Product {
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
