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

context (ConditionContext, DatabaseDialect<DRIVER_DATA_SOURCE>)
fun <T, DRIVER_DATA_SOURCE> ExpressionForCondition<T, DRIVER_DATA_SOURCE>.eq(other: ExpressionForCondition<T, DRIVER_DATA_SOURCE>): AliasableExpressionForCondition<Boolean, DRIVER_DATA_SOURCE> {
    return condition(other, "=")
}

context (ConditionContext, DatabaseDialect<DRIVER_DATA_SOURCE>)
fun <T, DRIVER_DATA_SOURCE> ExpressionForCondition<T, DRIVER_DATA_SOURCE>.eq(other: Parameter<T, DRIVER_DATA_SOURCE>): AliasableExpressionForCondition<Boolean, DRIVER_DATA_SOURCE> {
    return condition(other, "=")
}

context (ConditionContext, DatabaseDialect<DRIVER_DATA_SOURCE>)
fun <T, DRIVER_DATA_SOURCE> ExpressionForCondition<T, DRIVER_DATA_SOURCE>.eq(other: T): AliasableExpressionForCondition<Boolean, DRIVER_DATA_SOURCE> {
    return eq(databaseType().parameterFactory().invoke(other))
}

context (ConditionContext, DatabaseDialect<DRIVER_DATA_SOURCE>)
fun <T, DRIVER_DATA_SOURCE> ExpressionForCondition<T, DRIVER_DATA_SOURCE>.greater(other: ExpressionForCondition<T, DRIVER_DATA_SOURCE>): AliasableExpressionForCondition<Boolean, DRIVER_DATA_SOURCE> {
    return condition(other, ">")
}

context (ConditionContext, DatabaseDialect<DRIVER_DATA_SOURCE>)
fun <T, DRIVER_DATA_SOURCE> ExpressionForCondition<T, DRIVER_DATA_SOURCE>.greater(other: Parameter<T, DRIVER_DATA_SOURCE>): AliasableExpressionForCondition<Boolean, DRIVER_DATA_SOURCE> {
    return condition(other, ">")
}

context (ConditionContext, DatabaseDialect<DRIVER_DATA_SOURCE>)
fun <T, DRIVER_DATA_SOURCE> ExpressionForCondition<T, DRIVER_DATA_SOURCE>.greater(other: T): AliasableExpressionForCondition<Boolean, DRIVER_DATA_SOURCE> {
    return greater(databaseType().parameterFactory().invoke(other))
}

context (ConditionContext, DatabaseDialect<DRIVER_DATA_SOURCE>)
fun <T, DRIVER_DATA_SOURCE> ExpressionForCondition<T, DRIVER_DATA_SOURCE>.greaterEq(other: ExpressionForCondition<T, DRIVER_DATA_SOURCE>): AliasableExpressionForCondition<Boolean, DRIVER_DATA_SOURCE> {
    return condition(other, ">=")
}

context (ConditionContext, DatabaseDialect<DRIVER_DATA_SOURCE>)
fun <T, DRIVER_DATA_SOURCE> ExpressionForCondition<T, DRIVER_DATA_SOURCE>.greaterEq(other: Parameter<T, DRIVER_DATA_SOURCE>): AliasableExpressionForCondition<Boolean, DRIVER_DATA_SOURCE> {
    return condition(other, ">=")
}

context (ConditionContext, DatabaseDialect<DRIVER_DATA_SOURCE>)
fun <T, DRIVER_DATA_SOURCE> ExpressionForCondition<T, DRIVER_DATA_SOURCE>.greaterEq(other: T): AliasableExpressionForCondition<Boolean, DRIVER_DATA_SOURCE> {
    return greaterEq(databaseType().parameterFactory().invoke(other))
}

context (ConditionContext, DatabaseDialect<DRIVER_DATA_SOURCE>)
fun <T, DRIVER_DATA_SOURCE> ExpressionForCondition<T, DRIVER_DATA_SOURCE>.less(other: ExpressionForCondition<T, DRIVER_DATA_SOURCE>): AliasableExpressionForCondition<Boolean, DRIVER_DATA_SOURCE> {
    return condition(other, "<")
}

