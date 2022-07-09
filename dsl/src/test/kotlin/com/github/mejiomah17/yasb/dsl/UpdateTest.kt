package com.github.mejiomah17.yasb.dsl

import com.github.mejiomah17.yasb.core.ddl.Table
import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.Test

interface UpdateTest<T : Table<T>> : SelectionTest<T> {

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
                    it[columnA()]= "qwertypop"
                    it[columnB()]= "popqwerty"
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
                    it[columnA()]= "qwertypop"
                    it[columnB()]= "popqwerty"
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
                    it[columnA()]= "qwertypop"
                    it[columnB()]= "popqwerty"
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