@file:Suppress("UNSUPPORTED_FEATURE", "UNSUPPORTED_CONTEXTUAL_DECLARATION_CALL")

package com.github.mejiomah17.yasb.core.dsl

import com.github.mejiomah17.yasb.core.DatabaseDialect
import com.github.mejiomah17.yasb.core.DatabaseType
import com.github.mejiomah17.yasb.core.expression.AliasableExpressionForCondition
import com.github.mejiomah17.yasb.core.expression.ExpressionForCondition
import com.github.mejiomah17.yasb.core.parameter.Parameter
import com.github.mejiomah17.yasb.core.query.QueryPart
import com.github.mejiomah17.yasb.core.query.QueryPartImpl

object ConditionContext

context (ConditionContext, DatabaseDialect<DRIVER_DATA_SOURCE, DRIVER_STATEMENT>)
fun <T, DRIVER_DATA_SOURCE, DRIVER_STATEMENT> ExpressionForCondition<T, DRIVER_DATA_SOURCE, DRIVER_STATEMENT>.eq(other: ExpressionForCondition<T, DRIVER_DATA_SOURCE, DRIVER_STATEMENT>): AliasableExpressionForCondition<Boolean, DRIVER_DATA_SOURCE, DRIVER_STATEMENT> {
    return condition(other, "=")
}

context (ConditionContext, DatabaseDialect<DRIVER_DATA_SOURCE, DRIVER_STATEMENT>)
fun <T, DRIVER_DATA_SOURCE, DRIVER_STATEMENT> ExpressionForCondition<T, DRIVER_DATA_SOURCE, DRIVER_STATEMENT>.eq(other: Parameter<T, DRIVER_DATA_SOURCE, DRIVER_STATEMENT>): AliasableExpressionForCondition<Boolean, DRIVER_DATA_SOURCE, DRIVER_STATEMENT> {
    return condition(other, "=")
}

context (ConditionContext, DatabaseDialect<DRIVER_DATA_SOURCE, DRIVER_STATEMENT>)
fun <T, DRIVER_DATA_SOURCE, DRIVER_STATEMENT> ExpressionForCondition<T, DRIVER_DATA_SOURCE, DRIVER_STATEMENT>.eq(other: T): AliasableExpressionForCondition<Boolean, DRIVER_DATA_SOURCE, DRIVER_STATEMENT> {
    return eq(databaseType().parameterFactory().invoke(other))
}

context (ConditionContext, DatabaseDialect<DRIVER_DATA_SOURCE, DRIVER_STATEMENT>)
fun <T, DRIVER_DATA_SOURCE, DRIVER_STATEMENT> ExpressionForCondition<T, DRIVER_DATA_SOURCE, DRIVER_STATEMENT>.greater(
    other: ExpressionForCondition<T, DRIVER_DATA_SOURCE, DRIVER_STATEMENT>
): AliasableExpressionForCondition<Boolean, DRIVER_DATA_SOURCE, DRIVER_STATEMENT> {
    return condition(other, ">")
}

context (ConditionContext, DatabaseDialect<DRIVER_DATA_SOURCE, DRIVER_STATEMENT>)
fun <T, DRIVER_DATA_SOURCE, DRIVER_STATEMENT> ExpressionForCondition<T, DRIVER_DATA_SOURCE, DRIVER_STATEMENT>.greater(
    other: Parameter<T, DRIVER_DATA_SOURCE, DRIVER_STATEMENT>
): AliasableExpressionForCondition<Boolean, DRIVER_DATA_SOURCE, DRIVER_STATEMENT> {
    return condition(other, ">")
}

context (ConditionContext, DatabaseDialect<DRIVER_DATA_SOURCE, DRIVER_STATEMENT>)
fun <T, DRIVER_DATA_SOURCE, DRIVER_STATEMENT> ExpressionForCondition<T, DRIVER_DATA_SOURCE, DRIVER_STATEMENT>.greater(
    other: T
): AliasableExpressionForCondition<Boolean, DRIVER_DATA_SOURCE, DRIVER_STATEMENT> {
    return greater(databaseType().parameterFactory().invoke(other))
}

context (ConditionContext, DatabaseDialect<DRIVER_DATA_SOURCE, DRIVER_STATEMENT>)
fun <T, DRIVER_DATA_SOURCE, DRIVER_STATEMENT> ExpressionForCondition<T, DRIVER_DATA_SOURCE, DRIVER_STATEMENT>.greaterEq(
    other: ExpressionForCondition<T, DRIVER_DATA_SOURCE, DRIVER_STATEMENT>
): AliasableExpressionForCondition<Boolean, DRIVER_DATA_SOURCE, DRIVER_STATEMENT> {
    return condition(other, ">=")
}

