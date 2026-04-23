package com.github.mejiomah17.yaksb.dsl

import com.github.mejiomah17.yaksb.core.DatabaseDialect
import com.github.mejiomah17.yaksb.core.dsl.alias.`as`
import com.github.mejiomah17.yaksb.core.dsl.count
import com.github.mejiomah17.yaksb.core.dsl.from
import com.github.mejiomah17.yaksb.core.dsl.select
import com.github.mejiomah17.yaksb.core.query.ReturningQuery
import com.github.mejiomah17.yaksb.core.transaction.TransactionAtLeastRepeatableRead
import io.kotest.matchers.shouldBe
import kotlin.test.Test

interface CountTest<
    TABLE : TestTable<TABLE, DRIVER_DATA_SOURCE, DRIVER_STATEMENT>,
    DRIVER_DATA_SOURCE,
    DRIVER_STATEMENT,
    DIALECT : DatabaseDialect<DRIVER_DATA_SOURCE, DRIVER_STATEMENT>,
    TRANSACTION : TransactionAtLeastRepeatableRead<DRIVER_DATA_SOURCE, DRIVER_STATEMENT>,
> : SelectionTest<TABLE, DRIVER_DATA_SOURCE, DRIVER_STATEMENT, DIALECT, TRANSACTION> {
    @Test
    fun count_returns_count_of_elements() {
        transactionFactory().repeatableRead {
            val count = count(tableTest().a).`as`("aCount")
            val from: ReturningQuery<DRIVER_DATA_SOURCE, DRIVER_STATEMENT> =
                select(count)
                    .from(tableTest())
            from
                .execute()
                .single()
                .get(count) shouldBe 2
        }
    }
}
