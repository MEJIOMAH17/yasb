package com.github.mejiomah17.yasb.postgres.jdbc.generator

import com.github.mejiomah17.yasb.dsl.generator.ColumnMetadata
import com.github.mejiomah17.yasb.dsl.generator.ColumnMetadataFactory
import com.github.mejiomah17.yasb.postgres.jdbc.generator.column.BigInt
import com.github.mejiomah17.yasb.postgres.jdbc.generator.column.Bool
import com.github.mejiomah17.yasb.postgres.jdbc.generator.column.DoublePrecision
import com.github.mejiomah17.yasb.postgres.jdbc.generator.column.Jsonb
import com.github.mejiomah17.yasb.postgres.jdbc.generator.column.Text
import com.github.mejiomah17.yasb.postgres.jdbc.generator.column.Timestamp
import com.github.mejiomah17.yasb.postgres.jdbc.generator.column.Uuid
import com.github.mejiomah17.yasb.postgres.jdbc.generator.column.Varchar

class PostgresColumnMetadataFactory : ColumnMetadataFactory {
    override fun create(name: String, type: String, nullable: Boolean): ColumnMetadata {
        return when (type) {
            "text" -> Text(name, nullable)
            "varchar" -> Varchar(name, nullable)
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
