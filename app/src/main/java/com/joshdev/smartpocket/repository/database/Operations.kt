package com.joshdev.smartpocket.repository.database

import com.joshdev.smartpocket.repository.interfaces.ToData
import com.joshdev.smartpocket.repository.interfaces.ToRealm
import io.realm.kotlin.Realm
import io.realm.kotlin.ext.query
import io.realm.kotlin.types.RealmObject
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import org.mongodb.kbson.ObjectId
import javax.inject.Inject

class Operations @Inject constructor(val db: Realm) {
    inline fun <D, reified R> observeItems(): Flow<List<D>>
            where D : ToRealm<R>, R : RealmObject, R : ToData<D> {

        val values = db.query<R>()
            .asFlow()
            .map { results ->
                results.list.map {
                    it.toData()
                }
            }


        return values
    }

    inline fun <D, reified R> addItem(item: D)
            where D : ToRealm<R>, R : RealmObject, R : ToData<D> {

        db.writeBlocking {
            copyToRealm(item.toRealm())
        }
    }

    suspend inline fun <D, reified R> deleteItem(itemId: String)
            where D : ToRealm<R>, R : RealmObject, R : ToData<D> {

        val id = ObjectId(itemId)
        db.write {
            val itemToDelete = this.query<R>("id == $0", id).first().find()
            itemToDelete?.let {
                delete(it)
            }
        }
    }
}