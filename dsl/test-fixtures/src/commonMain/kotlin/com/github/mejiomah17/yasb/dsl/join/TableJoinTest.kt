package com.github.mejiomah17.yasb.dsl.join

import com.github.mejiomah17.yasb.core.DatabaseDialect
import com.github.mejiomah17.yasb.core.ddl.Column
import com.github.mejiomah17.yasb.core.ddl.Table
import com.github.mejiomah17.yasb.dsl.alias.`as`
import com.github.mejiomah17.yasb.dsl.eq
import com.github.mejiomah17.yasb.dsl.from
import com.github.mejiomah17.yasb.dsl.select
import com.github.mejiomah17.yasb.dsl.transaction.TransactionFactory
import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.Test

interface TableJoinTest<T : Table<T, S>, S, D : DatabaseDialect<S>> {
    // <editor-fold desc="Inner">
    @Test
    fun `builds correct query for inner join`() {
        dialect().run {
            val query = select(joinColumnFromFirstTable(), joinColumnFromSecondTable())
                .from(firstTable())
                .innerJoin(secondTable()) {
                    joinColumnFromFirstTable().eq(joinColumnFromSecondTable())
                }.buildSelectQuery().sqlDefinition
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

    @Test
    fun `builds correct query for inner join with aliased table`() {
        dialect().run {
            val secondTable = secondTable().`as`("xxx")
            val joinColumnFromSecondTable = secondTable[joinColumnFromSecondTable()]
            val query = select(joinColumnFromFirstTable(), joinColumnFromSecondTable)
                .from(firstTable())
                .innerJoin(secondTable) {
                    joinColumnFromFirstTable().eq(joinColumnFromSecondTable)
                }.buildSelectQuery().sqlDefinition
            query shouldBe "SELECT FIRST.A, xxx.A FROM FIRST INNER JOIN SECOND AS xxx ON FIRST.A = xxx.A"
        }
    }

    @Test
    fun `select values from inner join with aliased table`() {
        transactionFactory().readCommitted {
            val secondTable = secondTable().`as`("xxx")
            val joinColumnFromSecondTable = secondTable[joinColumnFromSecondTable()]
            val dataColumnFromSecondTable = secondTable[dataColumnFromSecondTable()]
            val row = select(dataColumnFromFirstTable(), dataColumnFromSecondTable)
                .from(firstTable())
                .innerJoin(secondTable) {
                    joinColumnFromFirstTable().eq(joinColumnFromSecondTable)
                }.execute()
                .single()
            row[dataColumnFromFirstTable()] shouldBe "B1"
            row[dataColumnFromSecondTable] shouldBe "B2"
        }
    }

    @Test
    fun `builds correct query for inner join with nested query`() {
        dialect().run {
            val nestedQuery = select(joinColumnFromSecondTable())
                .from(secondTable())
                .`as`("xxx")
            val joinColumnFromSecondTable = nestedQuery[joinColumnFromSecondTable()]
            val query = select(joinColumnFromFirstTable(), joinColumnFromSecondTable)
                .from(firstTable())
                .innerJoin(nestedQuery) {
                    joinColumnFromFirstTable().eq(joinColumnFromSecondTable)
                }.buildSelectQuery().sqlDefinition
            query shouldBe "SELECT FIRST.A, xxx.A FROM FIRST INNER JOIN (SELECT SECOND.A FROM SECOND) AS xxx ON FIRST.A = xxx.A"
        }
    }

    @Test
    fun `select values from inner join with nested query`() {
        transactionFactory().readCommitted {
            val nestedQuery = select(joinColumnFromSecondTable(), dataColumnFromSecondTable())
                .from(secondTable())
                .`as`("xxx")
            val joinColumnFromSecondTable = nestedQuery[joinColumnFromSecondTable()]
            val dataColumnFromSecondTable = nestedQuery[dataColumnFromSecondTable()]
            val row = select(dataColumnFromFirstTable(), dataColumnFromSecondTable)
                .from(firstTable())
                .innerJoin(nestedQuery) {
                    joinColumnFromFirstTable().eq(joinColumnFromSecondTable)
                }.execute()
                .single()
            row[dataColumnFromFirstTable()] shouldBe "B1"
            row[dataColumnFromSecondTable] shouldBe "B2"
        }
    }
    // </editor-fold>

    // <editor-fold desc="LEFT">
    @Test
    fun `builds correct query for left join`() {
        dialect().run {
            val query = select(joinColumnFromFirstTable(), joinColumnFromSecondTable())
                .from(firstTable())
                .leftJoin(secondTable()) {
                    joinColumnFromFirstTable().eq(joinColumnFromSecondTable())
                }.buildSelectQuery().sqlDefinition
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

    @Test
    fun `builds correct query for left join with aliased table`() {
        val secondTable = secondTable().`as`("xxx")
        val joinColumnFromSecondTable = secondTable[joinColumnFromSecondTable()]
        dialect().run {
            val query = select(joinColumnFromFirstTable(), joinColumnFromSecondTable)
                .from(firstTable())
                .leftJoin(secondTable) {
                    joinColumnFromFirstTable().eq(joinColumnFromSecondTable)
                }.buildSelectQuery().sqlDefinition
            query shouldBe "SELECT FIRST.A, xxx.A FROM FIRST LEFT JOIN SECOND AS xxx ON FIRST.A = xxx.A"
        }
    }

    @Test
    fun `select values from left join with aliased table`() {
        transactionFactory().readCommitted {
            val secondTable = secondTable().`as`("xxx")
            val joinColumnFromSecondTable = secondTable[joinColumnFromSecondTable()]
            val dataColumnFromSecondTable = secondTable[dataColumnFromSecondTable()]
            val rows = select(
                joinColumnFromFirstTable(),
                dataColumnFromFirstTable(),
                joinColumnFromSecondTable,
                dataColumnFromSecondTable
            )
                .from(firstTable())
                .leftJoin(secondTable) {
                    joinColumnFromFirstTable().eq(joinColumnFromSecondTable)
                }.execute()
            rows.size shouldBe 2
            rows[0][dataColumnFromFirstTable()] shouldBe "B1"
            rows[0][dataColumnFromSecondTable] shouldBe "B2"
            rows[1][dataColumnFromFirstTable()] shouldBe "C1"
            rows[1][dataColumnFromSecondTable] shouldBe null
        }
    }

    @Test
    fun `builds correct query for left join with nested query`() {
        dialect().run {
            val nestedQuery = select(joinColumnFromSecondTable())
                .from(secondTable())
                .`as`("xxx")
            val joinColumnFromSecondTable = nestedQuery[joinColumnFromSecondTable()]
            val query = select(joinColumnFromFirstTable(), joinColumnFromSecondTable)
                .from(firstTable())
                .leftJoin(nestedQuery) {
                    joinColumnFromFirstTable().eq(joinColumnFromSecondTable)
                }.buildSelectQuery().sqlDefinition
            query shouldBe "SELECT FIRST.A, xxx.A FROM FIRST LEFT JOIN (SELECT SECOND.A FROM SECOND) AS xxx ON FIRST.A = xxx.A"
        }
    }

    @Test
    fun `select values from left join with nested query`() {
        transactionFactory().readCommitted {
            val nestedQuery = select(joinColumnFromSecondTable(), dataColumnFromSecondTable())
                .from(secondTable())
                .`as`("xxx")
            val joinColumnFromSecondTable = nestedQuery[joinColumnFromSecondTable()]
            val dataColumnFromSecondTable = nestedQuery[dataColumnFromSecondTable()]
            val rows = select(
                joinColumnFromFirstTable(),
                dataColumnFromFirstTable(),
                joinColumnFromSecondTable,
                dataColumnFromSecondTable
            )
                .from(firstTable())
                .leftJoin(nestedQuery) {
                    joinColumnFromFirstTable().eq(joinColumnFromSecondTable)
                }.execute()
            rows.size shouldBe 2
            rows[0][dataColumnFromFirstTable()] shouldBe "B1"
            rows[0][dataColumnFromSecondTable] shouldBe "B2"
            rows[1][dataColumnFromFirstTable()] shouldBe "C1"
            rows[1][dataColumnFromSecondTable] shouldBe null
        }
    }

    // </editor-fold>

    // <editor-fold desc="RIGHT">
    @Test
    fun `builds correct query for right join`() {
        dialect().run {
            val query = select(joinColumnFromFirstTable(), joinColumnFromSecondTable())
                .from(firstTable())
                .rightJoin(secondTable()) {
                    joinColumnFromFirstTable().eq(joinColumnFromSecondTable())
                }.buildSelectQuery().sqlDefinition
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

    @Test
    fun `builds correct query for right join with aliased table`() {
        dialect().run {
            val secondTable = secondTable().`as`("xxx")
            val joinColumnFromSecondTable = secondTable[joinColumnFromSecondTable()]
            val query = select(joinColumnFromFirstTable(), joinColumnFromSecondTable)
                .from(firstTable())
                .rightJoin(secondTable) {
                    joinColumnFromFirstTable().eq(joinColumnFromSecondTable)
                }.buildSelectQuery().sqlDefinition
            query shouldBe "SELECT FIRST.A, xxx.A FROM FIRST RIGHT JOIN SECOND AS xxx ON FIRST.A = xxx.A"
        }
    }

    @Test
    fun `select values from right join with aliased table`() {
        transactionFactory().readCommitted {
            val secondTable = secondTable().`as`("xxx")
            val joinColumnFromSecondTable = secondTable[joinColumnFromSecondTable()]
            val dataColumnFromSecondTable = secondTable[dataColumnFromSecondTable()]
            val rows = select(
                joinColumnFromFirstTable(),
                dataColumnFromFirstTable(),
                joinColumnFromSecondTable,
                dataColumnFromSecondTable
            )
                .from(firstTable())
                .rightJoin(secondTable) {
                    joinColumnFromFirstTable().eq(joinColumnFromSecondTable)
                }.execute()
            rows.size shouldBe 2
            rows[0][dataColumnFromFirstTable()] shouldBe "B1"
            rows[0][dataColumnFromSecondTable] shouldBe "B2"
            rows[1][dataColumnFromFirstTable()] shouldBe null
            rows[1][dataColumnFromSecondTable] shouldBe "D1"
        }
    }

    @Test
    fun `builds correct query for right join with nested query`() {
        dialect().run {
            val nestedQuery = select(joinColumnFromSecondTable())
                .from(secondTable())
                .`as`("xxx")
            val joinColumnFromSecondTable = nestedQuery[joinColumnFromSecondTable()]
            val query = select(joinColumnFromFirstTable(), joinColumnFromSecondTable)
                .from(firstTable())
                .rightJoin(nestedQuery) {
                    joinColumnFromFirstTable().eq(joinColumnFromSecondTable)
                }.buildSelectQuery().sqlDefinition
            query shouldBe "SELECT FIRST.A, xxx.A FROM FIRST RIGHT JOIN (SELECT SECOND.A FROM SECOND) AS xxx ON FIRST.A = xxx.A"
        }
    }

    @Test
    fun `select values from right join with nested query`() {
        transactionFactory().readCommitted {
            val nestedQuery = select(joinColumnFromSecondTable(), dataColumnFromSecondTable())
                .from(secondTable())
                .`as`("xxx")
            val joinColumnFromSecondTable = nestedQuery[joinColumnFromSecondTable()]
            val dataColumnFromSecondTable = nestedQuery[dataColumnFromSecondTable()]
            val rows = select(
                joinColumnFromFirstTable(),
                dataColumnFromFirstTable(),
                joinColumnFromSecondTable,
                dataColumnFromSecondTable
            )
                .from(firstTable())
                .rightJoin(nestedQuery) {
                    joinColumnFromFirstTable().eq(joinColumnFromSecondTable)
                }.execute()
            rows.size shouldBe 2
            rows[0][dataColumnFromFirstTable()] shouldBe "B1"
            rows[0][dataColumnFromSecondTable] shouldBe "B2"
            rows[1][dataColumnFromFirstTable()] shouldBe null
            rows[1][dataColumnFromSecondTable] shouldBe "D1"
        }
    }
    // </editor-fold>

    // <editor-fold desc="FULL">
    @Test
    fun `builds correct query for full join`() {
        dialect().run {
            val query = select(joinColumnFromFirstTable(), joinColumnFromSecondTable())
                .from(firstTable())
                .fullJoin(secondTable()) {
                    joinColumnFromFirstTable().eq(joinColumnFromSecondTable())
                }.buildSelectQuery().sqlDefinition
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

    @Test
    fun `builds correct query for full join with aliased table`() {
        dialect().run {
            val secondTable = secondTable().`as`("xxx")
            val joinColumnFromSecondTable = secondTable[joinColumnFromSecondTable()]
            val query = select(joinColumnFromFirstTable(), joinColumnFromSecondTable)
                .from(firstTable())
                .fullJoin(secondTable) {
                    joinColumnFromFirstTable().eq(joinColumnFromSecondTable)
                }.buildSelectQuery().sqlDefinition
            query shouldBe "SELECT FIRST.A, xxx.A FROM FIRST FULL JOIN SECOND AS xxx ON FIRST.A = xxx.A"
        }
    }

    @Test
    fun `select values from full join with aliased table`() {
        transactionFactory().readCommitted {
            val secondTable = secondTable().`as`("xxx")
            val joinColumnFromSecondTable = secondTable[joinColumnFromSecondTable()]
            val dataColumnFromSecondTable = secondTable[dataColumnFromSecondTable()]
            val rows = select(
                joinColumnFromFirstTable(),
                dataColumnFromFirstTable(),
                joinColumnFromSecondTable,
                dataColumnFromSecondTable
            )
                .from(firstTable())
                .fullJoin(secondTable) {
                    joinColumnFromFirstTable().eq(joinColumnFromSecondTable)
                }.execute()
            rows.size shouldBe 3
            rows[0][dataColumnFromFirstTable()] shouldBe "B1"
            rows[0][dataColumnFromSecondTable] shouldBe "B2"
            rows[1][dataColumnFromFirstTable()] shouldBe "C1"
            rows[1][dataColumnFromSecondTable] shouldBe null
            rows[2][dataColumnFromFirstTable()] shouldBe null
            rows[2][dataColumnFromSecondTable] shouldBe "D1"
        }
    }

    @Test
    fun `builds correct query for full join with nested query`() {
        dialect().run {
            val nestedQuery = select(joinColumnFromSecondTable())
                .from(secondTable())
                .`as`("xxx")
            val joinColumnFromSecondTable = nestedQuery[joinColumnFromSecondTable()]
            val query = select(joinColumnFromFirstTable(), joinColumnFromSecondTable)
                .from(firstTable())
                .fullJoin(nestedQuery) {
                    joinColumnFromFirstTable().eq(joinColumnFromSecondTable)
                }.buildSelectQuery().sqlDefinition
            query shouldBe "SELECT FIRST.A, xxx.A FROM FIRST FULL JOIN (SELECT SECOND.A FROM SECOND) AS xxx ON FIRST.A = xxx.A"
        }
    }

    @Test
    fun `select values from full join with nested query`() {
        transactionFactory().readCommitted {
            val nestedQuery = select(joinColumnFromSecondTable(), dataColumnFromSecondTable())
                .from(secondTable())
                .`as`("xxx")
            val joinColumnFromSecondTable = nestedQuery[joinColumnFromSecondTable()]
            val dataColumnFromSecondTable = nestedQuery[dataColumnFromSecondTable()]
            val rows = select(
                joinColumnFromFirstTable(),
                dataColumnFromFirstTable(),
                joinColumnFromSecondTable,
                dataColumnFromSecondTable
            )
                .from(firstTable())
                .fullJoin(nestedQuery) {
                    joinColumnFromFirstTable().eq(joinColumnFromSecondTable)
                }.execute()
            rows.size shouldBe 3
            rows[0][dataColumnFromFirstTable()] shouldBe "B1"
            rows[0][dataColumnFromSecondTable] shouldBe "B2"
            rows[1][dataColumnFromFirstTable()] shouldBe "C1"
            rows[1][dataColumnFromSecondTable] shouldBe null
            rows[2][dataColumnFromFirstTable()] shouldBe null
            rows[2][dataColumnFromSecondTable] shouldBe "D1"
        }
    }
    // </editor-fold>

    // <editor-fold desc="Multiple joins">
    @Test
    fun `builds correct query for multiple join`() {
        dialect().run {
            val query = select(joinColumnFromFirstTable(), joinColumnFromSecondTable(), joinColumnFromThirdTable())
                .from(firstTable())
                .leftJoin(secondTable()) {
                    joinColumnFromFirstTable().eq(joinColumnFromSecondTable())
                }.leftJoin(thirdTable()) {
                    joinColumnFromFirstTable().eq(joinColumnFromThirdTable())
                }
                .buildSelectQuery().sqlDefinition
            query shouldBe "SELECT FIRST.A, SECOND.A, THIRD.A FROM FIRST LEFT JOIN SECOND ON FIRST.A = SECOND.A LEFT JOIN THIRD ON FIRST.A = THIRD.A"
        }
    }

    @Test
    fun `select values from for multiple join`() {
        transactionFactory().readCommitted {
            val rows = select(
                joinColumnFromFirstTable(),
                dataColumnFromFirstTable(),
                joinColumnFromSecondTable(),
                dataColumnFromSecondTable(),
                dataColumnFromThirdTable()
            )
                .from(firstTable())
                .leftJoin(secondTable()) {
                    joinColumnFromFirstTable().eq(joinColumnFromSecondTable())
                }.leftJoin(thirdTable()) {
                    joinColumnFromFirstTable().eq(joinColumnFromThirdTable())
                }.execute()
            rows.size shouldBe 2
            rows[0][dataColumnFromFirstTable()] shouldBe "B1"
            rows[0][dataColumnFromSecondTable()] shouldBe "B2"
            rows[0][dataColumnFromThirdTable()] shouldBe "B3"
            rows[1][dataColumnFromFirstTable()] shouldBe "C1"
            rows[1][dataColumnFromSecondTable()] shouldBe null
            rows[1][dataColumnFromThirdTable()] shouldBe null
        }
    }

    @Test
    fun `builds correct query for multiple join with aliased table`() {
        dialect().run {
            val secondTable = secondTable().`as`("xxx")
            val joinColumnFromSecondTable = secondTable[joinColumnFromSecondTable()]
            val query = select(joinColumnFromFirstTable(), joinColumnFromSecondTable, joinColumnFromThirdTable())
                .from(firstTable())
                .leftJoin(secondTable) {
                    joinColumnFromFirstTable().eq(joinColumnFromSecondTable)
                }.leftJoin(thirdTable()) {
                    joinColumnFromFirstTable().eq(joinColumnFromThirdTable())
                }
                .buildSelectQuery().sqlDefinition
            query shouldBe "SELECT FIRST.A, xxx.A, THIRD.A FROM FIRST LEFT JOIN SECOND AS xxx ON FIRST.A = xxx.A LEFT JOIN THIRD ON FIRST.A = THIRD.A"
        }
    }

    @Test
    fun `select values from for multiple join with aliased table`() {
        transactionFactory().readCommitted {
            val secondTable = secondTable().`as`("xxx")
            val joinColumnFromSecondTable = secondTable[joinColumnFromSecondTable()]
            val dataColumnFromSecondTable = secondTable[dataColumnFromSecondTable()]
            val rows = select(
                joinColumnFromFirstTable(),
                dataColumnFromFirstTable(),
                joinColumnFromSecondTable,
                dataColumnFromSecondTable,
                dataColumnFromThirdTable()
            )
                .from(firstTable())
                .leftJoin(secondTable) {
                    joinColumnFromFirstTable().eq(joinColumnFromSecondTable)
                }.leftJoin(thirdTable()) {
                    joinColumnFromFirstTable().eq(joinColumnFromThirdTable())
                }.execute()
            rows.size shouldBe 2
            rows[0][dataColumnFromFirstTable()] shouldBe "B1"
            rows[0][dataColumnFromSecondTable] shouldBe "B2"
            rows[0][dataColumnFromThirdTable()] shouldBe "B3"
            rows[1][dataColumnFromFirstTable()] shouldBe "C1"
            rows[1][dataColumnFromSecondTable] shouldBe null
            rows[1][dataColumnFromThirdTable()] shouldBe null
        }
    }

    @Test
    fun `builds correct query for multiple join with nested query`() {
        dialect().run {
            val nestedQuery = select(joinColumnFromSecondTable())
                .from(secondTable())
                .`as`("xxx")
            val joinColumnFromSecondTable = nestedQuery[joinColumnFromSecondTable()]
            val query = select(joinColumnFromFirstTable(), joinColumnFromSecondTable, joinColumnFromThirdTable())
                .from(firstTable())
                .leftJoin(nestedQuery) {
                    joinColumnFromFirstTable().eq(joinColumnFromSecondTable)
                }.leftJoin(thirdTable()) {
                    joinColumnFromFirstTable().eq(joinColumnFromThirdTable())
                }.buildSelectQuery().sqlDefinition
            query shouldBe "SELECT FIRST.A, xxx.A, THIRD.A FROM FIRST LEFT JOIN (SELECT SECOND.A FROM SECOND) AS xxx ON FIRST.A = xxx.A LEFT JOIN THIRD ON FIRST.A = THIRD.A"
        }
    }

    @Test
    fun `select values from multiple join with nested query`() {
        transactionFactory().readCommitted {
            val nestedQuery = select(joinColumnFromSecondTable(), dataColumnFromSecondTable())
                .from(secondTable())
                .`as`("xxx")
            val joinColumnFromSecondTable = nestedQuery[joinColumnFromSecondTable()]
            val dataColumnFromSecondTable = nestedQuery[dataColumnFromSecondTable()]
            val rows = select(
                joinColumnFromFirstTable(),
                dataColumnFromFirstTable(),
                joinColumnFromSecondTable,
                dataColumnFromSecondTable,
                dataColumnFromThirdTable()
            )
                .from(firstTable())
                .leftJoin(nestedQuery) {
                    joinColumnFromFirstTable().eq(joinColumnFromSecondTable)
                }.leftJoin(thirdTable()) {
                    joinColumnFromFirstTable().eq(joinColumnFromThirdTable())
                }.execute()
            rows.size shouldBe 2
            rows[0][dataColumnFromFirstTable()] shouldBe "B1"
            rows[0][dataColumnFromSecondTable] shouldBe "B2"
            rows[0][dataColumnFromThirdTable()] shouldBe "B3"
            rows[1][dataColumnFromFirstTable()] shouldBe "C1"
            rows[1][dataColumnFromSecondTable] shouldBe null
            rows[1][dataColumnFromThirdTable()] shouldBe null
        }
    }
    // </editor-fold>

    fun firstTable(): Table<*, S>
    fun joinColumnFromFirstTable(): Column<*, String, S>
    fun dataColumnFromFirstTable(): Column<*, String, S>
    fun secondTable(): T
    fun joinColumnFromSecondTable(): Column<T, String, S>
    fun dataColumnFromSecondTable(): Column<T, String, S>
    fun thirdTable(): Table<*, S>
    fun joinColumnFromThirdTable(): Column<*, String, S>
    fun dataColumnFromThirdTable(): Column<*, String, S>
    fun transactionFactory(): TransactionFactory<D, S>
    fun dialect(): D
}
