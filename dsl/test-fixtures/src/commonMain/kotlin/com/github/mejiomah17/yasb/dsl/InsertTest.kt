package com.github.mejiomah17.yasb.dsl

import com.github.mejiomah17.yasb.core.SupportsInsertWithDefaultValue
import com.github.mejiomah17.yasb.core.ddl.Table
import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.Test

interface InsertTest<T : Table<T>> : SelectionTest<T> {

    @Test
    fun select_values_after_insert() {
        transactionFactory().repeatableRead {
            insertInto(tableTest()) {
                it[columnA()] = "abc"
                it[columnB()] = "bca"
            }.execute()
            val row = select(columnA(), columnB())
                .from(tableTest())
                .execute()
                .single()
            row[columnA()] shouldBe "abc"
            row[columnB()] shouldBe "bca"
        }
    }

    @Test
    fun select_values_after_insert_collection() {
        transactionFactory().repeatableRead {
            if (this is SupportsInsertWithDefaultValue) {
                val values = (0..100).toList()

                insertInto(tableTest(), values) { context, value ->
                    context[columnA()] = value.toString()
                    context[columnB()] = "bca"
                }.execute()
                val rows = select(columnA(), columnB())
                    .from(tableTest())
                    .execute()
                values.forEach {
                    val row = rows[it]
                    row[columnA()] shouldBe it.toString()
                    row[columnB()] shouldBe "bca"
                }
            }
        }
    }

    @Test
    fun select_values_after_insert_collection_using_default() {
        transactionFactory().repeatableRead {
            if (this is SupportsInsertWithDefaultValue) {
                val values = (0..100).toList()
                insertInto(tableTest(), values) { context, value ->
                    if (value % 2 == 0) {
                        context[columnA()] = value.toString()
                    } else {
                        context[columnA()] = "abc"
                    }
                    if (value % 3 == 0) {
                        context[columnB()] = "bca"
                    }
                }.execute()
                val rows = select(columnA(), columnB())
                    .from(tableTest())
                    .execute()
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

    @Test
    fun select_values_after_insert_in_different_transaction() {
        transactionFactory().repeatableRead {
            insertInto(tableTest()) {
                it[columnA()] = "abc"
                it[columnB()] = "bca"
            }.execute()
        }
        transactionFactory().repeatableRead {
            val row = select(columnA(), columnB())
                .from(tableTest())
                .execute()
                .single()
            row[columnA()] shouldBe "abc"
            row[columnB()] shouldBe "bca"
        }
    }
}
