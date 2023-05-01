package com.github.mejiomah17.yasb.dsl

import com.github.mejiomah17.yasb.core.DatabaseDialect
import com.github.mejiomah17.yasb.core.dsl.eq
import com.github.mejiomah17.yasb.core.dsl.from
import com.github.mejiomah17.yasb.core.dsl.select
import com.github.mejiomah17.yasb.core.transaction.Transaction
import com.github.mejiomah17.yasb.core.where
import io.kotest.matchers.collections.shouldHaveSize
import io.kotest.matchers.shouldBe
import org.junit.Test

interface WhereTest<
    TABLE : TestTable<TABLE, DRIVER_DATA_SOURCE, DRIVER_STATEMENT>,
    DRIVER_DATA_SOURCE,
    DRIVER_STATEMENT,
    DIALECT : DatabaseDialect<DRIVER_DATA_SOURCE, DRIVER_STATEMENT>,
    TRANSACTION : Transaction<DRIVER_DATA_SOURCE, DRIVER_STATEMENT>
    > :
    SelectionTest<TABLE, DRIVER_DATA_SOURCE, DRIVER_STATEMENT, DIALECT, TRANSACTION> {
    @Test
    fun `where_filters_query`() {
        transactionFactory().readUncommitted {
            val queryWithoutWhere = select(tableTest().a, tableTest().b).from(tableTest())
            val given = queryWithoutWhere.execute()
            given.shouldHaveSize(2)
            given.get(1)[tableTest().a].shouldBe("42")

            val result = queryWithoutWhere.where {
                tableTest().a.eq("42")
            }.execute()
            result.shouldHaveSize(1)
            val row = result.single()
            row[tableTest().a] shouldBe "42"
        }
    }

    @Test
    fun `where_builds_correct_sql_for_param`() {
        transactionFactory().readUncommitted {
            select(tableTest().a, tableTest().b)
                .from(tableTest())
                .where {
                    tableTest().a.eq("42")
                }.buildSelectQuery()
                .sqlDefinition shouldBe "SELECT test.a, test.b FROM test WHERE test.a = ?"
        }
    }

    @Test
    fun `where_builds_correct_sql_for_column`() {
        transactionFactory().readUncommitted {
            select(tableTest().a, tableTest().b)
                .from(tableTest())
                .where {
                    tableTest().a.eq(tableTest().b)
                }.buildSelectQuery()
                .sqlDefinition shouldBe "SELECT test.a, test.b FROM test WHERE test.a = test.b"
        }
    }

    @Test
    fun `where_has_correct_returnExpressions`() {
        transactionFactory().readUncommitted {
            select(tableTest().a, tableTest().b)
                .from(tableTest())
                .where {
                    tableTest().a.eq("42")
                }.buildSelectQuery()
                .returnExpressions shouldBe listOf(tableTest().a, tableTest().b)
        }
    }

    @Test
    fun `where_has_correct_parameters`() {
        transactionFactory().readUncommitted {
            val params = select(tableTest().a, tableTest().b)
                .from(tableTest())
                .where {
                    tableTest().a.eq("42")
                }.buildSelectQuery()
                .parameters
            params.shouldHaveSize(1)
            params.single().value shouldBe "42"
        }
    }
}