context (ConditionContext, DatabaseDialect<DRIVER_DATA_SOURCE, DRIVER_STATEMENT>)
fun <T, DRIVER_DATA_SOURCE, DRIVER_STATEMENT> ExpressionForCondition<T, DRIVER_DATA_SOURCE, DRIVER_STATEMENT>.greaterEq(
    other: Parameter<T, DRIVER_DATA_SOURCE, DRIVER_STATEMENT>
): AliasableExpressionForCondition<Boolean, DRIVER_DATA_SOURCE, DRIVER_STATEMENT> {
    return condition(other, ">=")
}

context (ConditionContext, DatabaseDialect<DRIVER_DATA_SOURCE, DRIVER_STATEMENT>)
fun <T, DRIVER_DATA_SOURCE, DRIVER_STATEMENT> ExpressionForCondition<T, DRIVER_DATA_SOURCE, DRIVER_STATEMENT>.greaterEq(
    other: T
): AliasableExpressionForCondition<Boolean, DRIVER_DATA_SOURCE, DRIVER_STATEMENT> {
    return greaterEq(databaseType().parameterFactory().invoke(other))
}

context (ConditionContext, DatabaseDialect<DRIVER_DATA_SOURCE, DRIVER_STATEMENT>)
fun <T, DRIVER_DATA_SOURCE, DRIVER_STATEMENT> ExpressionForCondition<T, DRIVER_DATA_SOURCE, DRIVER_STATEMENT>.less(other: ExpressionForCondition<T, DRIVER_DATA_SOURCE, DRIVER_STATEMENT>): AliasableExpressionForCondition<Boolean, DRIVER_DATA_SOURCE, DRIVER_STATEMENT> {
    return condition(other, "<")
}

context (ConditionContext, DatabaseDialect<DRIVER_DATA_SOURCE, DRIVER_STATEMENT>)
fun <T, DRIVER_DATA_SOURCE, DRIVER_STATEMENT> ExpressionForCondition<T, DRIVER_DATA_SOURCE, DRIVER_STATEMENT>.less(other: Parameter<T, DRIVER_DATA_SOURCE, DRIVER_STATEMENT>): AliasableExpressionForCondition<Boolean, DRIVER_DATA_SOURCE, DRIVER_STATEMENT> {
    return condition(other, "<")
}

context (ConditionContext, DatabaseDialect<DRIVER_DATA_SOURCE, DRIVER_STATEMENT>)
fun <T, DRIVER_DATA_SOURCE, DRIVER_STATEMENT> ExpressionForCondition<T, DRIVER_DATA_SOURCE, DRIVER_STATEMENT>.less(other: T): AliasableExpressionForCondition<Boolean, DRIVER_DATA_SOURCE, DRIVER_STATEMENT> {
    return less(databaseType().parameterFactory().invoke(other))
}

context (ConditionContext, DatabaseDialect<DRIVER_DATA_SOURCE, DRIVER_STATEMENT>)
fun <T, DRIVER_DATA_SOURCE, DRIVER_STATEMENT> ExpressionForCondition<T, DRIVER_DATA_SOURCE, DRIVER_STATEMENT>.lessEq(
    other: ExpressionForCondition<T, DRIVER_DATA_SOURCE, DRIVER_STATEMENT>
): AliasableExpressionForCondition<Boolean, DRIVER_DATA_SOURCE, DRIVER_STATEMENT> {
    return condition(other, "<=")
}

context (ConditionContext, DatabaseDialect<DRIVER_DATA_SOURCE, DRIVER_STATEMENT>)
fun <T, DRIVER_DATA_SOURCE, DRIVER_STATEMENT> ExpressionForCondition<T, DRIVER_DATA_SOURCE, DRIVER_STATEMENT>.lessEq(
    other: Parameter<T, DRIVER_DATA_SOURCE, DRIVER_STATEMENT>
): AliasableExpressionForCondition<Boolean, DRIVER_DATA_SOURCE, DRIVER_STATEMENT> {
    return condition(other, "<=")
}

