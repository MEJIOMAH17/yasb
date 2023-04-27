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

context (ConditionContext, DatabaseDialect<S>)
fun <T, S> ExpressionForCondition<T, S>.eq(other: ExpressionForCondition<T, S>): AliasableExpressionForCondition<Boolean, S> {
    return condition(other, "=")
}

context (ConditionContext, DatabaseDialect<S>)
fun <T, S> ExpressionForCondition<T, S>.eq(other: Parameter<T, S>): AliasableExpressionForCondition<Boolean, S> {
    return condition(other, "=")
}

context (ConditionContext, DatabaseDialect<S>)
fun <T, S> ExpressionForCondition<T, S>.eq(other: T): AliasableExpressionForCondition<Boolean, S> {
    return eq(databaseType().parameterFactory().invoke(other))
}

context (ConditionContext, DatabaseDialect<S>)
fun <T, S> ExpressionForCondition<T, S>.greater(other: ExpressionForCondition<T, S>): AliasableExpressionForCondition<Boolean, S> {
    return condition(other, ">")
}

context (ConditionContext, DatabaseDialect<S>)
fun <T, S> ExpressionForCondition<T, S>.greater(other: Parameter<T, S>): AliasableExpressionForCondition<Boolean, S> {
    return condition(other, ">")
}

context (ConditionContext, DatabaseDialect<S>)
fun <T, S> ExpressionForCondition<T, S>.greater(other: T): AliasableExpressionForCondition<Boolean, S> {
    return greater(databaseType().parameterFactory().invoke(other))
}

context (ConditionContext, DatabaseDialect<S>)
fun <T, S> ExpressionForCondition<T, S>.greaterEq(other: ExpressionForCondition<T, S>): AliasableExpressionForCondition<Boolean, S> {
    return condition(other, ">=")
}

context (ConditionContext, DatabaseDialect<S>)
fun <T, S> ExpressionForCondition<T, S>.greaterEq(other: Parameter<T, S>): AliasableExpressionForCondition<Boolean, S> {
    return condition(other, ">=")
}

context (ConditionContext, DatabaseDialect<S>)
fun <T, S> ExpressionForCondition<T, S>.greaterEq(other: T): AliasableExpressionForCondition<Boolean, S> {
    return greaterEq(databaseType().parameterFactory().invoke(other))
}

context (ConditionContext, DatabaseDialect<S>)
fun <T, S> ExpressionForCondition<T, S>.less(other: ExpressionForCondition<T, S>): AliasableExpressionForCondition<Boolean, S> {
    return condition(other, "<")
}

context (ConditionContext, DatabaseDialect<S>)
fun <T, S> ExpressionForCondition<T, S>.less(other: Parameter<T, S>): AliasableExpressionForCondition<Boolean, S> {
    return condition(other, "<")
}

context (ConditionContext, DatabaseDialect<S>)
fun <T, S> ExpressionForCondition<T, S>.less(other: T): AliasableExpressionForCondition<Boolean, S> {
    return less(databaseType().parameterFactory().invoke(other))
}

context (ConditionContext, DatabaseDialect<S>)
fun <T, S> ExpressionForCondition<T, S>.lessEq(other: ExpressionForCondition<T, S>): AliasableExpressionForCondition<Boolean, S> {
    return condition(other, "<=")
}

context (ConditionContext, DatabaseDialect<S>)
fun <T, S> ExpressionForCondition<T, S>.lessEq(other: Parameter<T, S>): AliasableExpressionForCondition<Boolean, S> {
    return condition(other, "<=")
}

context (ConditionContext, DatabaseDialect<S>)
fun <T, S> ExpressionForCondition<T, S>.lessEq(other: T): AliasableExpressionForCondition<Boolean, S> {
    return lessEq(databaseType().parameterFactory().invoke(other))
}

context (ConditionContext, DatabaseDialect<S>)
fun <S> ExpressionForCondition<String?, S>.like(other: Parameter<String?, S>): AliasableExpressionForCondition<Boolean, S> {
    return condition(other, "like")
}

