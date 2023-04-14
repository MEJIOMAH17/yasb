package com.github.mejiomah17.yasb.postgres.jdbc.generator.column

import com.github.mejiomah17.yasb.dsl.generator.ColumnMetadata
import com.github.mejiomah17.yasb.dsl.generator.toCamelCase

data class Jsonb(private val name: String, private val nullable: Boolean) : ColumnMetadata {
    override fun columnDefinition(): String {
        return if (!nullable) {
            "val ${name.toCamelCase()} = jsonb(\"$name\")"
        } else {
            "val ${name.toCamelCase()} = jsonbNullable(\"$name\")"
        }
    }
}
