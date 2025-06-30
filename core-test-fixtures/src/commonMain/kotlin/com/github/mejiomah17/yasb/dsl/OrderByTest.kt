package com.github.mejiomah17.yasb.dsl

import com.github.mejiomah17.yasb.core.DatabaseDialect
import com.github.mejiomah17.yasb.core.SupportsLimit
import com.github.mejiomah17.yasb.core.dsl.asc
import com.github.mejiomah17.yasb.core.dsl.desc
import com.github.mejiomah17.yasb.core.dsl.eq
import com.github.mejiomah17.yasb.core.dsl.from
import com.github.mejiomah17.yasb.core.dsl.limit
import com.github.mejiomah17.yasb.core.dsl.orderBy
import com.github.mejiomah17.yasb.core.dsl.select
import com.github.mejiomah17.yasb.core.transaction.TransactionAtLeastRepeatableRead
import com.github.mejiomah17.yasb.core.transaction.TransactionFactory
import com.github.mejiomah17.yasb.core.where
import io.kotest.matchers.collections.shouldHaveAtLeastSize
import io.kotest.matchers.collections.shouldHaveSize
import io.kotest.matchers.shouldBe
import org.junit.Test

interface OrderByTest<
        TABLE : TestTable<TABLE, DRIVER_DATA_SOURCE, DRIVER_STATEMENT>,
        DRIVER_DATA_SOURCE,
        DRIVER_STATEMENT,
        DIALECT,
        TRANSACTION : TransactionAtLeastRepeatableRead<DRIVER_DATA_SOURCE, DRIVER_STATEMENT>,
        > : SqlTest where DIALECT : DatabaseDialect<DRIVER_DATA_SOURCE, DRIVER_STATEMENT>,
                          DIALECT : SupportsLimit {
    @Test
    fun `order_by_generates_correct_sql`() {
        transactionFactory().repeatableRead {
            select(tableTest().a)
                .from(tableTest())
                .orderBy(tableTest().b)
                .sql() shouldBe "SELECT test.a FROM test ORDER BY test.b"
        }
    }

    @Test
    fun `order_by_called_after_where`() {
        transactionFactory().repeatableRead {
            select(tableTest().a)
                .from(tableTest())
                .where { tableTest().a.eq("the a") }
                .orderBy(tableTest().b)
                .execute()
        }
    }

    @Test
    fun `order_by_returns_sorted`() {
        transactionFactory().repeatableRead {
            val result = select(tableTest().a)
                .from(tableTest())
                .orderBy(tableTest().b)
                .execute()
            result shouldHaveAtLeastSize 2
            result.sortedBy { it[tableTest().b] } shouldBe result
        }
    }

    @Test
    fun `order_by_asc_returns_sorted`() {
        transactionFactory().repeatableRead {
            val result = select(tableTest().a)
                .from(tableTest())
                .orderBy(tableTest().b.asc())
                .execute()
            result shouldHaveAtLeastSize 2
            result.sortedBy { it[tableTest().b] } shouldBe result
        }
    }

    @Test
    fun `order_by_desc_returns_sorted`() {
        transactionFactory().repeatableRead {
            val result = select(tableTest().a)
                .from(tableTest())
                .orderBy(tableTest().b.desc())
                .execute()
            result shouldHaveAtLeastSize 2
            result.sortedByDescending { it[tableTest().b] } shouldBe result
        }
    }


    fun transactionFactory(): TransactionFactory<DRIVER_DATA_SOURCE, DRIVER_STATEMENT, DIALECT, *, *, TRANSACTION, *>

    fun tableTest(): TABLE
}
