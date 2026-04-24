package com.github.mejiomah17.yaksb.dsl

import com.github.mejiomah17.yaksb.core.DatabaseDialect
import com.github.mejiomah17.yaksb.core.SupportsInsertReturning
import com.github.mejiomah17.yaksb.core.SupportsInsertWithDefaultValue
import com.github.mejiomah17.yaksb.core.dsl.insertInto
import com.github.mejiomah17.yaksb.core.dsl.returning
import com.github.mejiomah17.yaksb.core.transaction.TransactionAtLeastRepeatableRead
import io.kotest.matchers.shouldBe
import kotlin.test.Test

interface InsertWithReturningTest<
    TABLE : TestTable<TABLE, DRIVER_DATA_SOURCE, DRIVER_STATEMENT>,
    DRIVER_DATA_SOURCE,
    DRIVER_STATEMENT,
    DIALECT : DatabaseDialect<DRIVER_DATA_SOURCE, DRIVER_STATEMENT>,
    TRANSACTION : TransactionAtLeastRepeatableRead<DRIVER_DATA_SOURCE, DRIVER_STATEMENT>,
> : InsertTest<TABLE, DRIVER_DATA_SOURCE, DRIVER_STATEMENT, DIALECT, TRANSACTION> {
    @Test
    fun output_returns_values() {
        readCommitedTransaction {
            if (databaseDialect is SupportsInsertReturning) {
                val d = databaseDialect as SupportsInsertReturning
                val row =
                    d
                        .run {
                            insertInto(tableTest()) {
                                it[tableTest().a] = "abc"
                                it[tableTest().b] = "bca"
                            }.returning(tableTest().a, tableTest().b)
                        }.execute()
                        .single()
                row[tableTest().a] shouldBe "abc"
                row[tableTest().b] shouldBe "bca"
            }
        }
    }

    fun DIALECT.x(): DIALECT = this

    @Test
    fun output_returns_values_for_iterable_insert() {
        readCommitedTransaction {
            if (this is SupportsInsertWithDefaultValue && this is SupportsInsertReturning) {
                val values = (0..100).toList()
                val rows =
                    insertInto(
                        tableTest(),
                        values,
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
