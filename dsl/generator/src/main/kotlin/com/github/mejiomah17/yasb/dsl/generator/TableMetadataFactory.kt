package com.github.mejiomah17.yasb.dsl.generator

import java.io.Serializable
import java.sql.Connection

interface TableMetadataFactory : Serializable {
    fun create(connection: Connection, tableName: String, schemaPattern: String?): TableMetadata
}
