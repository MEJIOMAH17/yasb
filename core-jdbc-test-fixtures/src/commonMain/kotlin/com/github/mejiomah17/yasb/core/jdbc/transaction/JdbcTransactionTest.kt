package com.github.mejiomah17.yasb.core.jdbc.transaction

import com.github.mejiomah17.yasb.core.dsl.from
import com.github.mejiomah17.yasb.core.dsl.insertInto
import com.github.mejiomah17.yasb.core.dsl.select
import com.github.mejiomah17.yasb.dsl.TestTable
import io.kotest.matchers.collections.shouldBeEmpty
import io.kotest.matchers.shouldBe
import org.awaitility.Awaitility.await
import org.junit.Before
import org.junit.Test
import java.sql.PreparedStatement
import java.sql.ResultSet
import java.util.concurrent.atomic.AtomicReference
import kotlin.concurrent.thread

interface JdbcTransactionTest<TABLE : TestTable<TABLE, ResultSet, PreparedStatement>> {
    @Before
    fun init() {
        transactionFactory().repeatableRead {
            this.connection.prepareStatement("DELETE from test").execute()
        }
    }

    @Test
    fun commits_transaction() {
        val activeThread = AtomicReference(Thread.READER)
        thread {
            transactionFactory().readCommitted {
                await().until { activeThread.get() == Thread.WRITER }
                insertInto(tableTest()) {
                    it[tableTest().a] = "abc"
                }.execute()
                commit()
                activeThread.set(Thread.READER)
                await().until { activeThread.get() == Thread.WRITER }
            }
        }
        transactionFactory().readCommitted {
            select(tableTest().a).from(tableTest()).execute().shouldBeEmpty()
        }
        activeThread.set(Thread.WRITER)
        transactionFactory().readCommitted {
            await().until { activeThread.get() == Thread.READER }
            select(tableTest().a).from(tableTest()).execute().single()[tableTest().a].shouldBe("abc")
            activeThread.set(Thread.WRITER)
        }
    }

    @Test
    fun commits_transaction_on_complete() {
        transactionFactory().readCommitted {
            insertInto(tableTest()) {
                it[tableTest().a] = "abc"
            }.execute()
        }
        transactionFactory().readCommitted {
            select(tableTest().a).from(tableTest()).execute().single()[tableTest().a].shouldBe("abc")
        }
    }

    @Test
    fun rollbacks_transaction() {
        val activeThread = AtomicReference(Thread.READER)
        thread {
            transactionFactory().readCommitted {
                await().until { activeThread.get() == Thread.WRITER }
                insertInto(tableTest()) {
                    it[tableTest().a] = "abc"
                }.execute()
                rollback()
                activeThread.set(Thread.READER)
                await().until { activeThread.get() == Thread.WRITER }
            }
        }
        transactionFactory().readCommitted {
            select(tableTest().a).from(tableTest()).execute().shouldBeEmpty()
        }
        activeThread.set(Thread.WRITER)
        transactionFactory().readCommitted {
            await().until { activeThread.get() == Thread.READER }
            select(tableTest().a).from(tableTest()).execute().shouldBeEmpty()
            activeThread.set(Thread.WRITER)
        }
    }

    @Test
    fun rollbacks_transaction_on_exception() {
        kotlin.runCatching {
            transactionFactory().readCommitted {
                insertInto(tableTest()) {
                    it[tableTest().a] = "abc"
                }.execute()
                throw IllegalStateException()
            }
        }
        transactionFactory().readCommitted {
            select(tableTest().a).from(tableTest()).execute().shouldBeEmpty()
        }
    }

    enum class Thread {
        WRITER,
        READER
    }

    fun transactionFactory(): JdbcTransactionFactory<*>
    fun tableTest(): TABLE
}
