package com.joshdev.smartpocket.domain.models

import com.joshdev.smartpocket.repository.models.ProductRealm
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
    fun toRealm(): ProductRealm {
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

        return productRealm
    }
}