context (ConditionContext, DatabaseDialect<DRIVER_DATA_SOURCE>)
fun <T, DRIVER_DATA_SOURCE> ExpressionForCondition<T, DRIVER_DATA_SOURCE>.less(other: Parameter<T, DRIVER_DATA_SOURCE>): AliasableExpressionForCondition<Boolean, DRIVER_DATA_SOURCE> {
    return condition(other, "<")
}

context (ConditionContext, DatabaseDialect<DRIVER_DATA_SOURCE>)
fun <T, DRIVER_DATA_SOURCE> ExpressionForCondition<T, DRIVER_DATA_SOURCE>.less(other: T): AliasableExpressionForCondition<Boolean, DRIVER_DATA_SOURCE> {
    return less(databaseType().parameterFactory().invoke(other))
}

context (ConditionContext, DatabaseDialect<DRIVER_DATA_SOURCE>)
fun <T, DRIVER_DATA_SOURCE> ExpressionForCondition<T, DRIVER_DATA_SOURCE>.lessEq(other: ExpressionForCondition<T, DRIVER_DATA_SOURCE>): AliasableExpressionForCondition<Boolean, DRIVER_DATA_SOURCE> {
    return condition(other, "<=")
}

context (ConditionContext, DatabaseDialect<DRIVER_DATA_SOURCE>)
fun <T, DRIVER_DATA_SOURCE> ExpressionForCondition<T, DRIVER_DATA_SOURCE>.lessEq(other: Parameter<T, DRIVER_DATA_SOURCE>): AliasableExpressionForCondition<Boolean, DRIVER_DATA_SOURCE> {
    return condition(other, "<=")
}

context (ConditionContext, DatabaseDialect<DRIVER_DATA_SOURCE>)
fun <T, DRIVER_DATA_SOURCE> ExpressionForCondition<T, DRIVER_DATA_SOURCE>.lessEq(other: T): AliasableExpressionForCondition<Boolean, DRIVER_DATA_SOURCE> {
    return lessEq(databaseType().parameterFactory().invoke(other))
}

context (ConditionContext, DatabaseDialect<DRIVER_DATA_SOURCE>)
fun <DRIVER_DATA_SOURCE> ExpressionForCondition<String?, DRIVER_DATA_SOURCE>.like(other: Parameter<String?, DRIVER_DATA_SOURCE>): AliasableExpressionForCondition<Boolean, DRIVER_DATA_SOURCE> {
    return condition(other, "like")
}

context (ConditionContext, DatabaseDialect<DRIVER_DATA_SOURCE>)
fun <DRIVER_DATA_SOURCE> ExpressionForCondition<String?, DRIVER_DATA_SOURCE>.like(other: String): AliasableExpressionForCondition<Boolean, DRIVER_DATA_SOURCE> {
    val parameter: Parameter<String?, DRIVER_DATA_SOURCE> = databaseType().parameterFactory().invoke(other)
    return like(parameter)
}

context (ConditionContext, DatabaseDialect<DRIVER_DATA_SOURCE>)
fun <T, DRIVER_DATA_SOURCE> ExpressionForCondition<T, DRIVER_DATA_SOURCE>.inListParameters(list: Iterable<Parameter<T, DRIVER_DATA_SOURCE>>): AliasableExpressionForCondition<Boolean, DRIVER_DATA_SOURCE> {
    return condition(list, "in")
}

context (ConditionContext, DatabaseDialect<DRIVER_DATA_SOURCE>)
fun <T, DRIVER_DATA_SOURCE> ExpressionForCondition<T, DRIVER_DATA_SOURCE>.inList(list: Iterable<T>): AliasableExpressionForCondition<Boolean, DRIVER_DATA_SOURCE> {
    return inListParameters(list.map { databaseType().parameterFactory().invoke(it) })
}

