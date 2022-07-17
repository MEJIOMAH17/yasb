package com.github.mejiomah17.yasb.dsl

import com.github.mejiomah17.yasb.core.where
import com.github.mejiomah17.yasb.dsl.transaction.PostgresTransactionFactory
import io.kotest.matchers.collections.shouldHaveSize
import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class PostgresLimitTest : PostgresTest() {
    @BeforeEach
    fun setup() {
        dataSource.connection.use {
            it.createStatement().use {
                it.execute("TRUNCATE TABLE test")
                it.execute(
                    """INSERT INTO test (a,b,c,d) values (
                    |'the a',
                    |'the b',
                    |'3e2220cd-e6a5-4eae-a258-6ed41e91c221',
                    |'2022-05-13 02:09:09.683194'::timestamp
                    | ),
                    | (
                    |'the a',
                    |'the asd',
                    |'3e2220cd-e6a5-4eae-a258-6ed41e91c222',
                    |'2022-05-13 02:09:09.683195'::timestamp
                    | )""".trimMargin()
                )
            }
        }
    }

    @Test
    fun `limit generates correct sql`() {
        transactionFactory().readCommitted {
            select(TestTable.a)
                .from(TestTable)
                .limit(1)
                .buildSelectQuery()
                .sqlDefinition shouldBe "SELECT test.a FROM test LIMIT 1"
        }
    }



    @Test
    fun `limit 1 return single record`() {
        transactionFactory().readCommitted {
            select(TestTable.a)
                .from(TestTable)
                .limit(1)
                .execute() shouldHaveSize 1
        }
    }

    @Test
    fun `limit called after where`() {
        transactionFactory().readCommitted {
            select(TestTable.a)
                .from(TestTable)
                .where { TestTable.a.eq("the a") }
                .limit(1)
                .execute()
        }
    }

    private fun transactionFactory(): PostgresTransactionFactory {
        return PostgresTransactionFactory(dataSource)
    }
}