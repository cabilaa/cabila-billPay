package com.cabila0046.assessment1.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import com.cabila0046.assessment1.model.Pinjaman
import kotlinx.coroutines.InternalCoroutinesApi
import kotlinx.coroutines.internal.synchronized

val MIGRATION_1_3 = object : Migration(1, 3) {
    override fun migrate(db: SupportSQLiteDatabase) {
        db.execSQL("ALTER TABLE pinjaman ADD COLUMN dihapus INTEGER NOT NULL DEFAULT 0")
    }
}

@Database(entities = [Pinjaman::class], version = 3, exportSchema = false)
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
                    ).addMigrations(MIGRATION_1_3)
                        .build()
                    INSTANCE = instance
                }
                return instance
            }
        }
    }
}


