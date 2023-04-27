package com.github.mejiomah17.yasb.dsl

import com.github.mejiomah17.yasb.core.DatabaseDialect
import com.github.mejiomah17.yasb.core.ddl.Table
import com.github.mejiomah17.yasb.core.dsl.alias.`as`
import com.github.mejiomah17.yasb.core.dsl.eq
import com.github.mejiomah17.yasb.core.dsl.from
import com.github.mejiomah17.yasb.core.dsl.groupBy
import com.github.mejiomah17.yasb.core.dsl.select
import com.github.mejiomah17.yasb.core.where
import io.kotest.matchers.collections.shouldHaveSize
import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.Test

interface GroupByTest<T : Table<T, DRIVER_DATA_SOURCE>, DRIVER_DATA_SOURCE, D : DatabaseDialect<DRIVER_DATA_SOURCE>> :
    SelectionTest<T, DRIVER_DATA_SOURCE, D> {
    @Test
    fun `groupBy creates correct sql for from clause`() {
        val result = select(columnA(), parameter().`as`("p"))
            .from(tableTest())
            .groupBy(columnA())
            .buildSelectQuery()
            .sqlDefinition
        result shouldBe "SELECT test.a, (?) AS p FROM test GROUP BY test.a"
    }

    @Test
    fun `groupBy creates correct sql for where clause`(): Unit = databaseDialect.run {
        val result = select(columnA(), parameter().`as`("p"))
            .from(tableTest())
            .where { columnA().eq("the a") }
            .groupBy(columnA())
            .buildSelectQuery()
            .sqlDefinition
        result shouldBe "SELECT test.a, (?) AS p FROM test WHERE test.a = ? GROUP BY test.a"
    }

    @Test
    fun `groupBy returns correct expressions`() {
        select(columnA())
            .from(tableTest())
            .groupBy(columnA())
            .buildSelectQuery()
            .returnExpressions.shouldBe(listOf(columnA()))
    }

    @Test
    fun `groupBy grouping values`() {
        transactionFactory().readUncommitted {
            val queryWithoutGroupBy = select(columnA()).from(tableTest())
            val given = queryWithoutGroupBy.execute()
            given.shouldHaveSize(2)

            val repeatingColumn = queryWithoutGroupBy.groupBy(columnA()).execute()
            repeatingColumn.shouldHaveSize(1)
            val row = repeatingColumn.single()
            row[columnA()] shouldBe "the a"
        }
    }

    @Test
    fun `groupBy executes after where expression`() {
        transactionFactory().readUncommitted {
            val repeatingColumn = select(columnA()).from(tableTest())
                .where { columnA().eq("the a") }
                .groupBy(columnA()).execute()
            repeatingColumn.shouldHaveSize(1)
            val row = repeatingColumn.single()
            row[columnA()] shouldBe "the a"
        }
    }
}
