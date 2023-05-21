package com.github.mejiomah17.yasb.sqlite.generator.column

import com.github.mejiomah17.yasb.dsl.generator.ColumnMetadata
import com.github.mejiomah17.yasb.dsl.generator.toCamelCase

data class BigInt(private val name: String, private val nullable: Boolean) : ColumnMetadata {

    override fun columnDefinition(): String {
        return if (!nullable) {
            "val ${name.toCamelCase()} = bigint(\"$name\")"
        } else {
            "val ${name.toCamelCase()} = bigintNullable(\"$name\")"
        }
    }
}
