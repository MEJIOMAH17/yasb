package com.github.mejiomah17.yasb.dsl.generator

import java.sql.Connection

open class PostgresTableMetadataFactory(
    private val columnMetadataFactory: ColumnMetadataFactory
) : TableMetadataFactory {
    override fun create(connection: Connection, tableName: String, schemaPattern: String?): TableMetadata {
        val columns = ResultSetIterator(connection.metaData.getColumns(null, schemaPattern, tableName, null))
            .asSequence()
            .map {
                columnMetadataFactory.create(
                    name = it.getString(4),
                    type = it.getString(6),
                    nullable = it.getBoolean(18)
                )
            }.toList()
        return TableMetadata(
            tableName,
            columns
        )
    }
}
