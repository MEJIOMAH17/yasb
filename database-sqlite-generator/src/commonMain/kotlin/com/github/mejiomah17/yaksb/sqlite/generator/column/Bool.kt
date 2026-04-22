package com.github.mejiomah17.yaksb.sqlite.generator.column

import com.github.mejiomah17.yaksb.dsl.generator.ColumnMetadata
import com.github.mejiomah17.yaksb.dsl.generator.toCamelCase

data class Bool(
    private val name: String,
    private val nullable: Boolean,
) : ColumnMetadata {
    override fun columnDefinition(): String =
        if (!nullable) {
            "val ${name.toCamelCase()} = bool(\"$name\")"
        } else {
            "val ${name.toCamelCase()} = boolNullable(\"$name\")"
        }
}
