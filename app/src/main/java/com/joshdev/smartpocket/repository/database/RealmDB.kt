package com.joshdev.smartpocket.repository.database

import com.joshdev.smartpocket.repository.models.ArchingCategoryRealm
import com.joshdev.smartpocket.repository.models.ArchingProductRealm
import com.joshdev.smartpocket.repository.models.ArchingRealm
import com.joshdev.smartpocket.repository.models.CurrencyRealm
import com.joshdev.smartpocket.repository.models.LedgerCategoryRealm
import com.joshdev.smartpocket.repository.models.LedgerRealm
import com.joshdev.smartpocket.repository.models.LedgerProductRealm
import com.joshdev.smartpocket.repository.models.LedgerTransactionRealm
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
                        // Currency Classes
                        CurrencyRealm::class,

                        // Ledger Classes
                        LedgerRealm::class,
                        LedgerCategoryRealm::class,
                        LedgerTransactionRealm::class,
                        LedgerProductRealm::class,

                        // Arching Classes
                        ArchingRealm::class,
                        ArchingCategoryRealm::class,
                        ArchingProductRealm::class
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


