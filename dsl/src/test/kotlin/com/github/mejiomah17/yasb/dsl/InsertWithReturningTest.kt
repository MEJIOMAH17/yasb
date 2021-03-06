package com.github.mejiomah17.yasb.dsl

import com.github.mejiomah17.yasb.core.ddl.Table
import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.Test

interface InsertWithReturningTest<T : Table<T>> : InsertTest<T> {

    @Test
    fun output_returns_values() {
        transactionFactory().repeatableRead {
            val row = insertInto(tableTest(), returning = Returning(columnA(), columnB())) {
                it[columnA()] = "abc"
                it[columnB()] = "bca"
            }.execute().single()
            row[columnA()] shouldBe "abc"
            row[columnB()] shouldBe "bca"
        }
    }

    @Test
    fun output_returns_values_for_iterable_insert() {
        transactionFactory().repeatableRead {
            val values = (0..100).toList()
            val rows =
                insertInto(tableTest(), returning = Returning(columnA(), columnB()), values) { context, value ->
                    if (value % 2 == 0) {
                        context[columnA()] = value.toString()
                    } else {
                        context[columnA()] = "abc"
                    }
                    if (value % 3 == 0) {
                        context[columnB()] = "bca"
                    }
                }.execute()
            values.forEach { value ->
                val row = rows[value]
                if (value % 2 == 0) {
                    row[columnA()] shouldBe value.toString()
                } else {
                    row[columnA()] shouldBe "abc"
                }

                if (value % 3 == 0) {
                    row[columnB()] shouldBe "bca"
                } else {
                    row[columnB()] shouldBe null
                }
            }
        }
    }
}
