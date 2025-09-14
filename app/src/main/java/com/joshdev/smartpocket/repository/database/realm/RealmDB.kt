package com.joshdev.smartpocket.repository.database.realm

import android.content.Context
import com.joshdev.smartpocket.domain.models.CategoryRealms
import com.joshdev.smartpocket.domain.models.LedgerRealm
import io.realm.kotlin.Realm
import io.realm.kotlin.RealmConfiguration
import io.realm.kotlin.types.TypedRealmObject
import kotlin.reflect.KClass

object RealmDBSingleton {
    @Volatile
    private var INSTANCE: Realm? = null

    @Suppress("UNCHECKED_CAST")
    private fun initializeRealm(): Realm {
        val config = RealmConfiguration.create(
            schema = setOf(
                LedgerRealm::class,
                CategoryRealms::class,
            ) as Set<KClass<out TypedRealmObject>>
        )

        return Realm.open(config)
    }

    fun getInstance(context: Context): Realm {
        if (INSTANCE == null) {
            INSTANCE = initializeRealm()
        }

        return INSTANCE!!
    }
}


