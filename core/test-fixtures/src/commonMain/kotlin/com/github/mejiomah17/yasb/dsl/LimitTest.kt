package com.github.mejiomah17.yasb.dsl

import com.github.mejiomah17.yasb.core.DatabaseDialect
import com.github.mejiomah17.yasb.core.SupportsLimit
import com.github.mejiomah17.yasb.core.dsl.eq
import com.github.mejiomah17.yasb.core.dsl.from
import com.github.mejiomah17.yasb.core.dsl.limit
import com.github.mejiomah17.yasb.core.dsl.select
import com.github.mejiomah17.yasb.core.transaction.TransactionAtLeastRepeatableRead
import com.github.mejiomah17.yasb.core.transaction.TransactionFactory
import com.github.mejiomah17.yasb.core.where
import io.kotest.matchers.collections.shouldHaveSize
import io.kotest.matchers.shouldBe
import org.junit.Test

interface LimitTest<
    TABLE : TestTable<TABLE, DRIVER_DATA_SOURCE, DRIVER_STATEMENT>,
    DRIVER_DATA_SOURCE,
    DRIVER_STATEMENT,
    DIALECT,
    TRANSACTION : TransactionAtLeastRepeatableRead<DRIVER_DATA_SOURCE, DRIVER_STATEMENT>
    > : SqlTest where DIALECT : DatabaseDialect<DRIVER_DATA_SOURCE, DRIVER_STATEMENT>,
                      DIALECT : SupportsLimit {
    @Test
    fun `limit_generates_correct_sql`() {
        transactionFactory().repeatableRead {
            select(tableTest().a)
                .from(tableTest())
                .limit(1)
                .buildSelectQuery()
                .sqlDefinition shouldBe "SELECT test.a FROM test LIMIT 1"
        }
    }

    @Test
    fun `limit_1_return_single_record`() {
        transactionFactory().repeatableRead {
            select(tableTest().a)
                .from(tableTest())
                .limit(1)
                .execute() shouldHaveSize 1
        }
    }

    @Test
    fun `limit_called_after_where`() {
        transactionFactory().repeatableRead {
            select(tableTest().a)
                .from(tableTest())
                .where { tableTest().a.eq("the a") }
                .limit(1)
                .execute()
        }
    }

    fun transactionFactory(): TransactionFactory<DRIVER_DATA_SOURCE, DRIVER_STATEMENT, DIALECT, *, *, TRANSACTION, *>
    fun tableTest(): TABLE
}
