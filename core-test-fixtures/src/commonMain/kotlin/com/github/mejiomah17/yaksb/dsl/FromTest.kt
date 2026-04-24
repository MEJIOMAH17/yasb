package com.github.mejiomah17.yaksb.dsl

import com.github.mejiomah17.yaksb.core.DatabaseDialect
import com.github.mejiomah17.yaksb.core.dsl.alias.`as`
import com.github.mejiomah17.yaksb.core.dsl.from
import com.github.mejiomah17.yaksb.core.dsl.select
import com.github.mejiomah17.yaksb.core.transaction.TransactionAtLeastRepeatableRead
import io.kotest.matchers.shouldBe
import kotlin.test.Test

interface FromTest<
    TABLE : TestTable<TABLE, DRIVER_DATA_SOURCE, DRIVER_STATEMENT>,
    DRIVER_DATA_SOURCE,
    DRIVER_STATEMENT,
    DIALECT : DatabaseDialect<DRIVER_DATA_SOURCE, DRIVER_STATEMENT>,
    TRANSACTION : TransactionAtLeastRepeatableRead<DRIVER_DATA_SOURCE, DRIVER_STATEMENT>,
> : SelectionTest<TABLE, DRIVER_DATA_SOURCE, DRIVER_STATEMENT, DIALECT, TRANSACTION> {
    @Test
    fun `from_creates_correct_sql`() {
        val result =
            select(tableTest().a, tableTest().b, parameter().`as`("p"))
                .from(tableTest())
                .sql()
        result shouldBe "SELECT test.a, test.b, (?) AS p FROM test"
    }

    @Test
    fun `from_creates_correct_sql_for_aliased_table`() {
        val table = tableTest().`as`("xxx")
        val result =
            select(table[tableTest().a], table[tableTest().b], parameter().`as`("p"))
                .from(table)
                .sql()
        result shouldBe "SELECT xxx.a, xxx.b, (?) AS p FROM test AS xxx"
    }

    @Test
    fun `from_returns_correct_expressions`() {
        select(tableTest().a, tableTest().b)
            .from(tableTest())
            .returnExpressions()
            .shouldBe(listOf(tableTest().a, tableTest().b))
    }

    @Test
    fun `from_returns_correct_expressions_for_aliased_table`() {
        val table = tableTest().`as`("xxx")
        val aColumn = table[tableTest().a]
        val bColumn = table[tableTest().b]
        select(aColumn, bColumn)
            .from(table)
            .returnExpressions()
            .shouldBe(listOf(aColumn, bColumn))
    }

    @Test
    fun `from_returns_columns`() {
        transaction {
            val row =
                select(tableTest().a, tableTest().b)
                    .from(tableTest())
                    .execute()
                    .single()
            row[tableTest().a] shouldBe "the a"
            row[tableTest().b] shouldBe "the b"
        }
    }

    @Test
    fun `from_returns_columns_for_aliased_table`() {
        val table = tableTest().`as`("xxx")
        val aColumn = table[tableTest().a]
        val bColumn = table[tableTest().b]
        transaction {
            val row =
                select(aColumn, bColumn)
                    .from(table)
                    .execute()
                    .single()
            row[aColumn] shouldBe "the a"
            row[bColumn] shouldBe "the b"
        }
    }

    @Test
    fun `from_returns_columns_for_nested_query`() {
        val param = parameter().`as`("p")
        transaction {
            val nestedQuery =
                select(tableTest().a, tableTest().b, param)
                    .from(tableTest())
                    .`as`("xxx")
            val columnA = nestedQuery[tableTest().a]
            val columnB = nestedQuery[tableTest().b]
            val paramFromQuery = nestedQuery[param]

            val query =
                select(columnA, columnB, paramFromQuery)
                    .from(nestedQuery)

            val row =
                query
                    .execute()
                    .single()
            row[columnA] shouldBe "the a"
            row[columnB] shouldBe "the b"
            row[paramFromQuery] shouldBe "param"
        }
    }

    @Test
    fun `from_returns_parameter`() {
        val param = parameter().`as`("p")
        transaction {
            val row =
                select(param)
                    .from(tableTest())
                    .execute()
                    .single()
            row[param] shouldBe "param"
        }
    }

    @Test
    fun `from_returns_correct_parameters`() {
        val param = parameter()
        select(param.`as`("p"))
            .from(tableTest())
            .parameters()
            .single()
            .shouldBe(param)
    }

    @Test
    fun `from_returns_correct_sql_for_nested_query`() {
        val result =
            select(tableTest().a, tableTest().b, parameter().`as`("p"))
                .from(
                    select(tableTest().a, tableTest().b)
                        .from(tableTest())
                        .`as`("xxx"),
                ).sql()
        result shouldBe "SELECT test.a, test.b, (?) AS p FROM (SELECT test.a, test.b FROM test) AS xxx"
    }
}
