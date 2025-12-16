package com.joshdev.smartpocket.repository.database.room

import androidx.room.Database
import androidx.room.RoomDatabase
import com.joshdev.smartpocket.domain.arching.ArcCategory
import com.joshdev.smartpocket.domain.arching.ArcProduct
import com.joshdev.smartpocket.domain.arching.ArcRecord
import com.joshdev.smartpocket.domain.arching.ArcRecordItem
import com.joshdev.smartpocket.domain.arching.Arching
import com.joshdev.smartpocket.domain.currency.Currency
import com.joshdev.smartpocket.domain.ledger.LedCategory
import com.joshdev.smartpocket.domain.ledger.LedProduct
import com.joshdev.smartpocket.domain.ledger.LedTransaction
import com.joshdev.smartpocket.domain.ledger.Ledger
import com.joshdev.smartpocket.repository.database.room.dao.ArchingCategoryDao
import com.joshdev.smartpocket.repository.database.room.dao.ArchingDao
import com.joshdev.smartpocket.repository.database.room.dao.ArchingProductDao
import com.joshdev.smartpocket.repository.database.room.dao.ArchingRecordDao
import com.joshdev.smartpocket.repository.database.room.dao.ArchingRecordItemDao
import com.joshdev.smartpocket.repository.database.room.dao.CurrencyDao
import com.joshdev.smartpocket.repository.database.room.dao.LedgerCategoryDao
import com.joshdev.smartpocket.repository.database.room.dao.LedgerDao
import com.joshdev.smartpocket.repository.database.room.dao.LedgerProductDao
import com.joshdev.smartpocket.repository.database.room.dao.LedgerTransactionsDao

@Database(
    entities = [Ledger::class, LedTransaction::class, LedProduct::class, LedCategory::class, Arching::class, ArcRecord::class, ArcRecordItem::class, ArcProduct::class, ArcCategory::class, Currency::class],
    version = 2,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {
    // Ledger
    abstract fun ledgerDao(): LedgerDao
    abstract fun ledTransactionDao(): LedgerTransactionsDao
    abstract fun ledCategoryDao(): LedgerCategoryDao
    abstract fun ledProductDao(): LedgerProductDao

    // Arching
    abstract fun archingDao(): ArchingDao
    abstract fun archingRecordDao(): ArchingRecordDao
    abstract fun archingRecordItemDao(): ArchingRecordItemDao
    abstract fun archingCategoryDao(): ArchingCategoryDao
    abstract fun archingProductDao(): ArchingProductDao

    abstract fun currencyDao(): CurrencyDao
}
