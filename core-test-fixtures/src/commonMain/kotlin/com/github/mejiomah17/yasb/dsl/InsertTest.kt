@file:Suppress("UNSUPPORTED_FEATURE", "UNSUPPORTED_CONTEXTUAL_DECLARATION_CALL")

package com.github.mejiomah17.yasb.dsl

import com.github.mejiomah17.yasb.core.DatabaseDialect
import com.github.mejiomah17.yasb.core.SupportsInsertWithDefaultValue
import com.github.mejiomah17.yasb.core.dsl.from
import com.github.mejiomah17.yasb.core.dsl.insertInto
import com.github.mejiomah17.yasb.core.dsl.select
import com.github.mejiomah17.yasb.core.transaction.TransactionAtLeastRepeatableRead
import io.kotest.matchers.shouldBe
import org.junit.Test

interface InsertTest<
    TABLE : TestTable<TABLE, DRIVER_DATA_SOURCE, DRIVER_STATEMENT>,
    DRIVER_DATA_SOURCE,
    DRIVER_STATEMENT,
    DIALECT : DatabaseDialect<DRIVER_DATA_SOURCE, DRIVER_STATEMENT>,
    TRANSACTION : TransactionAtLeastRepeatableRead<DRIVER_DATA_SOURCE, DRIVER_STATEMENT>
    > :
    SelectionTest<TABLE, DRIVER_DATA_SOURCE, DRIVER_STATEMENT, DIALECT, TRANSACTION> {

    @Test
    fun select_values_after_insert() {
        transactionFactory().repeatableRead {
            insertInto(tableTest()) {
                it[tableTest().a] = "abc"
                it[tableTest().b] = "bca"
            }.execute()
            val row = select(tableTest().a, tableTest().b)
                .from(tableTest())
                .execute()
                .single()
            row[tableTest().a] shouldBe "abc"
            row[tableTest().b] shouldBe "bca"
        }
    }

    @Test
    fun select_values_after_insert_collection() {
        transactionFactory().repeatableRead {
            if (this is SupportsInsertWithDefaultValue) {
                val values = (0..100).toList()

                insertInto(tableTest(), values) { context, value ->
                    context[tableTest().a] = value.toString()
                    context[tableTest().b] = "bca"
                }.execute()
                val rows = select(tableTest().a, tableTest().b)
                    .from(tableTest())
                    .execute()
                values.forEach {
                    val row = rows[it]
                    row[tableTest().a] shouldBe it.toString()
                    row[tableTest().b] shouldBe "bca"
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
                        context[tableTest().a] = value.toString()
                    } else {
                        context[tableTest().a] = "abc"
                    }
                    if (value % 3 == 0) {
                        context[tableTest().b] = "bca"
                    }
                }.execute()
                val rows = select(tableTest().a, tableTest().b)
                    .from(tableTest())
                    .execute()
                values.forEach { value ->
                    val row = rows[value]
                    if (value % 2 == 0) {
                        row[tableTest().a] shouldBe value.toString()
                    } else {
                        row[tableTest().a] shouldBe "abc"
                    }

                    if (value % 3 == 0) {
                        row[tableTest().b] shouldBe "bca"
                    } else {
                        row[tableTest().b] shouldBe null
                    }
                }
            }
        }
    }

    @Test
    fun select_values_after_insert_in_different_transaction() {
        transactionFactory().repeatableRead {
            insertInto(tableTest()) {
                it[tableTest().a] = "abc"
                it[tableTest().b] = "bca"
            }.execute()
        }
        transactionFactory().repeatableRead {
            val row = select(tableTest().a, tableTest().b)
                .from(tableTest())
                .execute()
                .single()
            row[tableTest().a] shouldBe "abc"
            row[tableTest().b] shouldBe "bca"
        }
    }
}
