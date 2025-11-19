package com.joshdev.smartpocket.domain.models

import com.joshdev.smartpocket.repository.models.LedgerProductRealm
import org.mongodb.kbson.ObjectId

data class LedgerProduct(
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
        val ledgerProductRealm = LedgerProductRealm().apply {
            id = if (this@LedgerProduct.id != "") ObjectId(this@LedgerProduct.id) else ObjectId()
            invoiceId = this@LedgerProduct.invoiceId
            name = this@LedgerProduct.name
            quantity = this@LedgerProduct.quantity
            cost = this@LedgerProduct.cost
            currency = this@LedgerProduct.currency
            customRate = this@LedgerProduct.customRate
            order = this@LedgerProduct.order
            categoryId = this@LedgerProduct.categoryId
            subCategoryId = this@LedgerProduct.subCategoryId
            baseCost = this@LedgerProduct.baseCost
        }

        return ledgerProductRealm
    }
}