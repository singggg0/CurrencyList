package com.sws.currencylist.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.sws.currencylist.data.database.dao.CurrencyInfoDao
import com.sws.currencylist.data.database.entity.CurrencyInfoEntity

@Database(
    entities = [CurrencyInfoEntity::class],
    version = 1
)
abstract class CurrencyDataBase : RoomDatabase() {
    abstract fun currencyInfoDao(): CurrencyInfoDao

    companion object {
        @Volatile
        private var INSTANCE: CurrencyDataBase? = null

        fun getDatabase(context: Context): CurrencyDataBase {
            return INSTANCE ?: synchronized(this) {
                return Room.databaseBuilder(context.applicationContext, CurrencyDataBase::class.java, "currency.db")
                    .build()
                    .also { INSTANCE = it }
            }
        }
    }
}