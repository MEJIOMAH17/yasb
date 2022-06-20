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
}