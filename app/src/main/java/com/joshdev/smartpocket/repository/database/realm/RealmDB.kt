package com.joshdev.smartpocket.repository.database.realm

import com.joshdev.smartpocket.domain.models.CategoryRealms
import com.joshdev.smartpocket.domain.models.CurrencyRealm
import com.joshdev.smartpocket.domain.models.LedgerRealm
import com.joshdev.smartpocket.domain.models.ProductRealm
import com.joshdev.smartpocket.domain.models.TransactionRealm
import io.realm.kotlin.Realm
import io.realm.kotlin.RealmConfiguration
import io.realm.kotlin.ext.query
import io.realm.kotlin.types.TypedRealmObject
import kotlin.reflect.KClass

object RealmDatabase {
    @Volatile
    private var INSTANCE: Realm? = null

    fun getInstance(): Realm {
        if (INSTANCE == null) {
            val config = RealmConfiguration
                .create(
                    schema = setOf(
                        LedgerRealm::class,
                        TransactionRealm::class,
                        ProductRealm::class,
                        CategoryRealms::class,
                        CurrencyRealm::class,
                    ) as Set<KClass<out TypedRealmObject>>
                )

            config.deleteRealmIfMigrationNeeded

            INSTANCE = Realm.open(config)
        }

        initializeDefaultData(INSTANCE!!)

        return INSTANCE!!
    }

    private fun initializeDefaultData(realm: Realm) {
        realm.writeBlocking {
            val existingCurrency = this.query<CurrencyRealm>("name == $0", "USD")
                .find()
                .firstOrNull()

            if (existingCurrency == null) {
                copyToRealm(
                    CurrencyRealm().apply {
                        name = "USD"
                        symbol = "$"
                        rate = 1.0
                    }
                )
            }
        }
    }
}


