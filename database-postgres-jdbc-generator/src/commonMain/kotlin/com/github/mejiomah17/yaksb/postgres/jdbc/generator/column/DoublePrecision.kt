package com.github.mejiomah17.yaksb.postgres.jdbc.generator.column

import com.github.mejiomah17.yaksb.dsl.generator.ColumnMetadata
import com.github.mejiomah17.yaksb.dsl.generator.toCamelCase

class DoublePrecision(
    private val name: String,
    private val nullable: Boolean,
) : ColumnMetadata {
    override fun columnDefinition(): String =
        if (!nullable) {
            "val ${name.toCamelCase()} = doublePrecision(\"$name\")"
        } else {
            "val ${name.toCamelCase()} = doublePrecisionNullable(\"$name\")"
        }
}
