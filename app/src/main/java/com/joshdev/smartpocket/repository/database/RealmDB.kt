package com.joshdev.smartpocket.repository.database

import com.joshdev.smartpocket.repository.database.entities.ArchingCategoryRealm
import com.joshdev.smartpocket.repository.database.entities.ArchingProductRealm
import com.joshdev.smartpocket.repository.database.entities.ArchingRealm
import com.joshdev.smartpocket.repository.database.entities.ArchingRecordRealm
import com.joshdev.smartpocket.repository.database.entities.CurrencyRealm
import com.joshdev.smartpocket.repository.database.entities.LedgerCategoryRealm
import com.joshdev.smartpocket.repository.database.entities.LedgerRealm
import com.joshdev.smartpocket.repository.database.entities.LedgerProductRealm
import com.joshdev.smartpocket.repository.database.entities.LedgerTransactionRealm
import io.realm.kotlin.Realm
import io.realm.kotlin.RealmConfiguration
import io.realm.kotlin.ext.query
import io.realm.kotlin.types.TypedRealmObject
import kotlin.reflect.KClass

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
            ArchingCategoryRealm::class,
            ArchingProductRealm::class
        ) as Set<KClass<out TypedRealmObject>>

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


