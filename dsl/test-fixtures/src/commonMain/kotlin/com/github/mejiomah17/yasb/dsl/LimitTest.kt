package com.github.mejiomah17.yasb.dsl

import com.github.mejiomah17.yasb.core.DatabaseDialect
import com.github.mejiomah17.yasb.core.SupportsLimit
import com.github.mejiomah17.yasb.core.ddl.Column
import com.github.mejiomah17.yasb.core.ddl.Table
import com.github.mejiomah17.yasb.core.where
import com.github.mejiomah17.yasb.dsl.transaction.TransactionFactory
import io.kotest.matchers.collections.shouldHaveSize
import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.Test

interface LimitTest<T : Table<T, S>, F, D, S> where F : TransactionFactory<D, S>, D : DatabaseDialect<S>, D : SupportsLimit {

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
    fun columnA(): Column<T, String, S>
    fun tableTest(): T
}
