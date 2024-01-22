package com.github.mejiomah17.yasb.sqlite.android

import com.github.mejiomah17.yasb.dsl.TransactionFactoryTest
import org.sqlite.database.sqlite.SQLiteException

class AndroidTransactionFactoryTest : SqliteAndroidTest(), TransactionFactoryTest {
    override fun exception(): Exception {
        return SQLiteException()
    }
}