context (ConditionContext, DatabaseDialect<DRIVER_DATA_SOURCE>)
fun <DRIVER_DATA_SOURCE> ExpressionForCondition<Boolean, DRIVER_DATA_SOURCE>.and(other: ExpressionForCondition<Boolean, DRIVER_DATA_SOURCE>): AliasableExpressionForCondition<Boolean, DRIVER_DATA_SOURCE> {
    return object : AliasableExpressionForCondition<Boolean, DRIVER_DATA_SOURCE> {
        override fun databaseType(): DatabaseType<Boolean, DRIVER_DATA_SOURCE> {
            return booleanType()
        }

        override fun build(): QueryPart<DRIVER_DATA_SOURCE> {
            val first = this@and.build()
            val second = other.build()
            return QueryPartImpl(
                sqlDefinition = "(${first.sqlDefinition}) AND (${second.sqlDefinition})",
                parameters = first.parameters + second.parameters
            )
        }
    }
}

context (ConditionContext, DatabaseDialect<DRIVER_DATA_SOURCE>)
fun <DRIVER_DATA_SOURCE> ExpressionForCondition<Boolean, DRIVER_DATA_SOURCE>.or(other: ExpressionForCondition<Boolean, DRIVER_DATA_SOURCE>): AliasableExpressionForCondition<Boolean, DRIVER_DATA_SOURCE> {
    return object : AliasableExpressionForCondition<Boolean, DRIVER_DATA_SOURCE> {
        override fun databaseType(): DatabaseType<Boolean, DRIVER_DATA_SOURCE> {
            return booleanType()
        }

        override fun build(): QueryPart<DRIVER_DATA_SOURCE> {
            val first = this@or.build()
            val second = other.build()
            return QueryPartImpl(
                sqlDefinition = "(${first.sqlDefinition}) OR (${second.sqlDefinition})",
                parameters = first.parameters + second.parameters
            )
        }
    }
}

context (ConditionContext, DatabaseDialect<DRIVER_DATA_SOURCE>)
private fun <T, DRIVER_DATA_SOURCE> ExpressionForCondition<T, DRIVER_DATA_SOURCE>.condition(
    other: ExpressionForCondition<T, DRIVER_DATA_SOURCE>,
    operator: String
): AliasableExpressionForCondition<Boolean, DRIVER_DATA_SOURCE> {
    return object : AliasableExpressionForCondition<Boolean, DRIVER_DATA_SOURCE> {
        override fun databaseType(): DatabaseType<Boolean, DRIVER_DATA_SOURCE> {
            return booleanType()
        }

        override fun build(): QueryPart<DRIVER_DATA_SOURCE> {
            val leftExpression = this@condition.build()
            val rightExpression = other.build()
            return QueryPartImpl(
                sqlDefinition = "${leftExpression.sqlDefinition} $operator ${rightExpression.sqlDefinition}",
                parameters = leftExpression.parameters + rightExpression.parameters
            )
        }
    }
}

context (ConditionContext, DatabaseDialect<DRIVER_DATA_SOURCE>)
private fun <T, DRIVER_DATA_SOURCE> ExpressionForCondition<T, DRIVER_DATA_SOURCE>.condition(
    other: Parameter<T, DRIVER_DATA_SOURCE>,
    operator: String
): AliasableExpressionForCondition<Boolean, DRIVER_DATA_SOURCE> {
    return object : AliasableExpressionForCondition<Boolean, DRIVER_DATA_SOURCE> {
        override fun databaseType(): DatabaseType<Boolean, DRIVER_DATA_SOURCE> {
            return booleanType()
        }

        override fun build(): QueryPart<DRIVER_DATA_SOURCE> {
            val leftExpression = this@condition.build()
            return QueryPartImpl(
                sqlDefinition = "${leftExpression.sqlDefinition} $operator ${other.parameterInSql}",
                parameters = leftExpression.parameters + other
            )
        }
    }
}

context (ConditionContext, DatabaseDialect<DRIVER_DATA_SOURCE>)
private fun <T, DRIVER_DATA_SOURCE> ExpressionForCondition<T, DRIVER_DATA_SOURCE>.condition(
    others: Iterable<Parameter<T, DRIVER_DATA_SOURCE>>,
    operator: String
): AliasableExpressionForCondition<Boolean, DRIVER_DATA_SOURCE> {
    return object : AliasableExpressionForCondition<Boolean, DRIVER_DATA_SOURCE> {
        override fun databaseType(): DatabaseType<Boolean, DRIVER_DATA_SOURCE> {
            return booleanType()
        }

        override fun build(): QueryPart<DRIVER_DATA_SOURCE> {
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
