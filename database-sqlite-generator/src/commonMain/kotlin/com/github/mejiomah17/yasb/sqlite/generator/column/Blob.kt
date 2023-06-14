package com.github.mejiomah17.yasb.sqlite.generator.column

import com.github.mejiomah17.yasb.dsl.generator.ColumnMetadata
import com.github.mejiomah17.yasb.dsl.generator.toCamelCase

class Blob(private val name: String, private val nullable: Boolean) : ColumnMetadata {

    override fun columnDefinition(): String {
        return if (!nullable) {
            "val ${name.toCamelCase()} = blob(\"$name\")"
        } else {
            "val ${name.toCamelCase()} = blobNullable(\"$name\")"
        }
    }
}
