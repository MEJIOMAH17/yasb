@file:Suppress("UNSUPPORTED_FEATURE", "UNSUPPORTED_CONTEXTUAL_DECLARATION_CALL")

package com.github.mejiomah17.yasb.dsl

import com.github.mejiomah17.yasb.core.DatabaseDialect
import com.github.mejiomah17.yasb.core.dsl.alias.`as`
import com.github.mejiomah17.yasb.core.dsl.count
import com.github.mejiomah17.yasb.core.dsl.from
import com.github.mejiomah17.yasb.core.dsl.select
import com.github.mejiomah17.yasb.core.transaction.TransactionAtLeastRepeatableRead
import io.kotest.matchers.shouldBe
import org.junit.Test

interface CountTest<
    TABLE : TestTable<TABLE, DRIVER_DATA_SOURCE, DRIVER_STATEMENT>,
    DRIVER_DATA_SOURCE,
    DRIVER_STATEMENT,
    DIALECT : DatabaseDialect<DRIVER_DATA_SOURCE, DRIVER_STATEMENT>,
    TRANSACTION : TransactionAtLeastRepeatableRead<DRIVER_DATA_SOURCE, DRIVER_STATEMENT>
    > :
    SelectionTest<TABLE, DRIVER_DATA_SOURCE, DRIVER_STATEMENT, DIALECT, TRANSACTION> {

    @Test
    fun `count_returns_count_of_elements`() {
        transactionFactory().repeatableRead {
            val count = count(tableTest().a).`as`("aCount")
            val from = select(count)
                .from(tableTest())
            from
                .execute()
                .single()
                .get(count) shouldBe 2
        }
    }
}
