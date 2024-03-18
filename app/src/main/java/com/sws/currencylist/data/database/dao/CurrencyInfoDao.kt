package com.sws.currencylist.data.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.sws.currencylist.data.database.entity.CurrencyInfoEntity

@Dao
interface CurrencyInfoDao {
    @Query("SELECT * FROM currencies")
    suspend fun getAll(): List<CurrencyInfoEntity>

    @Query("DELETE FROM currencies")
    suspend fun clear()

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(vararg currencies: CurrencyInfoEntity)
}