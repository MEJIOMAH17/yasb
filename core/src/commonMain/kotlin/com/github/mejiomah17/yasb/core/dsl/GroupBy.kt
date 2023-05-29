package com.github.mejiomah17.yasb.core.dsl

import com.github.mejiomah17.yasb.core.ddl.Column
import com.github.mejiomah17.yasb.core.expression.Expression
import com.github.mejiomah17.yasb.core.parameter.Parameter

class GroupBy<DRIVER_DATA_SOURCE, DRIVER_STATEMENT> internal constructor(
    private val query: SelectQuery<DRIVER_DATA_SOURCE, DRIVER_STATEMENT>,
    private val groupingElementList: GroupingElementList
) : GroupByClauseAndSelectQuery<DRIVER_DATA_SOURCE, DRIVER_STATEMENT> {

    override fun returnExpressions(): List<Expression<*, DRIVER_DATA_SOURCE, DRIVER_STATEMENT>> {
        return query.returnExpressions()
    }

    override fun sql(): String {
        val groupDefinition = groupingElementList.joinToString(",") {
            it.sqlDefinition
        }
        return "${query.sql()} GROUP BY $groupDefinition"
    }

    override fun parameters(): List<Parameter<*, DRIVER_DATA_SOURCE, DRIVER_STATEMENT>> {
        return query.parameters()
    }
}

fun <DRIVER_DATA_SOURCE, DRIVER_STATEMENT> FromClauseAndSelectQuery<DRIVER_DATA_SOURCE, DRIVER_STATEMENT>.groupBy(
    columns: List<Column<*, *, DRIVER_DATA_SOURCE, DRIVER_STATEMENT>>
): GroupByClauseAndSelectQuery<DRIVER_DATA_SOURCE, DRIVER_STATEMENT> {
    return GroupBy(
        query = this,
        groupingElementList = columns.map { ColumnReference(it) }
    )
}

fun <DRIVER_DATA_SOURCE, DRIVER_STATEMENT> FromClauseAndSelectQuery<DRIVER_DATA_SOURCE, DRIVER_STATEMENT>.groupBy(
    vararg columns: Column<*, *, DRIVER_DATA_SOURCE, DRIVER_STATEMENT>
): GroupByClauseAndSelectQuery<DRIVER_DATA_SOURCE, DRIVER_STATEMENT> {
    return groupBy(columns.toList())
}

fun <DRIVER_DATA_SOURCE, DRIVER_STATEMENT> WhereClauseAndSelectQuery<DRIVER_DATA_SOURCE, DRIVER_STATEMENT>.groupBy(
    columns: List<Column<*, *, DRIVER_DATA_SOURCE, DRIVER_STATEMENT>>
): GroupByClauseAndSelectQuery<DRIVER_DATA_SOURCE, DRIVER_STATEMENT> {
    return GroupBy(
        query = this,
        groupingElementList = columns.map { ColumnReference(it) }
    )
}

fun <DRIVER_DATA_SOURCE, DRIVER_STATEMENT> WhereClauseAndSelectQuery<DRIVER_DATA_SOURCE, DRIVER_STATEMENT>.groupBy(
    vararg columns: Column<*, *, DRIVER_DATA_SOURCE, DRIVER_STATEMENT>
): GroupByClauseAndSelectQuery<DRIVER_DATA_SOURCE, DRIVER_STATEMENT> {
    return groupBy(columns.toList())
}

internal typealias GroupingElementList = List<GroupingElement>

internal interface GroupingElement {
    val sqlDefinition: String
}

private class ColumnReference(column: Column<*, *, *, *>) : GroupingElement {
    override val sqlDefinition: String = column.sql()
}