context (ConditionContext, DatabaseDialect<S>)
fun <S> ExpressionForCondition<String?, S>.like(other: String): AliasableExpressionForCondition<Boolean, S> {
    val parameter: Parameter<String?, S> = databaseType().parameterFactory().invoke(other)
    return like(parameter)
}

context (ConditionContext, DatabaseDialect<S>)
fun <T, S> ExpressionForCondition<T, S>.inListParameters(list: Iterable<Parameter<T, S>>): AliasableExpressionForCondition<Boolean, S> {
    return condition(list, "in")
}

context (ConditionContext, DatabaseDialect<S>)
fun <T, S> ExpressionForCondition<T, S>.inList(list: Iterable<T>): AliasableExpressionForCondition<Boolean, S> {
    return inListParameters(list.map { databaseType().parameterFactory().invoke(it) })
}

context (ConditionContext, DatabaseDialect<S>)
fun <S> ExpressionForCondition<Boolean, S>.and(other: ExpressionForCondition<Boolean, S>): AliasableExpressionForCondition<Boolean, S> {
    return object : AliasableExpressionForCondition<Boolean, S> {
        override fun databaseType(): DatabaseType<Boolean, S> {
            return booleanType()
        }

        override fun build(): QueryPart<S> {
            val first = this@and.build()
            val second = other.build()
            return QueryPartImpl(
                sqlDefinition = "(${first.sqlDefinition}) AND (${second.sqlDefinition})",
                parameters = first.parameters + second.parameters
            )
        }
    }
}

context (ConditionContext, DatabaseDialect<S>)
fun <S> ExpressionForCondition<Boolean, S>.or(other: ExpressionForCondition<Boolean, S>): AliasableExpressionForCondition<Boolean, S> {
    return object : AliasableExpressionForCondition<Boolean, S> {
        override fun databaseType(): DatabaseType<Boolean, S> {
            return booleanType()
        }

        override fun build(): QueryPart<S> {
            val first = this@or.build()
            val second = other.build()
            return QueryPartImpl(
                sqlDefinition = "(${first.sqlDefinition}) OR (${second.sqlDefinition})",
                parameters = first.parameters + second.parameters
            )
        }
    }
}

context (ConditionContext, DatabaseDialect<S>)
private fun <T, S> ExpressionForCondition<T, S>.condition(
    other: ExpressionForCondition<T, S>,
    operator: String
): AliasableExpressionForCondition<Boolean, S> {
    return object : AliasableExpressionForCondition<Boolean, S> {
        override fun databaseType(): DatabaseType<Boolean, S> {
            return booleanType()
        }

        override fun build(): QueryPart<S> {
            val leftExpression = this@condition.build()
            val rightExpression = other.build()
            return QueryPartImpl(
                sqlDefinition = "${leftExpression.sqlDefinition} $operator ${rightExpression.sqlDefinition}",
                parameters = leftExpression.parameters + rightExpression.parameters
            )
        }
    }
}

context (ConditionContext, DatabaseDialect<S>)
private fun <T, S> ExpressionForCondition<T, S>.condition(
    other: Parameter<T, S>,
    operator: String
): AliasableExpressionForCondition<Boolean, S> {
    return object : AliasableExpressionForCondition<Boolean, S> {
        override fun databaseType(): DatabaseType<Boolean, S> {
            return booleanType()
        }

        override fun build(): QueryPart<S> {
            val leftExpression = this@condition.build()
            return QueryPartImpl(
                sqlDefinition = "${leftExpression.sqlDefinition} $operator ${other.parameterInSql}",
                parameters = leftExpression.parameters + other
            )
        }
    }
}

context (ConditionContext, DatabaseDialect<S>)
private fun <T, S> ExpressionForCondition<T, S>.condition(
    others: Iterable<Parameter<T, S>>,
    operator: String
): AliasableExpressionForCondition<Boolean, S> {
    return object : AliasableExpressionForCondition<Boolean, S> {
        override fun databaseType(): DatabaseType<Boolean, S> {
            return booleanType()
        }

        override fun build(): QueryPart<S> {
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
