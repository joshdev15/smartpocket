package com.joshdev.smartpocket.domain.models

import com.joshdev.smartpocket.repository.mappers.ToRealm
import com.joshdev.smartpocket.repository.entities.ArchingProductRealm
import org.mongodb.kbson.ObjectId

data class ArchingProduct(
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
) : ToRealm<ArchingProductRealm> {
    override fun toRealm(): ArchingProductRealm {
        return ArchingProductRealm().apply {
            id = if (this@ArchingProduct.id != "") ObjectId(this@ArchingProduct.id) else ObjectId()
            invoiceId = this@ArchingProduct.invoiceId
            name = this@ArchingProduct.name
            quantity = this@ArchingProduct.quantity
            cost = this@ArchingProduct.cost
            currency = this@ArchingProduct.currency
            customRate = this@ArchingProduct.customRate
            order = this@ArchingProduct.order
            categoryId = this@ArchingProduct.categoryId
            subCategoryId = this@ArchingProduct.subCategoryId
            baseCost = this@ArchingProduct.baseCost
        }
    }
}