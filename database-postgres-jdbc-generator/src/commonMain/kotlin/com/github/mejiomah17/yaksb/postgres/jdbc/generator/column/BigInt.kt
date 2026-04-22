package com.github.mejiomah17.yaksb.postgres.jdbc.generator.column

import com.github.mejiomah17.yaksb.dsl.generator.ColumnMetadata
import com.github.mejiomah17.yaksb.dsl.generator.toCamelCase

class BigInt(
    private val name: String,
    private val nullable: Boolean,
) : ColumnMetadata {
    override fun columnDefinition(): String =
        if (!nullable) {
            "val ${name.toCamelCase()} = bigint(\"$name\")"
        } else {
            "val ${name.toCamelCase()} = bigintNullable(\"$name\")"
        }
}
