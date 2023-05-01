package com.github.mejiomah17.yasb.dsl

import com.github.mejiomah17.yasb.core.DatabaseDialect
import com.github.mejiomah17.yasb.core.SupportsLimit
import com.github.mejiomah17.yasb.core.ddl.Column
import com.github.mejiomah17.yasb.core.ddl.Table
import com.github.mejiomah17.yasb.core.dsl.eq
import com.github.mejiomah17.yasb.core.dsl.from
import com.github.mejiomah17.yasb.core.dsl.limit
import com.github.mejiomah17.yasb.core.dsl.select
import com.github.mejiomah17.yasb.core.transaction.Transaction
import com.github.mejiomah17.yasb.core.transaction.TransactionFactory
import com.github.mejiomah17.yasb.core.where
import io.kotest.matchers.collections.shouldHaveSize
import io.kotest.matchers.shouldBe
import org.junit.Test

interface LimitTest<
    TABLE : Table<TABLE, DRIVER_DATA_SOURCE, DRIVER_STATEMENT>,
    DRIVER_DATA_SOURCE,
    DRIVER_STATEMENT,
    DIALECT,
    TRANSACTION : Transaction<DRIVER_DATA_SOURCE, DRIVER_STATEMENT>,
    F : TransactionFactory<DRIVER_DATA_SOURCE, DRIVER_STATEMENT, DIALECT, TRANSACTION>
    > where DIALECT : DatabaseDialect<DRIVER_DATA_SOURCE, DRIVER_STATEMENT>,
            DIALECT : SupportsLimit {

    @Test
    fun `limit generates correct sql`() {
        transactionFactory().readCommitted {
            select(columnA())
                .from(TestTable())
                .limit(1)
                .buildSelectQuery()
                .sqlDefinition shouldBe "SELECT test.a FROM test LIMIT 1"
        }
    }

    @Test
    fun `limit 1 return single record`() {
        transactionFactory().readCommitted {
            select(columnA())
                .from(TestTable())
                .limit(1)
                .execute() shouldHaveSize 1
        }
    }

    @Test
    fun `limit called after where`() {
        transactionFactory().readCommitted {
            select(columnA())
                .from(TestTable())
                .where { columnA().eq("the a") }
                .limit(1)
                .execute()
        }
    }

    fun transactionFactory(): F
    fun columnA(): Column<TABLE, String, DRIVER_DATA_SOURCE, DRIVER_STATEMENT>
    fun tableTest(): TABLE
}