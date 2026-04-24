package com.github.mejiomah17.yaksb.dsl

import com.github.mejiomah17.yaksb.core.DatabaseDialect
import com.github.mejiomah17.yaksb.core.SupportsLimit
import com.github.mejiomah17.yaksb.core.dsl.eq
import com.github.mejiomah17.yaksb.core.dsl.from
import com.github.mejiomah17.yaksb.core.dsl.limit
import com.github.mejiomah17.yaksb.core.dsl.select
import com.github.mejiomah17.yaksb.core.transaction.TransactionAtLeastRepeatableRead
import com.github.mejiomah17.yaksb.core.where
import io.kotest.matchers.collections.shouldHaveSize
import io.kotest.matchers.shouldBe
import kotlin.test.Test

interface LimitTest<
    TABLE : TestTable<TABLE, DRIVER_DATA_SOURCE, DRIVER_STATEMENT>,
    DRIVER_DATA_SOURCE,
    DRIVER_STATEMENT,
    DIALECT,
    TRANSACTION : TransactionAtLeastRepeatableRead<DRIVER_DATA_SOURCE, DRIVER_STATEMENT>,
> : SqlTest where DIALECT : DatabaseDialect<DRIVER_DATA_SOURCE, DRIVER_STATEMENT>,
          DIALECT : SupportsLimit {
    @Test
    fun `limit_generates_correct_sql`() {
        transaction {
            select(tableTest().a)
                .from(tableTest())
                .limit(1)
                .sql() shouldBe "SELECT test.a FROM test LIMIT 1"
        }
    }

    @Test
    fun `limit_1_return_single_record`() {
        transaction {
            select(tableTest().a)
                .from(tableTest())
                .limit(1)
                .execute() shouldHaveSize 1
        }
    }

    @Test
    fun `limit_called_after_where`() {
        transaction {
            val x =
                select(tableTest().a)
                    .from(tableTest())
                    .where { tableTest().a.eq("the a") }
                    .limit(1)
                    .execute()
            println(x)
        }
    }

    fun <V> transaction(block: context(DIALECT) TRANSACTION.() -> V): V

    fun tableTest(): TABLE
}
