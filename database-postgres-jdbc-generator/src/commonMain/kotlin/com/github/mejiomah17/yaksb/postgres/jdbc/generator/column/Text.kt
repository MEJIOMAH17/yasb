package com.github.mejiomah17.yaksb.postgres.jdbc.generator.column

import com.github.mejiomah17.yaksb.dsl.generator.ColumnMetadata
import com.github.mejiomah17.yaksb.dsl.generator.toCamelCase

data class Text(private val name: String, private val nullable: Boolean) : ColumnMetadata {
    override fun columnDefinition(): String {
        return if (!nullable) {
            "val ${name.toCamelCase()} = text(\"$name\")"
        } else {
            "val ${name.toCamelCase()} = textNullable(\"$name\")"
        }
    }
}
