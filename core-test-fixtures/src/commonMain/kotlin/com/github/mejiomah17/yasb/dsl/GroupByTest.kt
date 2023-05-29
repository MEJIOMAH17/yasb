package com.github.mejiomah17.yasb.dsl

import com.github.mejiomah17.yasb.core.DatabaseDialect
import com.github.mejiomah17.yasb.core.dsl.alias.`as`
import com.github.mejiomah17.yasb.core.dsl.eq
import com.github.mejiomah17.yasb.core.dsl.from
import com.github.mejiomah17.yasb.core.dsl.groupBy
import com.github.mejiomah17.yasb.core.dsl.select
import com.github.mejiomah17.yasb.core.transaction.TransactionAtLeastRepeatableRead
import com.github.mejiomah17.yasb.core.where
import io.kotest.matchers.collections.shouldHaveSize
import io.kotest.matchers.shouldBe
import org.junit.Test

interface GroupByTest<
    TABLE : TestTable<TABLE, DRIVER_DATA_SOURCE, DRIVER_STATEMENT>,
    DRIVER_DATA_SOURCE,
    DRIVER_STATEMENT,
    DIALECT : DatabaseDialect<DRIVER_DATA_SOURCE, DRIVER_STATEMENT>,
    TRANSACTION : TransactionAtLeastRepeatableRead<DRIVER_DATA_SOURCE, DRIVER_STATEMENT>
    > :
    SelectionTest<TABLE, DRIVER_DATA_SOURCE, DRIVER_STATEMENT, DIALECT, TRANSACTION> {
    @Test
    fun `groupBy_creates_correct_sql_for_from_clause`() {
        val result = select(tableTest().a, parameter().`as`("p"))
            .from(tableTest())
            .groupBy(tableTest().a)
            .sql()
        result shouldBe "SELECT test.a, (?) AS p FROM test GROUP BY test.a"
    }

    @Test
    fun `groupBy_creates_correct_sql_for_where_clause`(): Unit = databaseDialect.run {
        val result = select(tableTest().a, parameter().`as`("p"))
            .from(tableTest())
            .where { tableTest().a.eq("the a") }
            .groupBy(tableTest().a)
            .sql()
        result shouldBe "SELECT test.a, (?) AS p FROM test WHERE test.a = ? GROUP BY test.a"
    }

    @Test
    fun `groupBy_returns_correct_expressions`() {
        select(tableTest().a)
            .from(tableTest())
            .groupBy(tableTest().a)
            .returnExpressions()
            .shouldBe(listOf(tableTest().a))
    }

    @Test
    fun `groupBy_grouping_values`() {
        transactionFactory().repeatableRead {
            val queryWithoutGroupBy = select(tableTest().a).from(tableTest())
            val given = queryWithoutGroupBy.execute()
            given.shouldHaveSize(2)

            val repeatingColumn = queryWithoutGroupBy.groupBy(tableTest().a).execute()
            repeatingColumn.shouldHaveSize(1)
            val row = repeatingColumn.single()
            row[tableTest().a] shouldBe "the a"
        }
    }

    @Test
    fun `groupBy_executes_after_where_expression`() {
        transactionFactory().repeatableRead {
            val repeatingColumn = select(tableTest().a).from(tableTest())
                .where { tableTest().a.eq("the a") }
                .groupBy(tableTest().a).execute()
            repeatingColumn.shouldHaveSize(1)
            val row = repeatingColumn.single()
            row[tableTest().a] shouldBe "the a"
        }
    }
}
