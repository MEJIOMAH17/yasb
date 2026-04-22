package com.github.mejiomah17.yaksb.postgres.jdbc.generator.column

import com.github.mejiomah17.yaksb.dsl.generator.ColumnMetadata
import com.github.mejiomah17.yaksb.dsl.generator.toCamelCase

data class Jsonb(
    private val name: String,
    private val nullable: Boolean,
) : ColumnMetadata {
    override fun columnDefinition(): String =
        if (!nullable) {
            "val ${name.toCamelCase()} = jsonb(\"$name\")"
        } else {
            "val ${name.toCamelCase()} = jsonbNullable(\"$name\")"
        }
}
