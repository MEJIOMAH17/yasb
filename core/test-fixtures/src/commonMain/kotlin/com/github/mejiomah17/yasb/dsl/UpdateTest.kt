package com.github.mejiomah17.yasb.dsl

import com.github.mejiomah17.yasb.core.DatabaseDialect
import com.github.mejiomah17.yasb.core.dsl.eq
import com.github.mejiomah17.yasb.core.dsl.from
import com.github.mejiomah17.yasb.core.dsl.insertInto
import com.github.mejiomah17.yasb.core.dsl.select
import com.github.mejiomah17.yasb.core.dsl.update
import com.github.mejiomah17.yasb.core.transaction.Transaction
import io.kotest.matchers.shouldBe
import org.junit.Test

interface UpdateTest<
    TABLE : TestTable<TABLE, DRIVER_DATA_SOURCE, DRIVER_STATEMENT>,
    DRIVER_DATA_SOURCE,
    DRIVER_STATEMENT,
    DIALECT : DatabaseDialect<DRIVER_DATA_SOURCE, DRIVER_STATEMENT>,
    TRANSACTION : Transaction<DRIVER_DATA_SOURCE, DRIVER_STATEMENT>
    > :
    SelectionTest<TABLE, DRIVER_DATA_SOURCE, DRIVER_STATEMENT, DIALECT, TRANSACTION> {

    @Test
    fun updates_values_without_where() {
        transactionFactory().repeatableRead {
            insertInto(tableTest()) {
                it[tableTest().a] = "abc"
                it[tableTest().b] = "bca"
            }.execute()
            update(
                tableTest(),
                set = {
                    it[tableTest().a] = "qwertypop"
                    it[tableTest().b] = "popqwerty"
                }
            ).execute()
            val row = select(tableTest().a, tableTest().b)
                .from(tableTest())
                .execute()
                .single()
            row[tableTest().a] shouldBe "qwertypop"
            row[tableTest().b] shouldBe "popqwerty"
        }
    }

    @Test
    fun updates_values_with_where() {
        transactionFactory().repeatableRead {
            insertInto(tableTest()) {
                it[tableTest().a] = "abc"
                it[tableTest().b] = "bca"
            }.execute()
            update(
                tableTest(),
                set = {
                    it[tableTest().a] = "qwertypop"
                    it[tableTest().b] = "popqwerty"
                },
                where = {
                    tableTest().a.eq("abc")
                }
            ).execute()
            val row = select(tableTest().a, tableTest().b)
                .from(tableTest())
                .execute()
                .single()
            row[tableTest().a] shouldBe "qwertypop"
            row[tableTest().b] shouldBe "popqwerty"
        }
    }

    @Test
    fun does_not_update_values_with_where() {
        transactionFactory().repeatableRead {
            insertInto(tableTest()) {
                it[tableTest().a] = "abc"
                it[tableTest().b] = "bca"
            }.execute()
            update(
                tableTest(),
                set = {
                    it[tableTest().a] = "qwertypop"
                    it[tableTest().b] = "popqwerty"
                },
                where = {
                    tableTest().a.eq("abcs")
                }
            ).execute()
            val row = select(tableTest().a, tableTest().b)
                .from(tableTest())
                .execute()
                .single()
            row[tableTest().a] shouldBe "abc"
            row[tableTest().b] shouldBe "bca"
        }
    }
}
