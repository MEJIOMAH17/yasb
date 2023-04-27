package com.github.mejiomah17.yasb.core.dsl

import com.github.mejiomah17.yasb.core.ddl.Column
import com.github.mejiomah17.yasb.core.query.QueryForExecute

class GroupBy<S> internal constructor(
    private val selectQuery: SelectQuery<S>,
    private val groupingElementList: GroupingElementList
) : GroupByClauseAndSelectQuery<S> {

    override fun buildSelectQuery(): QueryForExecute<S> {
        val query = selectQuery.buildSelectQuery()
        val groupDefinition = groupingElementList.joinToString(",") {
            it.sqlDefinition
        }
        return QueryForExecute(
            sqlDefinition = "${query.sqlDefinition} GROUP BY $groupDefinition",
            parameters = query.parameters,
            returnExpressions = query.returnExpressions
        )
    }
}

fun <S> FromClauseAndSelectQuery<S>.groupBy(
    columns: List<Column<*, *, S>>
): GroupByClauseAndSelectQuery<S> {
    return GroupBy(
        selectQuery = this,
        groupingElementList = columns.map { ColumnReference(it) }
    )
}

fun <S> FromClauseAndSelectQuery<S>.groupBy(
    vararg columns: Column<*, *, S>
): GroupByClauseAndSelectQuery<S> {
    return groupBy(columns.toList())
}

fun <S> WhereClauseAndSelectQuery<S>.groupBy(
    columns: List<Column<*, *, S>>
): GroupByClauseAndSelectQuery<S> {
    return GroupBy(
        selectQuery = this,
        groupingElementList = columns.map { ColumnReference(it) }
    )
}

fun <S> WhereClauseAndSelectQuery<S>.groupBy(
    vararg columns: Column<*, *, S>
): GroupByClauseAndSelectQuery<S> {
    return groupBy(columns.toList())
}

internal typealias GroupingElementList = List<GroupingElement>

internal interface GroupingElement {
    val sqlDefinition: String
}

private class ColumnReference(column: Column<*, *, *>) : GroupingElement {
    override val sqlDefinition: String = column.build().sqlDefinition
}