context (ConditionContext, DatabaseDialect<DRIVER_DATA_SOURCE, DRIVER_STATEMENT>)
fun <T, DRIVER_DATA_SOURCE, DRIVER_STATEMENT> ExpressionForCondition<T, DRIVER_DATA_SOURCE, DRIVER_STATEMENT>.lessEq(
    other: T
): AliasableExpressionForCondition<Boolean, DRIVER_DATA_SOURCE, DRIVER_STATEMENT> {
    return lessEq(databaseType().parameterFactory().invoke(other))
}

context (ConditionContext, DatabaseDialect<DRIVER_DATA_SOURCE, DRIVER_STATEMENT>)
fun <DRIVER_DATA_SOURCE, DRIVER_STATEMENT> ExpressionForCondition<String?, DRIVER_DATA_SOURCE, DRIVER_STATEMENT>.like(
    other: Parameter<String?, DRIVER_DATA_SOURCE, DRIVER_STATEMENT>
): AliasableExpressionForCondition<Boolean, DRIVER_DATA_SOURCE, DRIVER_STATEMENT> {
    return condition(other, "like")
}

context (ConditionContext, DatabaseDialect<DRIVER_DATA_SOURCE, DRIVER_STATEMENT>)
fun <DRIVER_DATA_SOURCE, DRIVER_STATEMENT> ExpressionForCondition<String?, DRIVER_DATA_SOURCE, DRIVER_STATEMENT>.like(
    other: String
): AliasableExpressionForCondition<Boolean, DRIVER_DATA_SOURCE, DRIVER_STATEMENT> {
    val parameter: Parameter<String?, DRIVER_DATA_SOURCE, DRIVER_STATEMENT> =
        databaseType().parameterFactory().invoke(other)
    return like(parameter)
}

context (ConditionContext, DatabaseDialect<DRIVER_DATA_SOURCE, DRIVER_STATEMENT>)
fun <T, DRIVER_DATA_SOURCE, DRIVER_STATEMENT> ExpressionForCondition<T, DRIVER_DATA_SOURCE, DRIVER_STATEMENT>.inListParameters(
    list: Iterable<Parameter<T, DRIVER_DATA_SOURCE, DRIVER_STATEMENT>>
): AliasableExpressionForCondition<Boolean, DRIVER_DATA_SOURCE, DRIVER_STATEMENT> {
    return condition(list, "in")
}

context (ConditionContext, DatabaseDialect<DRIVER_DATA_SOURCE, DRIVER_STATEMENT>)
fun <T, DRIVER_DATA_SOURCE, DRIVER_STATEMENT> ExpressionForCondition<T, DRIVER_DATA_SOURCE, DRIVER_STATEMENT>.inList(
    list: Iterable<T>
): AliasableExpressionForCondition<Boolean, DRIVER_DATA_SOURCE, DRIVER_STATEMENT> {
    return inListParameters(list.map { databaseType().parameterFactory().invoke(it) })
}

context (ConditionContext, DatabaseDialect<DRIVER_DATA_SOURCE, DRIVER_STATEMENT>)
fun <DRIVER_DATA_SOURCE, DRIVER_STATEMENT> ExpressionForCondition<Boolean, DRIVER_DATA_SOURCE, DRIVER_STATEMENT>.and(
    other: ExpressionForCondition<Boolean, DRIVER_DATA_SOURCE, DRIVER_STATEMENT>
): AliasableExpressionForCondition<Boolean, DRIVER_DATA_SOURCE, DRIVER_STATEMENT> {
    return object : AliasableExpressionForCondition<Boolean, DRIVER_DATA_SOURCE, DRIVER_STATEMENT> {
        override fun databaseType(): DatabaseType<Boolean, DRIVER_DATA_SOURCE, DRIVER_STATEMENT> {
            return booleanType()
        }

        override fun build(): QueryPart<DRIVER_DATA_SOURCE, DRIVER_STATEMENT> {
            val first = this@and.build()
            val second = other.build()
            return QueryPartImpl(
                sqlDefinition = "(${first.sqlDefinition}) AND (${second.sqlDefinition})",
                parameters = first.parameters + second.parameters
            )
        }
    }
}

