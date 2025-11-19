package com.joshdev.smartpocket.repository.database

import android.util.Log
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
    private val config = RealmConfiguration
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

    fun createInstance() {
        config.deleteRealmIfMigrationNeeded
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


