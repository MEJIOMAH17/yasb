package com.github.mejiomah17.yasb.dsl

import com.github.mejiomah17.yasb.core.ddl.Table
import com.github.mejiomah17.yasb.core.where
import io.kotest.matchers.collections.shouldHaveSize
import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.Test

interface WhereTest<T: Table<T>> : SelectionTest<T> {
    @Test
    fun `where filters query`() {
        transactionFactory().readUncommitted {
            val queryWithoutWhere = select(columnA(), columnB()).from(tableTest())
            val given = queryWithoutWhere.execute()
            given.shouldHaveSize(2)
            given.get(1)[columnA()].shouldBe("42")

            val result = queryWithoutWhere.where {
                columnA().eq("42")
            }.execute()
            result.shouldHaveSize(1)
            val row = result.single()
            row[columnA()] shouldBe "42"
        }
    }

    @Test
    fun `where builds correct sql for param`() {
        transactionFactory().readUncommitted {
            select(columnA(), columnB())
                .from(tableTest())
                .where {
                    columnA().eq("42")
                }.buildSelectQuery()
                .value shouldBe "SELECT test.a, test.b FROM test WHERE test.a = ?"
        }
    }

    @Test
    fun `where builds correct sql for column`() {
        transactionFactory().readUncommitted {
            select(columnA(), columnB())
                .from(tableTest())
                .where {
                    columnA().eq(columnB())
                }.buildSelectQuery()
                .value shouldBe "SELECT test.a, test.b FROM test WHERE test.a = test.b"
        }
    }

    @Test
    fun `where has correct returnExpressions`() {
        transactionFactory().readUncommitted {
            select(columnA(), columnB())
                .from(tableTest())
                .where {
                    columnA().eq("42")
                }.buildSelectQuery()
                .returnExpressions shouldBe listOf(columnA(), columnB())
        }
    }

    @Test
    fun `where has correct parameters`() {
        transactionFactory().readUncommitted {
            val params = select(columnA(), columnB())
                .from(tableTest())
                .where {
                    columnA().eq("42")
                }.buildSelectQuery()
                .parameters
            params.shouldHaveSize(1)
            params.single().value shouldBe "42"
        }
    }
}