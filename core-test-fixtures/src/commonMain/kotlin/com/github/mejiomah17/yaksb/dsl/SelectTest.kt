package com.github.mejiomah17.yaksb.dsl

import com.github.mejiomah17.yaksb.core.DatabaseDialect
import com.github.mejiomah17.yaksb.core.dsl.alias.`as`
import com.github.mejiomah17.yaksb.core.dsl.select
import com.github.mejiomah17.yaksb.core.transaction.TransactionAtLeastRepeatableRead
import io.kotest.matchers.shouldBe
import kotlin.test.Test

interface SelectTest<
    TABLE : TestTable<TABLE, DRIVER_DATA_SOURCE, DRIVER_STATEMENT>,
    DRIVER_DATA_SOURCE,
    DRIVER_STATEMENT,
    DIALECT : DatabaseDialect<DRIVER_DATA_SOURCE, DRIVER_STATEMENT>,
    TRANSACTION : TransactionAtLeastRepeatableRead<DRIVER_DATA_SOURCE, DRIVER_STATEMENT>,
> : SelectionTest<TABLE, DRIVER_DATA_SOURCE, DRIVER_STATEMENT, DIALECT, TRANSACTION> {
    @Test
    fun selects_parameter() {
        readCommitedTransaction {
            val param = parameter().`as`("param")
            select(param)
                .execute()
                .map {
                    it[param]
                }.single()
                .shouldBe(parameter().value)
        }
    }
}
