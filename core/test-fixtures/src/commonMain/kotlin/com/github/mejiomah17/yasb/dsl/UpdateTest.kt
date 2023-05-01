package com.github.mejiomah17.yasb.dsl

import com.github.mejiomah17.yasb.core.DatabaseDialect
import com.github.mejiomah17.yasb.core.ddl.Table
import com.github.mejiomah17.yasb.core.dsl.eq
import com.github.mejiomah17.yasb.core.dsl.from
import com.github.mejiomah17.yasb.core.dsl.insertInto
import com.github.mejiomah17.yasb.core.dsl.select
import com.github.mejiomah17.yasb.core.dsl.update
import com.github.mejiomah17.yasb.core.transaction.Transaction
import io.kotest.matchers.shouldBe
import org.junit.Test

interface UpdateTest<
    TABLE : Table<TABLE, DRIVER_DATA_SOURCE, DRIVER_STATEMENT>,
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
                it[columnA()] = "abc"
                it[columnB()] = "bca"
            }.execute()
            update(
                tableTest(),
                set = {
                    it[columnA()] = "qwertypop"
                    it[columnB()] = "popqwerty"
                }
            ).execute()
            val row = select(columnA(), columnB())
                .from(tableTest())
                .execute()
                .single()
            row[columnA()] shouldBe "qwertypop"
            row[columnB()] shouldBe "popqwerty"
        }
    }

    @Test
    fun updates_values_with_where() {
        transactionFactory().repeatableRead {
            insertInto(tableTest()) {
                it[columnA()] = "abc"
                it[columnB()] = "bca"
            }.execute()
            update(
                tableTest(),
                set = {
                    it[columnA()] = "qwertypop"
                    it[columnB()] = "popqwerty"
                },
                where = {
                    columnA().eq("abc")
                }
            ).execute()
            val row = select(columnA(), columnB())
                .from(tableTest())
                .execute()
                .single()
            row[columnA()] shouldBe "qwertypop"
            row[columnB()] shouldBe "popqwerty"
        }
    }

    @Test
    fun does_not_update_values_with_where() {
        transactionFactory().repeatableRead {
            insertInto(tableTest()) {
                it[columnA()] = "abc"
                it[columnB()] = "bca"
            }.execute()
            update(
                tableTest(),
                set = {
                    it[columnA()] = "qwertypop"
                    it[columnB()] = "popqwerty"
                },
                where = {
                    columnA().eq("abcs")
                }
            ).execute()
            val row = select(columnA(), columnB())
                .from(tableTest())
                .execute()
                .single()
            row[columnA()] shouldBe "abc"
            row[columnB()] shouldBe "bca"
        }
    }
}