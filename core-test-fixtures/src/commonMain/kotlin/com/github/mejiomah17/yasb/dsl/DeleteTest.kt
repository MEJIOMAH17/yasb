@file:Suppress("UNSUPPORTED_FEATURE", "UNSUPPORTED_CONTEXTUAL_DECLARATION_CALL")

package com.github.mejiomah17.yasb.dsl

import com.github.mejiomah17.yasb.core.DatabaseDialect
import com.github.mejiomah17.yasb.core.dsl.alias.`as`
import com.github.mejiomah17.yasb.core.dsl.count
import com.github.mejiomah17.yasb.core.dsl.delete
import com.github.mejiomah17.yasb.core.dsl.eq
import com.github.mejiomah17.yasb.core.dsl.from
import com.github.mejiomah17.yasb.core.dsl.select
import com.github.mejiomah17.yasb.core.transaction.TransactionAtLeastRepeatableRead
import com.github.mejiomah17.yasb.core.where
import io.kotest.matchers.shouldBe
import io.kotest.matchers.shouldNotBe
import org.junit.Test

interface DeleteTest<
    TABLE : TestTable<TABLE, DRIVER_DATA_SOURCE, DRIVER_STATEMENT>,
    DRIVER_DATA_SOURCE,
    DRIVER_STATEMENT,
    DIALECT : DatabaseDialect<DRIVER_DATA_SOURCE, DRIVER_STATEMENT>,
    TRANSACTION : TransactionAtLeastRepeatableRead<DRIVER_DATA_SOURCE, DRIVER_STATEMENT>
    > :
    SelectionTest<TABLE, DRIVER_DATA_SOURCE, DRIVER_STATEMENT, DIALECT, TRANSACTION> {
    @Test
    fun deletes_everything() {
        transactionFactory().repeatableRead {
            val count = count(tableTest().a).`as`("aCount")
            select(count)
                .from(tableTest())
                .execute()
                .single()
                .get(count) shouldNotBe 0

            delete()
                .from(tableTest())
                .execute()

            select(count)
                .from(tableTest())
                .execute()
                .single()
                .get(count) shouldBe 0
        }
    }

    @Test
    fun deletes_where() {
        transactionFactory().repeatableRead {
            val count = count(tableTest().a).`as`("aCount")
            select(count)
                .from(tableTest())
                .execute()
                .single()
                .get(count) shouldNotBe 1

            delete()
                .from(tableTest())
                .where {
                    tableTest().a.eq("the a")
                }
                .execute()

            select(count)
                .from(tableTest())
                .execute()
                .single()
                .get(count) shouldBe 1
        }
    }
}
