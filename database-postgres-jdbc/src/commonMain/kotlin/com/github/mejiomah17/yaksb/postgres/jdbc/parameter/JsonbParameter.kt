package com.github.mejiomah17.yaksb.postgres.jdbc.parameter

import com.github.mejiomah17.yaksb.core.jdbc.JDBCDatabaseType
import com.github.mejiomah17.yaksb.postgres.jdbc.type.JsonbDatabaseType
import org.postgresql.util.PGobject
import java.sql.PreparedStatement

class JsonbParameter(
    override val value: String?,
) : PostgresParameter<String>() {
    override val databaseType: JDBCDatabaseType<String> = JsonbDatabaseType

    override fun applyToStatement(
        statement: PreparedStatement,
        index: Int,
    ) {
        statement.setObject(
            index,
            PGobject().apply {
                type = "jsonb"
                value = this@JsonbParameter.value
            },
        )
    }
}
