package com.github.mejiomah17.yasb.dsl.generator

import com.github.mejiomah17.yasb.dsl.generator.column.Bool
import com.github.mejiomah17.yasb.dsl.generator.column.Text
import com.github.mejiomah17.yasb.dsl.generator.column.Timestamp
import com.github.mejiomah17.yasb.dsl.generator.column.Uuid

class PostgresColumnMetadataFactory : ColumnMetadataFactory {
    override fun create(name: String, type: String, nullable: Boolean): ColumnMetadata {
        return when (type) {
            //TODO split varchar and text
            "text", "varchar" -> Text(name, nullable)
            "uuid" -> Uuid(name, nullable)
            "timestamp" -> Timestamp(name, nullable)
            "bool" -> Bool(name, nullable)
            else -> error("type $type is not supported yet")
        }
    }
}