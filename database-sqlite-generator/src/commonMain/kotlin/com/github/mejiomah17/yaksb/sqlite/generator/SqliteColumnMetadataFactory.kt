package com.github.mejiomah17.yaksb.sqlite.generator

import com.github.mejiomah17.yaksb.dsl.generator.ColumnMetadata
import com.github.mejiomah17.yaksb.dsl.generator.ColumnMetadataFactory
import com.github.mejiomah17.yaksb.sqlite.generator.column.BigInt
import com.github.mejiomah17.yaksb.sqlite.generator.column.Blob
import com.github.mejiomah17.yaksb.sqlite.generator.column.Bool
import com.github.mejiomah17.yaksb.sqlite.generator.column.Text

class SqliteColumnMetadataFactory : ColumnMetadataFactory {
    override fun create(
        name: String,
        type: String,
        nullable: Boolean,
    ): ColumnMetadata =
        when (type.lowercase()) {
            "text" -> Text(name, nullable)
            "character varying" -> Text(name, nullable)
            "bool" -> Bool(name, nullable)
            "boolean" -> Bool(name, nullable)
            "bigint" -> BigInt(name, nullable)
            "integer" -> BigInt(name, nullable)
            "blob" -> Blob(name, nullable)
            else -> error("type $type is not supported yet")
        }
}