context (ConditionContext, DatabaseDialect<DRIVER_DATA_SOURCE, DRIVER_STATEMENT>)
fun <DRIVER_DATA_SOURCE, DRIVER_STATEMENT> ExpressionForCondition<Boolean, DRIVER_DATA_SOURCE, DRIVER_STATEMENT>.or(
    other: ExpressionForCondition<Boolean, DRIVER_DATA_SOURCE, DRIVER_STATEMENT>
): AliasableExpressionForCondition<Boolean, DRIVER_DATA_SOURCE, DRIVER_STATEMENT> {
    return object : AliasableExpressionForCondition<Boolean, DRIVER_DATA_SOURCE, DRIVER_STATEMENT> {
        override fun databaseType(): DatabaseType<Boolean, DRIVER_DATA_SOURCE, DRIVER_STATEMENT> {
            return booleanType()
        }

        override fun build(): QueryPart<DRIVER_DATA_SOURCE, DRIVER_STATEMENT> {
            val first = this@or.build()
            val second = other.build()
            return QueryPartImpl(
                sqlDefinition = "(${first.sqlDefinition}) OR (${second.sqlDefinition})",
                parameters = first.parameters + second.parameters
            )
        }
    }
}

context (ConditionContext, DatabaseDialect<DRIVER_DATA_SOURCE, DRIVER_STATEMENT>)
private fun <T, DRIVER_DATA_SOURCE, DRIVER_STATEMENT> ExpressionForCondition<T, DRIVER_DATA_SOURCE, DRIVER_STATEMENT>.condition(
    other: ExpressionForCondition<T, DRIVER_DATA_SOURCE, DRIVER_STATEMENT>,
    operator: String
): AliasableExpressionForCondition<Boolean, DRIVER_DATA_SOURCE, DRIVER_STATEMENT> {
    return object : AliasableExpressionForCondition<Boolean, DRIVER_DATA_SOURCE, DRIVER_STATEMENT> {
        override fun databaseType(): DatabaseType<Boolean, DRIVER_DATA_SOURCE, DRIVER_STATEMENT> {
            return booleanType()
        }

        override fun build(): QueryPart<DRIVER_DATA_SOURCE, DRIVER_STATEMENT> {
            val leftExpression = this@condition.build()
            val rightExpression = other.build()
            return QueryPartImpl(
                sqlDefinition = "${leftExpression.sqlDefinition} $operator ${rightExpression.sqlDefinition}",
                parameters = leftExpression.parameters + rightExpression.parameters
            )
        }
    }
}

context (ConditionContext, DatabaseDialect<DRIVER_DATA_SOURCE, DRIVER_STATEMENT>)
private fun <T, DRIVER_DATA_SOURCE, DRIVER_STATEMENT> ExpressionForCondition<T, DRIVER_DATA_SOURCE, DRIVER_STATEMENT>.condition(
    other: Parameter<T, DRIVER_DATA_SOURCE, DRIVER_STATEMENT>,
    operator: String
): AliasableExpressionForCondition<Boolean, DRIVER_DATA_SOURCE, DRIVER_STATEMENT> {
    return object : AliasableExpressionForCondition<Boolean, DRIVER_DATA_SOURCE, DRIVER_STATEMENT> {
        override fun databaseType(): DatabaseType<Boolean, DRIVER_DATA_SOURCE, DRIVER_STATEMENT> {
            return booleanType()
        }

        override fun build(): QueryPart<DRIVER_DATA_SOURCE, DRIVER_STATEMENT> {
            val leftExpression = this@condition.build()
            return QueryPartImpl(
                sqlDefinition = "${leftExpression.sqlDefinition} $operator ${other.parameterInSql}",
                parameters = leftExpression.parameters + other
            )
        }
    }
}

context (ConditionContext, DatabaseDialect<DRIVER_DATA_SOURCE, DRIVER_STATEMENT>)
private fun <T, DRIVER_DATA_SOURCE, DRIVER_STATEMENT> ExpressionForCondition<T, DRIVER_DATA_SOURCE, DRIVER_STATEMENT>.condition(
    others: Iterable<Parameter<T, DRIVER_DATA_SOURCE, DRIVER_STATEMENT>>,
    operator: String
): AliasableExpressionForCondition<Boolean, DRIVER_DATA_SOURCE, DRIVER_STATEMENT> {
    return object : AliasableExpressionForCondition<Boolean, DRIVER_DATA_SOURCE, DRIVER_STATEMENT> {
        override fun databaseType(): DatabaseType<Boolean, DRIVER_DATA_SOURCE, DRIVER_STATEMENT> {
            return booleanType()
        }

        override fun build(): QueryPart<DRIVER_DATA_SOURCE, DRIVER_STATEMENT> {
            val leftExpression = this@condition.build()
            val parameters = others.joinToString(",") {
                it.parameterInSql
            }
            return QueryPartImpl(
                sqlDefinition = "${leftExpression.sqlDefinition} $operator ($parameters)",
                parameters = leftExpression.parameters + others
            )
        }
    }
}
