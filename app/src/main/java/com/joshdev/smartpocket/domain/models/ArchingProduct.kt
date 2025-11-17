package com.joshdev.smartpocket.domain.models

import com.joshdev.smartpocket.repository.interfaces.ToData
import com.joshdev.smartpocket.repository.interfaces.ToRealm
import com.joshdev.smartpocket.repository.models.ArchingProductRealm
import com.joshdev.smartpocket.repository.models.LedgerProductRealm
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
        val archingProductRealm = ArchingProductRealm().apply {
            id = ObjectId(this@ArchingProduct.id)
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

        return archingProductRealm
    }
}