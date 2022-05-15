package com.github.mejiomah17.yasb.dsl.generator

import java.sql.Connection

interface TableMetadataFactory {
    fun create(connection: Connection, tableName: String,schemaPattern: String?): TableMetadata
}