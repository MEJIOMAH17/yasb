package com.github.mejiomah17.yasb.core

import com.github.mejiomah17.yasb.core.expression.Expression

class Row(private val columnToValues: Map<Expression<*, *>, Any?>) {
    operator fun <T> get(column: Expression<T, *>): T {
        return columnToValues[column] as T
    }

    override fun toString(): String {
        return columnToValues.entries.joinToString("    ") { "${it.key}:${it.value}" }
    }
}
