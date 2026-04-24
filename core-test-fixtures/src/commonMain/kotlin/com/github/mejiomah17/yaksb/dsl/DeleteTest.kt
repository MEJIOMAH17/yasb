package com.github.mejiomah17.yaksb.dsl

import com.github.mejiomah17.yaksb.core.DatabaseDialect
import com.github.mejiomah17.yaksb.core.dsl.alias.`as`
import com.github.mejiomah17.yaksb.core.dsl.count
import com.github.mejiomah17.yaksb.core.dsl.delete
import com.github.mejiomah17.yaksb.core.dsl.eq
import com.github.mejiomah17.yaksb.core.dsl.from
import com.github.mejiomah17.yaksb.core.dsl.select
import com.github.mejiomah17.yaksb.core.transaction.TransactionAtLeastRepeatableRead
import com.github.mejiomah17.yaksb.core.where
import io.kotest.matchers.shouldBe
import io.kotest.matchers.shouldNotBe
import kotlin.test.Test

interface DeleteTest<
    TABLE : TestTable<TABLE, DRIVER_DATA_SOURCE, DRIVER_STATEMENT>,
    DRIVER_DATA_SOURCE,
    DRIVER_STATEMENT,
    DIALECT : DatabaseDialect<DRIVER_DATA_SOURCE, DRIVER_STATEMENT>,
    TRANSACTION : TransactionAtLeastRepeatableRead<DRIVER_DATA_SOURCE, DRIVER_STATEMENT>,
> : SelectionTest<TABLE, DRIVER_DATA_SOURCE, DRIVER_STATEMENT, DIALECT, TRANSACTION> {
    @Test
    fun deletes_everything() {
        transaction {
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
        transaction {
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
                }.execute()

            select(count)
                .from(tableTest())
                .execute()
                .single()
                .get(count) shouldBe 1
        }
    }
}
