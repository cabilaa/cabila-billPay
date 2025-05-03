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

    @Query("SELECT * FROM pinjaman WHERE dihapus = 0 ORDER BY tanggal DESC")
    fun getPinjaman(): Flow<List<Pinjaman>>

    @Query("SELECT * FROM pinjaman WHERE id = :id")
    suspend fun getPinjamanById(id: Long): Pinjaman?

    @Query("DELETE  FROM pinjaman WHERE id = :id")
    suspend fun deleteById(id: Long)

    @Query("UPDATE pinjaman SET dihapus = 1 WHERE id = :id")
    suspend fun softDeleteById(id: Long)

    @Query("UPDATE pinjaman SET dihapus = 0 WHERE id = :id")
    suspend fun undoDeleteById(id: Long)

    @Query("SELECT * FROM pinjaman WHERE dihapus = 1 ORDER BY tanggal DESC")
    fun getPinjamanDihapus(): Flow<List<Pinjaman>>




}
