package com.joshdev.smartpocket.repository.database.room.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.MapColumn
import androidx.room.Query
import androidx.room.Update
import com.joshdev.smartpocket.domain.arching.ArcRecord
import kotlinx.coroutines.flow.Flow

@Dao
interface ArchingRecordDao {
    @Insert
    suspend fun insert(arcRecord: ArcRecord)

    @Update
    suspend fun update(arcRecord: ArcRecord)

    @Delete
    suspend fun delete(arcRecord: ArcRecord)

    @Query("SELECT * FROM arcRecord WHERE id = :recordId")
    suspend fun getRecordById(recordId: Long): ArcRecord?

    @Query("SELECT * FROM arcRecord WHERE archingId = :archingId")
    fun getRecordByArchingId(archingId: Long): Flow<List<ArcRecord>>

    @Query("SELECT * FROM arcRecord ORDER BY id DESC")
    fun getAllRecords(): Flow<List<ArcRecord>>

    @Query("DELETE FROM arching")
    suspend fun deleteAllRecords()

    @Query(
        """
        select c.name as name, sum(item.quantity * p.price) as sum from arcRecordItem as item
        inner join arcProduct as p on item.productId = p.id
        inner join arcCategory as c on p.categoryId = c.id
        inner join arcRecord as r on item.recordId = r.id
        inner join arching as a on r.archingId = a.id
        where a.id = :archingId
        """
    )
    fun getTotals(archingId: Long): Flow<Map<@MapColumn(columnName = "name") String?, @MapColumn(columnName = "sum") Double?>>
}