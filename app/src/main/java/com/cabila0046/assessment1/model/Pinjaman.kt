package com.cabila0046.assessment1.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "pinjaman")
data class Pinjaman(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0L,
    val nama: String,
    val total: String,
    val bunga: String,
    val bulan: String,
    val tanggal: String,
    val dihapus: Boolean = false
)
