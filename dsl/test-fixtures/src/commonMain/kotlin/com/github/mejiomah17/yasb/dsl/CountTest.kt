@file:Suppress("UNSUPPORTED_FEATURE", "UNSUPPORTED_CONTEXTUAL_DECLARATION_CALL")

package com.github.mejiomah17.yasb.dsl

import com.github.mejiomah17.yasb.core.DatabaseDialect
import com.github.mejiomah17.yasb.core.ddl.Table
import com.github.mejiomah17.yasb.core.dsl.From
import com.github.mejiomah17.yasb.core.dsl.alias.`as`
import com.github.mejiomah17.yasb.core.dsl.count
import com.github.mejiomah17.yasb.core.dsl.from
import com.github.mejiomah17.yasb.core.dsl.select
import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.Test

interface CountTest<TABLE : Table<TABLE, DRIVER_DATA_SOURCE, DRIVER_STATEMENT>, DRIVER_DATA_SOURCE, DRIVER_STATEMENT, DIALECT : DatabaseDialect<DRIVER_DATA_SOURCE, DRIVER_STATEMENT>> :
    SelectionTest<TABLE, DRIVER_DATA_SOURCE, DRIVER_STATEMENT, DIALECT> {
    @Test
    fun `count returns count of elements`() {
        transactionFactory().readCommitted {
            val count = count(columnA()).`as`("aCount")
            val from: From<DRIVER_DATA_SOURCE, DRIVER_STATEMENT> = select(count)
                .from(tableTest())
            from
                .execute()
                .single()
                .get(count) shouldBe 2
        }
    }
}
