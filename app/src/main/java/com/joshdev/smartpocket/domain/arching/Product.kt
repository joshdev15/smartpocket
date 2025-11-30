package com.joshdev.smartpocket.domain.arching

import com.joshdev.smartpocket.repository.mappers.ToRealm
import com.joshdev.smartpocket.repository.entities.arching.ArchingProductRealm
import org.mongodb.kbson.ObjectId

data class Product(
    val id: String = "",
    val name: String = "",
    val categoryId: String = "",
    val subCategoryId: String = "",
    val price: Double = 0.0,
) : ToRealm<ArchingProductRealm> {
    override fun toRealm(): ArchingProductRealm {
        return ArchingProductRealm().apply {
            id = if (this@Product.id != "") ObjectId(this@Product.id) else ObjectId()
            name = this@Product.name
            categoryId = this@Product.categoryId
            subCategoryId = this@Product.subCategoryId
            price = this@Product.price
        }
    }
}