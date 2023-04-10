package com.github.mejiomah17.yasb.dsl.generator.sqlite

import com.github.mejiomah17.yasb.core.sqlite.ddl.SqliteTable
import com.github.mejiomah17.yasb.dsl.generator.ColumnMetadataFactory
import com.github.mejiomah17.yasb.dsl.generator.ResultSetIterator
import com.github.mejiomah17.yasb.dsl.generator.TableMetadata
import com.github.mejiomah17.yasb.dsl.generator.TableMetadataFactory
import java.sql.Connection

open class SqliteTableMetadataFactory(
    private val columnMetadataFactory: ColumnMetadataFactory
) : TableMetadataFactory {
    override fun create(connection: Connection, tableName: String, schemaPattern: String?): TableMetadata {
        val columns = ResultSetIterator(connection.metaData.getColumns(null, schemaPattern, tableName, null))
            .asSequence()
            .map {
                columnMetadataFactory.create(
                    name = it.getString(4),
                    type = it.getString(6),
                    nullable = when (it.getString(18).lowercase()) {
                        "yes", "true", "1" -> true
                        "no", "false", "0" -> false
                        else -> error("unsupported value for boolean metadata ${it.getString(18)}")
                    }
                )
            }.toList()
        return TableMetadata(
            tableName,
            SqliteTable::class,
            columns
        )
    }
}
