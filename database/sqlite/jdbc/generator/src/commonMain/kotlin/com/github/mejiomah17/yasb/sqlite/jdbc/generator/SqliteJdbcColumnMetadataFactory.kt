package com.github.mejiomah17.yasb.sqlite.jdbc.generator

import com.github.mejiomah17.yasb.dsl.generator.ColumnMetadata
import com.github.mejiomah17.yasb.dsl.generator.ColumnMetadataFactory
import com.github.mejiomah17.yasb.sqlite.jdbc.generator.column.BigInt
import com.github.mejiomah17.yasb.sqlite.jdbc.generator.column.Bool
import com.github.mejiomah17.yasb.sqlite.jdbc.generator.column.Text

class SqliteJdbcColumnMetadataFactory : ColumnMetadataFactory {
    override fun create(name: String, type: String, nullable: Boolean): ColumnMetadata {
        return when (type.lowercase()) {
            "text" -> Text(name, nullable)
            "bool" -> Bool(name, nullable)
            "bigint" -> BigInt(name, nullable)
            else -> error("type $type is not supported yet")
        }
    }
}
