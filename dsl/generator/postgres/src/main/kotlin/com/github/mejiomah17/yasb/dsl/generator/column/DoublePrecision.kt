package com.github.mejiomah17.yasb.dsl.generator.column

import com.github.mejiomah17.yasb.dsl.generator.ColumnMetadata
import com.github.mejiomah17.yasb.dsl.generator.toCamelCase

class DoublePrecision(private val name: String, private val nullable: Boolean) : ColumnMetadata {

    override fun columnDefinition(): String {
        return if (!nullable) {
            "val ${name.toCamelCase()} = doublePrecision(\"$name\")"
        } else {
            "val ${name.toCamelCase()} = doublePrecisionNullable(\"$name\")"
        }
    }
}