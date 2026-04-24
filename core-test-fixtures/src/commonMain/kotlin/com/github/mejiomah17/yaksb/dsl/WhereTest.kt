package com.github.mejiomah17.yaksb.dsl

import com.github.mejiomah17.yaksb.core.DatabaseDialect
import com.github.mejiomah17.yaksb.core.dsl.eq
import com.github.mejiomah17.yaksb.core.dsl.from
import com.github.mejiomah17.yaksb.core.dsl.select
import com.github.mejiomah17.yaksb.core.transaction.TransactionAtLeastRepeatableRead
import com.github.mejiomah17.yaksb.core.where
import io.kotest.matchers.collections.shouldHaveSize
import io.kotest.matchers.shouldBe
import kotlin.test.Test

interface WhereTest<
    TABLE : TestTable<TABLE, DRIVER_DATA_SOURCE, DRIVER_STATEMENT>,
    DRIVER_DATA_SOURCE,
    DRIVER_STATEMENT,
    DIALECT : DatabaseDialect<DRIVER_DATA_SOURCE, DRIVER_STATEMENT>,
    TRANSACTION : TransactionAtLeastRepeatableRead<DRIVER_DATA_SOURCE, DRIVER_STATEMENT>,
> : SelectionTest<TABLE, DRIVER_DATA_SOURCE, DRIVER_STATEMENT, DIALECT, TRANSACTION> {
    @Test
    fun `where_filters_query`() {
        readCommitedTransaction {
            val queryWithoutWhere = select(tableTest().a, tableTest().b).from(tableTest())
            val given = queryWithoutWhere.execute()
            given.shouldHaveSize(2)
            given.get(1)[tableTest().a].shouldBe("42")

            val result =
                queryWithoutWhere
                    .where {
                        tableTest().a.eq("42")
                    }.execute()
            result.shouldHaveSize(1)
            val row = result.single()
            row[tableTest().a] shouldBe "42"
        }
    }

    @Test
    fun `where_builds_correct_sql_for_param`() {
        readCommitedTransaction {
            select(tableTest().a, tableTest().b)
                .from(tableTest())
                .where {
                    tableTest().a.eq("42")
                }.sql() shouldBe "SELECT test.a, test.b FROM test WHERE test.a = ?"
        }
    }

    @Test
    fun `where_builds_correct_sql_for_column`() {
        readCommitedTransaction {
            select(tableTest().a, tableTest().b)
                .from(tableTest())
                .where {
                    tableTest().a.eq(tableTest().b)
                }.sql() shouldBe "SELECT test.a, test.b FROM test WHERE test.a = test.b"
        }
    }

    @Test
    fun `where_has_correct_returnExpressions`() {
        readCommitedTransaction {
            select(tableTest().a, tableTest().b)
                .from(tableTest())
                .where {
                    tableTest().a.eq("42")
                }.returnExpressions() shouldBe listOf(tableTest().a, tableTest().b)
        }
    }

    @Test
    fun `where_has_correct_parameters`() {
        readCommitedTransaction {
            val params =
                select(tableTest().a, tableTest().b)
                    .from(tableTest())
                    .where {
                        tableTest().a.eq("42")
                    }.parameters()
            params.shouldHaveSize(1)
            params.single().value shouldBe "42"
        }
    }
}
