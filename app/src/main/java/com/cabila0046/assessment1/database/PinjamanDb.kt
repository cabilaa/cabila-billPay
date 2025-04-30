package com.cabila0046.assessment1.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.cabila0046.assessment1.model.Pinjaman
import kotlinx.coroutines.InternalCoroutinesApi
import kotlinx.coroutines.internal.synchronized

@Database(entities = [Pinjaman::class], version = 1, exportSchema = false)
abstract class PinjamanDb : RoomDatabase() {
    abstract val dao: PinjamanDao

    companion object {

        @Volatile
        private var INSTANCE: PinjamanDb? = null

        @OptIn(InternalCoroutinesApi::class)
        fun getInstance(context: Context): PinjamanDb {
            synchronized(this) {
                var instance = INSTANCE

                if (instance == null) {
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        PinjamanDb::class.java,
                        "pinjaman.db"
                    ).build()
                    INSTANCE = instance
                }
                return instance
            }
        }
    }
}


