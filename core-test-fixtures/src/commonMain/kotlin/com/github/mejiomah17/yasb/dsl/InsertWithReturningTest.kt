@file:Suppress("UNSUPPORTED_FEATURE", "UNSUPPORTED_CONTEXTUAL_DECLARATION_CALL")

package com.github.mejiomah17.yasb.dsl

import com.github.mejiomah17.yasb.core.DatabaseDialect
import com.github.mejiomah17.yasb.core.SupportsInsertReturning
import com.github.mejiomah17.yasb.core.SupportsInsertWithDefaultValue
import com.github.mejiomah17.yasb.core.dsl.insertInto
import com.github.mejiomah17.yasb.core.dsl.returning
import com.github.mejiomah17.yasb.core.transaction.TransactionAtLeastRepeatableRead
import io.kotest.matchers.shouldBe
import org.junit.Test

interface InsertWithReturningTest<
    TABLE : TestTable<TABLE, DRIVER_DATA_SOURCE, DRIVER_STATEMENT>,
    DRIVER_DATA_SOURCE,
    DRIVER_STATEMENT,
    DIALECT : DatabaseDialect<DRIVER_DATA_SOURCE, DRIVER_STATEMENT>,
    TRANSACTION : TransactionAtLeastRepeatableRead<DRIVER_DATA_SOURCE, DRIVER_STATEMENT>> :
    InsertTest<TABLE, DRIVER_DATA_SOURCE, DRIVER_STATEMENT, DIALECT, TRANSACTION> {

    @Test
    fun output_returns_values() {
        transactionFactory().repeatableRead {
            val d = transactionFactory().dialect()
            if (d is SupportsInsertReturning) {
                val row = d.run {
                    insertInto(tableTest()) {
                        it[tableTest().a] = "abc"
                        it[tableTest().b] = "bca"
                    }.returning(tableTest().a, tableTest().b)
                }.execute().single()
                row[tableTest().a] shouldBe "abc"
                row[tableTest().b] shouldBe "bca"
            }
        }
    }

    fun DIALECT.x(): DIALECT {
        return this
    }

    @Test
    fun output_returns_values_for_iterable_insert() {
        transactionFactory().repeatableRead {
            if (this is SupportsInsertWithDefaultValue && this is SupportsInsertReturning) {
                val values = (0..100).toList()
                val rows =
                    insertInto(
                        tableTest(),
                        values
                    ) { context, value ->
                        if (value % 2 == 0) {
                            context[tableTest().a] = value.toString()
                        } else {
                            context[tableTest().a] = "abc"
                        }
                        if (value % 3 == 0) {
                            context[tableTest().b] = "bca"
                        }
                    }.returning(tableTest().a, tableTest().b).execute()
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
}
