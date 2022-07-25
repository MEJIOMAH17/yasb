package com.github.mejiomah17.yasb.dsl

import com.github.mejiomah17.yasb.core.DatabaseDialect
import com.github.mejiomah17.yasb.core.DatabaseType
import com.github.mejiomah17.yasb.core.expression.AliasableExpressionForCondition
import com.github.mejiomah17.yasb.core.expression.ExpressionForCondition
import com.github.mejiomah17.yasb.core.parameter.Parameter
import com.github.mejiomah17.yasb.core.query.QueryPart
import com.github.mejiomah17.yasb.core.query.QueryPartImpl

object ConditionContext

context (ConditionContext, DatabaseDialect)
fun <T> ExpressionForCondition<T>.eq(other: ExpressionForCondition<T>): AliasableExpressionForCondition<Boolean> {
    return condition(other, "=")
}

context (ConditionContext, DatabaseDialect)
fun <T> ExpressionForCondition<T>.eq(other: Parameter<T>): AliasableExpressionForCondition<Boolean> {
    return condition(other, "=")
}

context (ConditionContext, DatabaseDialect)
fun <T> ExpressionForCondition<T>.eq(other: T): AliasableExpressionForCondition<Boolean> {
    return eq(databaseType().parameterFactory().invoke(other))
}

context (ConditionContext, DatabaseDialect)
fun <T> ExpressionForCondition<T>.greater(other: ExpressionForCondition<T>): AliasableExpressionForCondition<Boolean> {
    return condition(other, ">")
}

context (ConditionContext, DatabaseDialect)
fun <T> ExpressionForCondition<T>.greater(other: Parameter<T>): AliasableExpressionForCondition<Boolean> {
    return condition(other, ">")
}

context (ConditionContext, DatabaseDialect)
fun <T> ExpressionForCondition<T>.greater(other: T): AliasableExpressionForCondition<Boolean> {
    return greater(databaseType().parameterFactory().invoke(other))
}

context (ConditionContext, DatabaseDialect)
fun <T> ExpressionForCondition<T>.greaterEq(other: ExpressionForCondition<T>): AliasableExpressionForCondition<Boolean> {
    return condition(other, ">=")
}

context (ConditionContext, DatabaseDialect)
fun <T> ExpressionForCondition<T>.greaterEq(other: Parameter<T>): AliasableExpressionForCondition<Boolean> {
    return condition(other, ">=")
}

context (ConditionContext, DatabaseDialect)
fun <T> ExpressionForCondition<T>.greaterEq(other: T): AliasableExpressionForCondition<Boolean> {
    return greaterEq(databaseType().parameterFactory().invoke(other))
}

context (ConditionContext, DatabaseDialect)
fun <T> ExpressionForCondition<T>.less(other: ExpressionForCondition<T>): AliasableExpressionForCondition<Boolean> {
    return condition(other, "<")
}

context (ConditionContext, DatabaseDialect)
fun <T> ExpressionForCondition<T>.less(other: Parameter<T>): AliasableExpressionForCondition<Boolean> {
    return condition(other, "<")
}

context (ConditionContext, DatabaseDialect)
fun <T> ExpressionForCondition<T>.less(other: T): AliasableExpressionForCondition<Boolean> {
    return less(databaseType().parameterFactory().invoke(other))
}

context (ConditionContext, DatabaseDialect)
fun <T> ExpressionForCondition<T>.lessEq(other: ExpressionForCondition<T>): AliasableExpressionForCondition<Boolean> {
    return condition(other, "<=")
}

context (ConditionContext, DatabaseDialect)
fun <T> ExpressionForCondition<T>.lessEq(other: Parameter<T>): AliasableExpressionForCondition<Boolean> {
    return condition(other, "<=")
}

context (ConditionContext, DatabaseDialect)
fun <T> ExpressionForCondition<T>.lessEq(other: T): AliasableExpressionForCondition<Boolean> {
    return lessEq(databaseType().parameterFactory().invoke(other))
}

