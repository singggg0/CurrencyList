package com.sws.currencylist.data.database

import android.content.Context
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.sws.currencylist.data.database.dao.CurrencyInfoDao
import com.sws.currencylist.data.database.entity.CurrencyInfoEntity
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import java.io.IOException

@RunWith(AndroidJUnit4::class)
class CurrencyDataBaseTest {
    private lateinit var currencyInfoDao: CurrencyInfoDao
    private lateinit var db: CurrencyDataBase

    @Before
    fun createDb() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        db = CurrencyDataBase.getDatabase(context)
        currencyInfoDao = db.currencyInfoDao()
    }

    @After
    @Throws(IOException::class)
    fun closeDb() {
        db.close()
    }

    @Test
    @Throws(Exception::class)
    fun insertCurrencies() {
        val newCurrencies = listOf(
            CurrencyInfoEntity("BTC", "Bitcoin", "BTC", null),
            CurrencyInfoEntity("HKD", "Hong Kong Dollar", "$", "HKD"),
        )
        runTest {
            currencyInfoDao.insertAll(*newCurrencies.toTypedArray())

            val updatedCurrencies = currencyInfoDao.getAll()
            assertEquals(updatedCurrencies, newCurrencies)
        }
    }

    @Test
    @Throws(Exception::class)
    fun clearCurrencies() {
        runTest {
            currencyInfoDao.clear()

            val updatedCurrencies = currencyInfoDao.getAll()
            assertEquals(updatedCurrencies, emptyList<CurrencyInfoEntity>())
        }
    }
}