package com.github.mejiomah17.yasb.dsl

import com.github.mejiomah17.yasb.core.DatabaseDialect
import com.github.mejiomah17.yasb.core.dsl.alias.`as`
import com.github.mejiomah17.yasb.core.dsl.from
import com.github.mejiomah17.yasb.core.dsl.select
import com.github.mejiomah17.yasb.core.transaction.Transaction
import io.kotest.matchers.shouldBe
import org.junit.Test

interface FromTest<
    TABLE : TestTable<TABLE, DRIVER_DATA_SOURCE, DRIVER_STATEMENT>,
    DRIVER_DATA_SOURCE,
    DRIVER_STATEMENT,
    DIALECT : DatabaseDialect<DRIVER_DATA_SOURCE, DRIVER_STATEMENT>,
    TRANSACTION : Transaction<DRIVER_DATA_SOURCE, DRIVER_STATEMENT>
    > :
    SelectionTest<TABLE, DRIVER_DATA_SOURCE, DRIVER_STATEMENT, DIALECT, TRANSACTION> {
    @Test
    fun `from_creates_From`() {
        val select = select(tableTest().a)

        val result = select.from(tableTest())

        result.select shouldBe select
        result.source shouldBe tableTest()
    }

    @Test
    fun `from_creates_From_for_aliased_table`() {
        val select = select(tableTest().a)
        val table = tableTest().`as`("xxx")
        val result = select.from(table)

        result.select shouldBe select
        result.source shouldBe table
    }

    @Test
    fun `from_creates_correct_sql`() {
        val result = select(tableTest().a, tableTest().b, parameter().`as`("p"))
            .from(tableTest())
            .buildSelectQuery()
            .sqlDefinition
        result shouldBe "SELECT test.a, test.b, (?) AS p FROM test"
    }

    @Test
    fun `from_creates_correct_sql_for_aliased_table`() {
        val table = tableTest().`as`("xxx")
        val result = select(table[tableTest().a], table[tableTest().b], parameter().`as`("p"))
            .from(table)
            .buildSelectQuery()
            .sqlDefinition
        result shouldBe "SELECT xxx.a, xxx.b, (?) AS p FROM test AS xxx"
    }

    @Test
    fun `from_returns_correct_expressions`() {
        select(tableTest().a, tableTest().b)
            .from(tableTest())
            .buildSelectQuery()
            .returnExpressions.shouldBe(listOf(tableTest().a, tableTest().b))
    }

    @Test
    fun `from_returns_correct_expressions_for_aliased_table`() {
        val table = tableTest().`as`("xxx")
        val aColumn = table[tableTest().a]
        val bColumn = table[tableTest().b]
        select(aColumn, bColumn)
            .from(table)
            .buildSelectQuery()
            .returnExpressions.shouldBe(listOf(aColumn, bColumn))
    }

    @Test
    fun `from_returns_columns`() {
        transactionFactory().readUncommitted {
            val row = select(tableTest().a, tableTest().b)
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
        transactionFactory().readUncommitted {
            val row = select(aColumn, bColumn)
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
        transactionFactory().readUncommitted {
            val nestedQuery = select(tableTest().a, tableTest().b, param)
                .from(tableTest())
                .`as`("xxx")
            val columnA = nestedQuery[tableTest().a]
            val columnB = nestedQuery[tableTest().b]
            val paramFromQuery = nestedQuery[param]

            val query = select(columnA, columnB, paramFromQuery)
                .from(nestedQuery)

            val row = query
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
        transactionFactory().readUncommitted {
            val row = select(param)
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
            .buildSelectQuery()
            .parameters.single().shouldBe(param)
    }

    @Test
    fun `from_returns_correct_sql_for_nested_query`() {
        val result = select(tableTest().a, tableTest().b, parameter().`as`("p"))
            .from(
                select(tableTest().a, tableTest().b)
                    .from(tableTest())
                    .`as`("xxx")
            )
            .buildSelectQuery()
            .sqlDefinition
        result shouldBe "SELECT test.a, test.b, (?) AS p FROM (SELECT test.a, test.b FROM test) AS xxx"
    }
}
