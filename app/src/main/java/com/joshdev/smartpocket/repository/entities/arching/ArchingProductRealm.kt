package com.joshdev.smartpocket.repository.entities.arching

import com.joshdev.smartpocket.domain.arching.Product
import com.joshdev.smartpocket.repository.mappers.ToData
import io.realm.kotlin.types.RealmObject
import io.realm.kotlin.types.annotations.PrimaryKey
import org.mongodb.kbson.ObjectId

class ArchingProductRealm : RealmObject, ToData<Product> {
    @PrimaryKey
    var id: ObjectId = ObjectId()
    var name: String = ""
    var categoryId: String = ""
    var subCategoryId: String = ""
    var price: Double = 0.0

    override fun toData(): Product {
        return Product(
            id = id.toHexString(),
            name = name,
            categoryId = categoryId,
            subCategoryId = subCategoryId,
            price = price
        )
    }
}
