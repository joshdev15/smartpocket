package com.joshdev.smartpocket.repository.database.room.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.MapColumn
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.joshdev.smartpocket.domain.arching.ArcRecordItem
import kotlinx.coroutines.flow.Flow

@Dao
interface ArchingRecordItemDao {
    @Insert
    suspend fun insert(item: ArcRecordItem)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(items: List<ArcRecordItem>)

    @Update
    suspend fun update(item: ArcRecordItem)

    @Delete
    suspend fun delete(item: ArcRecordItem)

    @Query("SELECT * FROM arcRecordItem WHERE id = :recordItemId")
    suspend fun getRecordItemById(recordItemId: Long): ArcRecordItem?

    @Query("SELECT * FROM arcRecordItem ORDER BY id DESC")
    fun getAllRecordItems(): Flow<List<ArcRecordItem>>

    @Query("SELECT * FROM arcRecordItem where recordId = :recordId")
    fun getAllRecordItemsByRecordId(recordId: Long): Flow<List<ArcRecordItem>>

    @Query("select * from arcRecordItem where recordId = :recordId and productId in (:productId)")
    fun getAllByRecordIdAndProductId(recordId: Long, productId: List<Long>): Flow<List<ArcRecordItem>>

    @Query("DELETE FROM arcRecordItem")
    suspend fun deleteAllRecordItems()

    @Query(
        """
        select c.name as name, sum(item.quantity * p.price) as sum from arcRecordItem as item
        inner join arcProduct as p on item.productId = p.id
        inner join arcCategory as c on p.categoryId = c.id
        inner join arcRecord as r on item.recordId = r.id
        where r.id = :recordId
        """
    )
    fun getTotals(recordId: Long): Flow<Map<@MapColumn(columnName = "name") String?, @MapColumn(columnName = "sum") Double?>>
}