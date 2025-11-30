package com.joshdev.smartpocket.domain.ledger

import com.joshdev.smartpocket.repository.entities.ledger.LedgerProductRealm
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
    fun toRealm(): LedgerProductRealm {
        val productRealm = LedgerProductRealm().apply {
            id = if (this@Product.id != "") ObjectId(this@Product.id) else ObjectId()
            invoiceId = this@Product.invoiceId
            name = this@Product.name
            quantity = this@Product.quantity
            cost = this@Product.cost
            currency = this@Product.currency
            customRate = this@Product.customRate
            order = this@Product.order
            categoryId = this@Product.categoryId
            subCategoryId = this@Product.subCategoryId
            baseCost = this@Product.baseCost
        }

        return productRealm
    }
}