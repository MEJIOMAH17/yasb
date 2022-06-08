package com.github.mejiomah17.yasb.dsl.join

import com.github.mejiomah17.yasb.core.DatabaseDialect
import com.github.mejiomah17.yasb.core.ddl.Column
import com.github.mejiomah17.yasb.core.ddl.Table
import com.github.mejiomah17.yasb.dsl.eq
import com.github.mejiomah17.yasb.dsl.from
import com.github.mejiomah17.yasb.dsl.select
import com.github.mejiomah17.yasb.dsl.transaction.TransactionFactory
import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.Test

interface TableJoinTest {
    //<editor-fold desc="Inner">
    @Test
    fun `builds correct query for inner join`() {
        dialect().run {
            val query = select(joinColumnFromFirstTable(), joinColumnFromSecondTable())
                .from(firstTable())
                .innerJoin(secondTable()) {
                    joinColumnFromFirstTable().eq(joinColumnFromSecondTable())
                }.buildSelectQuery().value
            query shouldBe "SELECT FIRST.A, SECOND.A FROM FIRST INNER JOIN SECOND ON FIRST.A = SECOND.A"
        }
    }

    @Test
    fun `select values from inner join`() {
        transactionFactory().readCommitted {
            val row = select(dataColumnFromFirstTable(), dataColumnFromSecondTable())
                .from(firstTable())
                .innerJoin(secondTable()) {
                    joinColumnFromFirstTable().eq(joinColumnFromSecondTable())
                }.execute()
                .single()
            row[dataColumnFromFirstTable()] shouldBe "B1"
            row[dataColumnFromSecondTable()] shouldBe "B2"
        }
    }
    //</editor-fold>

    // <editor-fold desc="LEFT">
    @Test
    fun `builds correct query for left join`() {
        dialect().run {
            val query = select(joinColumnFromFirstTable(), joinColumnFromSecondTable())
                .from(firstTable())
                .leftJoin(secondTable()) {
                    joinColumnFromFirstTable().eq(joinColumnFromSecondTable())
                }.buildSelectQuery().value
            query shouldBe "SELECT FIRST.A, SECOND.A FROM FIRST LEFT JOIN SECOND ON FIRST.A = SECOND.A"
        }
    }

    @Test
    fun `select values from left join`() {
        transactionFactory().readCommitted {
            val rows = select(
                joinColumnFromFirstTable(),
                dataColumnFromFirstTable(),
                joinColumnFromSecondTable(),
                dataColumnFromSecondTable()
            )
                .from(firstTable())
                .leftJoin(secondTable()) {
                    joinColumnFromFirstTable().eq(joinColumnFromSecondTable())
                }.execute()
            rows.size shouldBe 2
            rows[0][dataColumnFromFirstTable()] shouldBe "B1"
            rows[0][dataColumnFromSecondTable()] shouldBe "B2"
            rows[1][dataColumnFromFirstTable()] shouldBe "C1"
            rows[1][dataColumnFromSecondTable()] shouldBe null
        }
    }
    //</editor-fold>

    // <editor-fold desc="RIGHT">
    @Test
    fun `builds correct query for right join`() {
        dialect().run {
            val query = select(joinColumnFromFirstTable(), joinColumnFromSecondTable())
                .from(firstTable())
                .rightJoin(secondTable()) {
                    joinColumnFromFirstTable().eq(joinColumnFromSecondTable())
                }.buildSelectQuery().value
            query shouldBe "SELECT FIRST.A, SECOND.A FROM FIRST RIGHT JOIN SECOND ON FIRST.A = SECOND.A"
        }
    }

    @Test
    fun `select values from right join`() {
        transactionFactory().readCommitted {
            val rows = select(
                joinColumnFromFirstTable(),
                dataColumnFromFirstTable(),
                joinColumnFromSecondTable(),
                dataColumnFromSecondTable()
            )
                .from(firstTable())
                .rightJoin(secondTable()) {
                    joinColumnFromFirstTable().eq(joinColumnFromSecondTable())
                }.execute()
            rows.size shouldBe 2
            rows[0][dataColumnFromFirstTable()] shouldBe "B1"
            rows[0][dataColumnFromSecondTable()] shouldBe "B2"
            rows[1][dataColumnFromFirstTable()] shouldBe null
            rows[1][dataColumnFromSecondTable()] shouldBe "D1"
        }
    }
    //</editor-fold>

    // <editor-fold desc="FULL">
    @Test
    fun `builds correct query for full join`() {
        dialect().run {
            val query = select(joinColumnFromFirstTable(), joinColumnFromSecondTable())
                .from(firstTable())
                .fullJoin(secondTable()) {
                    joinColumnFromFirstTable().eq(joinColumnFromSecondTable())
                }.buildSelectQuery().value
            query shouldBe "SELECT FIRST.A, SECOND.A FROM FIRST FULL JOIN SECOND ON FIRST.A = SECOND.A"
        }
    }

    @Test
    fun `select values from full join`() {
        transactionFactory().readCommitted {
            val rows = select(
                joinColumnFromFirstTable(),
                dataColumnFromFirstTable(),
                joinColumnFromSecondTable(),
                dataColumnFromSecondTable()
            )
                .from(firstTable())
                .fullJoin(secondTable()) {
                    joinColumnFromFirstTable().eq(joinColumnFromSecondTable())
                }.execute()
            rows.size shouldBe 3
            rows[0][dataColumnFromFirstTable()] shouldBe "B1"
            rows[0][dataColumnFromSecondTable()] shouldBe "B2"
            rows[1][dataColumnFromFirstTable()] shouldBe "C1"
            rows[1][dataColumnFromSecondTable()] shouldBe null
            rows[2][dataColumnFromFirstTable()] shouldBe null
            rows[2][dataColumnFromSecondTable()] shouldBe "D1"
        }
    }
    //</editor-fold>

    fun firstTable(): Table<*>
    fun joinColumnFromFirstTable(): Column<*, String>
    fun dataColumnFromFirstTable(): Column<*, String>
    fun secondTable(): Table<*>
    fun joinColumnFromSecondTable(): Column<*, String>
    fun dataColumnFromSecondTable(): Column<*, String>
    fun transactionFactory(): TransactionFactory
    fun dialect(): DatabaseDialect

}