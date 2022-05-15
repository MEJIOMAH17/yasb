package com.github.mejiomah17.yasb.dsl

import com.github.mejiomah17.yasb.core.ddl.Table
import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.Test

interface SimpleInsertTest<T : Table<T>> : SelectionTest<T> {

    @Test
    fun select_values_after_insert() {
        transactionFactory().repeatableRead {
            insertInto(tableTest()) {
                it[columnA()] = "abc"
                it[columnB()] = "bca"
            }.execute()
            val row = select(columnA(),columnB())
                .from(tableTest())
                .execute()
                .single()
            row[columnA()] shouldBe "abc"
            row[columnB()] shouldBe "bca"
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
            val row = select(columnA(),columnB())
                .from(tableTest())
                .execute()
                .single()
            row[columnA()] shouldBe "abc"
            row[columnB()] shouldBe "bca"
        }
    }

}