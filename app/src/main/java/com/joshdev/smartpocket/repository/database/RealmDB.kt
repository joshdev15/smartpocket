package com.joshdev.smartpocket.repository.database

import com.joshdev.smartpocket.repository.entities.arching.ArchingCategoryRealm
import com.joshdev.smartpocket.repository.entities.arching.ArchingProductRealm
import com.joshdev.smartpocket.repository.entities.arching.ArchingRealm
import com.joshdev.smartpocket.repository.entities.arching.ArchingRecordItemRealm
import com.joshdev.smartpocket.repository.entities.arching.ArchingRecordRealm
import com.joshdev.smartpocket.repository.entities.currency.CurrencyRealm
import com.joshdev.smartpocket.repository.entities.ledger.LedgerCategoryRealm
import com.joshdev.smartpocket.repository.entities.ledger.LedgerRealm
import com.joshdev.smartpocket.repository.entities.ledger.LedgerProductRealm
import com.joshdev.smartpocket.repository.entities.ledger.LedgerTransactionRealm
import io.realm.kotlin.Realm
import io.realm.kotlin.RealmConfiguration
import io.realm.kotlin.ext.query

object RealmDatabase {
    @Volatile
    private var INSTANCE: Realm? = null
    private val config = generateConfig()

    fun generateConfig(): RealmConfiguration {
        val schema = setOf(
            // Currency Classes
            CurrencyRealm::class,

            // Ledger Classes
            LedgerRealm::class,
            LedgerCategoryRealm::class,
            LedgerTransactionRealm::class,
            LedgerProductRealm::class,

            // Arching Classes
            ArchingRealm::class,
            ArchingRecordRealm::class,
            ArchingRecordItemRealm::class,
            ArchingCategoryRealm::class,
            ArchingProductRealm::class
        )

        return RealmConfiguration.Builder(schema)
            .name("smartpocket.realm")
            .deleteRealmIfMigrationNeeded()
            .schemaVersion(1)
            .build()
    }

    fun createInstance() {
        INSTANCE = Realm.open(config)
    }

    fun getInstance(): Realm {
        if (INSTANCE == null) {
            createInstance()
        }

        initializeDefaultData(INSTANCE!!)

        return INSTANCE!!
    }

    fun nuke() {
        if (INSTANCE != null && !INSTANCE!!.isClosed()) {
            INSTANCE!!.close()
        }
        INSTANCE = null

        try {
            Realm.deleteRealm(config)
            println("☢️ BASE DE DATOS ELIMINADA FÍSICAMENTE ☢️")
        } catch (e: Exception) {
            println("Error al eliminar la base de datos: ${e.message}")
        }
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


