package com.github.mejiomah17.yasb.core.parameter

import com.github.mejiomah17.yasb.core.jdbc.JDBCDatabaseType
import com.github.mejiomah17.yasb.postgres.jdbc.parameter.PostgresParameter
import com.github.mejiomah17.yasb.postgres.jdbc.type.UuidDatabaseType
import org.postgresql.util.PGobject
import java.sql.PreparedStatement
import java.util.UUID

class UuidParameter(
    override val value: UUID?
) : PostgresParameter<UUID>() {
    override val databaseType: JDBCDatabaseType<UUID> = UuidDatabaseType

    override fun applyToStatement(
        statement: PreparedStatement,
        index: Int
    ) {
        statement.setObject(
            index,
            PGobject().apply {
                type = "uuid"
                value = this@UuidParameter.value.toString()
            }
        )
    }
}
