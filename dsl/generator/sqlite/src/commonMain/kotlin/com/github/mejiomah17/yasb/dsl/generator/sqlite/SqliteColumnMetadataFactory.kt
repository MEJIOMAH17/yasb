package com.github.mejiomah17.yasb.dsl.generator.sqlite

import com.github.mejiomah17.yasb.dsl.generator.ColumnMetadata
import com.github.mejiomah17.yasb.dsl.generator.ColumnMetadataFactory
import com.github.mejiomah17.yasb.dsl.generator.sqlite.column.BigInt
import com.github.mejiomah17.yasb.dsl.generator.sqlite.column.Bool
import com.github.mejiomah17.yasb.dsl.generator.sqlite.column.Text

class SqliteColumnMetadataFactory : ColumnMetadataFactory {
    override fun create(name: String, type: String, nullable: Boolean): ColumnMetadata {
        return when (type.lowercase()) {
            "text" -> Text(name, nullable)
            "bool" -> Bool(name, nullable)
            "bigint" -> BigInt(name, nullable)
            else -> error("type $type is not supported yet")
        }
    }
}
