package com.joshdev.smartpocket.repository.database

import com.joshdev.smartpocket.domain.ledger.Transaction
import com.joshdev.smartpocket.repository.mappers.ToData
import com.joshdev.smartpocket.repository.mappers.ToRealm
import com.joshdev.smartpocket.repository.entities.ledger.LedgerRealm
import com.joshdev.smartpocket.repository.entities.ledger.LedgerTransactionRealm
import io.realm.kotlin.Realm
import io.realm.kotlin.UpdatePolicy
import io.realm.kotlin.ext.query
import io.realm.kotlin.types.RealmObject
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import org.mongodb.kbson.ObjectId
import javax.inject.Inject

class Operations @Inject constructor(val db: Realm) {
    inline fun <D, reified R> observe(): Flow<List<D>>
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

    inline fun <D, reified R> observeWithQuery(query: String, args: Any? = null): Flow<List<D>>
            where D : ToRealm<R>, R : RealmObject, R : ToData<D> {

        val values = db.query<R>(query, args)
            .asFlow()
            .map { results ->
                results.list.map {
                    it.toData()
                }
            }


        return values
    }

    inline fun <D, reified R> get(query: String, args: Any? = null): Flow<List<D>>
            where D : ToRealm<R>, R : RealmObject, R : ToData<D> {

        val values = db.query<R>(query, args)
            .asFlow()
            .map { results ->
                results.list.map {
                    it.toData()
                }
            }

        return values
    }

    inline fun <D, reified R> getAllWithQuery(query: String, vararg args: Any?): Flow<List<D>>
            where D : ToRealm<R>, R : RealmObject, R : ToData<D> {

        val values = db.query<R>(query, *args)
            .asFlow()
            .map { results ->
                results.list.map {
                    it.toData()
                }
            }

        return values
    }

    inline fun <D, reified R> add(item: D)
            where D : ToRealm<R>, R : RealmObject, R : ToData<D> {

        db.writeBlocking {
            copyToRealm(item.toRealm())
        }
    }

    suspend inline fun <D, reified R> addAll(items: List<D>)
            where D : ToRealm<R>, R : RealmObject, R : ToData<D> {

        val realmItems = items.map { it.toRealm() }

        db.write {
            realmItems.forEach { item ->
                copyToRealm(item, UpdatePolicy.ALL)
            }
        }
    }

    suspend inline fun update(itemId: String) {
        val objectId = ObjectId(itemId)

        val ledger = db.query<LedgerRealm>("id == $0", objectId).first().find()
        val allTransactions = db.query<LedgerTransactionRealm>("ledgerId == $0", itemId).find()

        if (ledger != null) {
            var totalAmount = ledger.initialCapital

            allTransactions.forEach { transactionRealm ->
                val transaction = transactionRealm.toData()
                if (transaction.type == Transaction.TxType.INCOME) {
                    totalAmount += transaction.amount
                } else {
                    totalAmount -= transaction.amount
                }
            }

            db.write {
                findLatest(ledger)?.let { latestLedger ->
                    latestLedger.totalBalance = totalAmount
                }
            }
        }
    }

    suspend inline fun <D, reified R> delete(itemId: String)
            where D : ToRealm<R>, R : RealmObject, R : ToData<D> {

        val id = ObjectId(itemId)
        db.write {
            val itemToDelete = this.query<R>("id == $0", id).first().find()
            itemToDelete?.let {
                delete(it)
            }
        }
    }


    suspend inline fun <D, reified R> deleteAll(query: String, itemId: String)
            where D : ToRealm<R>, R : RealmObject, R : ToData<D> {

        val id = ObjectId(itemId)
        db.write {
            val itemToDelete = this.query<R>(query, id).first().find()
            itemToDelete?.let {
                delete(it)
            }
        }
    }
}