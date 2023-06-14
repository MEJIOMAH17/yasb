package com.github.mejiomah17.yasb.sqlite

import com.github.mejiomah17.yasb.core.dsl.eq
import com.github.mejiomah17.yasb.core.dsl.from
import com.github.mejiomah17.yasb.core.dsl.insertInto
import com.github.mejiomah17.yasb.core.dsl.select
import com.github.mejiomah17.yasb.core.transaction.TransactionAtLeastRepeatableRead
import com.github.mejiomah17.yasb.core.where
import com.github.mejiomah17.yasb.dsl.WhereTest
import io.kotest.matchers.collections.shouldHaveSize
import io.kotest.matchers.shouldBe
import org.junit.Test

interface SqliteWhereTest<
        TABLE : TestSqliteTable<TABLE, DRIVER_DATA_SOURCE, DRIVER_STATEMENT>,
        DRIVER_DATA_SOURCE,
        DRIVER_STATEMENT,
        DIALECT : SqliteDatabaseDialect<DRIVER_DATA_SOURCE, DRIVER_STATEMENT>,
        TRANSACTION : TransactionAtLeastRepeatableRead<DRIVER_DATA_SOURCE, DRIVER_STATEMENT>
        > :
    WhereTest<TABLE, DRIVER_DATA_SOURCE, DRIVER_STATEMENT, DIALECT, TRANSACTION> {

    override fun initSqlScripts(): List<String> {
        return listOf(
            "DELETE from test",
            """INSERT INTO test (a,b,c,d) values (
                    |'the a',
                    |'the b',
                    |3,
                    |false
                    | )
            """.trimMargin(),
            """ INSERT INTO test (a,b,c,d) values (
                    '42',
                    '42',
                    4,
                    true
                    )
            """.trimIndent()
        )
    }

    @Test
    fun `where_filters_query_by_long_column`() {
        transactionFactory().repeatableRead {
            val queryWithoutWhere = select(tableTest().a, tableTest().b).from(tableTest())
            val given = queryWithoutWhere.execute()
            given.shouldHaveSize(2)
            given.get(1)[tableTest().a].shouldBe("42")

            val result = queryWithoutWhere.where {
                tableTest().c.eq(4)
            }.execute()
            result.shouldHaveSize(1)
            val row = result.single()
            row[tableTest().a] shouldBe "42"
        }
    }

    @Test
    fun `where_filters_query_by_bool_column`() {
        transactionFactory().repeatableRead {
            val queryWithoutWhere = select(tableTest().allColumns()).from(tableTest())
            val given = queryWithoutWhere.execute()
            given.shouldHaveSize(2)
            given.get(1)[tableTest().a].shouldBe("42")

            val result = queryWithoutWhere.where {
                tableTest().d.eq(true)
            }.execute()
            result.shouldHaveSize(1)
            val row = result.single()
            row[tableTest().a] shouldBe "42"
            row[tableTest().d] shouldBe true
        }
    }

    @Test
    fun `where_filters_query_by_blob_column`() {
        transactionFactory().repeatableRead {
            val bytes = ByteArray(255) {
                it.toByte()
            }
            insertInto(tableTest()) {
                it[a] = "bloba"
                it[b] = "blobb"
                it[c] = 33
                it[d] = false
                it[e] = bytes
            }.execute()
            val queryWithoutWhere = select(tableTest().allColumns()).from(tableTest())
            val given = queryWithoutWhere.execute()
            given.shouldHaveSize(3)
            given.get(1)[tableTest().a].shouldBe("42")

            val result = queryWithoutWhere.where {
                tableTest().e.eq(bytes)
            }.execute()
            result.shouldHaveSize(1)
            val row = result.single()
            row[tableTest().a] shouldBe "bloba"
            row[tableTest().e] shouldBe bytes
        }
    }
}
