package com.github.mejiomah17.yaksb.sqlite.android

import com.github.mejiomah17.yaksb.dsl.TransactionFactoryTest
import org.sqlite.database.sqlite.SQLiteException

class AndroidTransactionFactoryTest : SqliteAndroidTest(), TransactionFactoryTest {
    override fun exception(): Exception {
        return SQLiteException()
    }
}
