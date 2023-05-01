package com.github.mejiomah17.yasb.sqlite.android

import com.github.mejiomah17.yasb.core.dsl.alias.`as`
import com.github.mejiomah17.yasb.core.dsl.count
import com.github.mejiomah17.yasb.core.dsl.from
import com.github.mejiomah17.yasb.core.dsl.select
import io.kotest.matchers.shouldBe
import org.junit.Test

class SqliteAndroidCountTest : SqliteAndroidTest() {
    override fun initSqlScripts(): List<String> {
        return listOf(
            "DELETE FROM test",
            """INSERT INTO test (a,b) values (
                    |'the a',
                    |'the b'
                    | ),
                    | (
                    |'the a',
                    |'the asd'
                    | )
            """.trimMargin()
        )
    }

    @Test
    fun count_returns_count_of_elements() {
        transactionFactory().readCommitted {
            val count = count(SqliteAndroidTestTable.a).`as`("aCount")
            val from = select(count)
                .from(SqliteAndroidTestTable)
            from
                .execute()
                .single()
                .get(count) shouldBe 2
        }
    }
}
