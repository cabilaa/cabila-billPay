package com.cabila0046.assessment1.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.cabila0046.assessment1.model.Pinjaman
import kotlinx.coroutines.flow.Flow

@Dao
interface PinjamanDao {

    @Insert
    suspend fun insert(pinjaman: Pinjaman)

    @Update
    suspend fun update(pinjaman: Pinjaman)

    @Query("SELECT * FROM pinjaman ORDER BY tanggal DESC")
    fun getPinjaman(): Flow<List<Pinjaman>>

    @Query("SELECT * FROM pinjaman WHERE id = :id")
    suspend fun getPinjamanById(id: Long): Pinjaman?

    @Query("DELETE  FROM pinjaman WHERE id = :id")
    suspend fun deleteById(id: Long)
}
