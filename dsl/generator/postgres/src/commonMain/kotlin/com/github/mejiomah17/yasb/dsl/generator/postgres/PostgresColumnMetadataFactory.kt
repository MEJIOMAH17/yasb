package com.github.mejiomah17.yasb.dsl.generator.postgres

import com.github.mejiomah17.yasb.dsl.generator.ColumnMetadata
import com.github.mejiomah17.yasb.dsl.generator.ColumnMetadataFactory
import com.github.mejiomah17.yasb.dsl.generator.postgres.column.BigInt
import com.github.mejiomah17.yasb.dsl.generator.postgres.column.Bool
import com.github.mejiomah17.yasb.dsl.generator.postgres.column.DoublePrecision
import com.github.mejiomah17.yasb.dsl.generator.postgres.column.Jsonb
import com.github.mejiomah17.yasb.dsl.generator.postgres.column.Text
import com.github.mejiomah17.yasb.dsl.generator.postgres.column.Timestamp
import com.github.mejiomah17.yasb.dsl.generator.postgres.column.Uuid

class PostgresColumnMetadataFactory : ColumnMetadataFactory {
    override fun create(name: String, type: String, nullable: Boolean): ColumnMetadata {
        return when (type) {
            // TODO split varchar and text
            "text", "varchar" -> Text(name, nullable)
            "uuid" -> Uuid(name, nullable)
            "timestamp" -> Timestamp(name, nullable)
            "bool" -> Bool(name, nullable)
            "float8" -> DoublePrecision(name, nullable)
            "bigserial" -> BigInt(name, nullable)
            "int8" -> BigInt(name, nullable)
            "jsonb" -> Jsonb(name, nullable)
            else -> error("type $type is not supported yet")
        }
    }
}