context (ConditionContext, DatabaseDialect)
fun ExpressionForCondition<String?>.like(other: Parameter<String?>): AliasableExpressionForCondition<Boolean> {
    return condition(other, "like")
}

context (ConditionContext, DatabaseDialect)
fun ExpressionForCondition<String?>.like(other: String): AliasableExpressionForCondition<Boolean> {
    val parameter: Parameter<String?> = databaseType().parameterFactory().invoke(other)
    return like(parameter)
}

context (ConditionContext, DatabaseDialect)
fun <T> ExpressionForCondition<T>.inListParameters(list: Iterable<Parameter<T>>): AliasableExpressionForCondition<Boolean> {
    return condition(list, "in")
}

context (ConditionContext, DatabaseDialect)
fun <T> ExpressionForCondition<T>.inList(list: Iterable<T>): AliasableExpressionForCondition<Boolean> {
    return inListParameters(list.map { databaseType().parameterFactory().invoke(it) })
}

context (ConditionContext, DatabaseDialect)
fun ExpressionForCondition<Boolean>.and(other: ExpressionForCondition<Boolean>): AliasableExpressionForCondition<Boolean> {
    return object : AliasableExpressionForCondition<Boolean> {
        override fun databaseType(): DatabaseType<Boolean> {
            return booleanType()
        }

        override fun build(): QueryPart {
            val first = this@and.build()
            val second = other.build()
            return QueryPartImpl(
                sqlDefinition = "(${first.sqlDefinition}) AND (${second.sqlDefinition})",
                parameters = first.parameters + second.parameters
            )
        }
    }
}

context (ConditionContext, DatabaseDialect)
fun ExpressionForCondition<Boolean>.or(other: ExpressionForCondition<Boolean>): AliasableExpressionForCondition<Boolean> {
    return object : AliasableExpressionForCondition<Boolean> {
        override fun databaseType(): DatabaseType<Boolean> {
            return booleanType()
        }

        override fun build(): QueryPart {
            val first = this@or.build()
            val second = other.build()
            return QueryPartImpl(
                sqlDefinition = "(${first.sqlDefinition}) OR (${second.sqlDefinition})",
                parameters = first.parameters + second.parameters
            )
        }
    }
}

context (ConditionContext, DatabaseDialect) private fun <T> ExpressionForCondition<T>.condition(
    other: ExpressionForCondition<T>,
    operator: String
): AliasableExpressionForCondition<Boolean> {
    return object : AliasableExpressionForCondition<Boolean> {
        override fun databaseType(): DatabaseType<Boolean> {
            return booleanType()
        }

        override fun build(): QueryPart {
            val leftExpression = this@condition.build()
            val rightExpression = other.build()
            return QueryPartImpl(
                sqlDefinition = "${leftExpression.sqlDefinition} $operator ${rightExpression.sqlDefinition}",
                parameters = leftExpression.parameters + rightExpression.parameters
            )
        }
    }
}

context (ConditionContext, DatabaseDialect) private fun <T> ExpressionForCondition<T>.condition(
    other: Parameter<T>,
    operator: String
): AliasableExpressionForCondition<Boolean> {
    return object : AliasableExpressionForCondition<Boolean> {
        override fun databaseType(): DatabaseType<Boolean> {
            return booleanType()
        }

        override fun build(): QueryPart {
            val leftExpression = this@condition.build()
            return QueryPartImpl(
                sqlDefinition = "${leftExpression.sqlDefinition} $operator ${other.parameterInSql}",
                parameters = leftExpression.parameters + other
            )
        }
    }
}

context (ConditionContext, DatabaseDialect) private fun <T> ExpressionForCondition<T>.condition(
    others: Iterable<Parameter<T>>,
    operator: String
): AliasableExpressionForCondition<Boolean> {
    return object : AliasableExpressionForCondition<Boolean> {
        override fun databaseType(): DatabaseType<Boolean> {
            return booleanType()
        }

        override fun build(): QueryPart {
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



