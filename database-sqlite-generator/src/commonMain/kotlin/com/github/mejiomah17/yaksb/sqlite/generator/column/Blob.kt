package com.github.mejiomah17.yaksb.sqlite.generator.column

import com.github.mejiomah17.yaksb.dsl.generator.ColumnMetadata
import com.github.mejiomah17.yaksb.dsl.generator.toCamelCase

class Blob(
    private val name: String,
    private val nullable: Boolean,
) : ColumnMetadata {
    override fun columnDefinition(): String =
        if (!nullable) {
            "val ${name.toCamelCase()} = blob(\"$name\")"
        } else {
            "val ${name.toCamelCase()} = blobNullable(\"$name\")"
        }
}
