package com.mejiomah17.yasb.example.jvm.user

import com.github.mejiomah17.yasb.UsersTable
import com.github.mejiomah17.yasb.core.Row
import com.github.mejiomah17.yasb.core.dsl.eq
import com.github.mejiomah17.yasb.core.dsl.from
import com.github.mejiomah17.yasb.core.dsl.insertInto
import com.github.mejiomah17.yasb.core.dsl.select
import com.github.mejiomah17.yasb.core.dsl.update
import com.github.mejiomah17.yasb.core.jdbc.transaction.JdbcTransaction
import com.github.mejiomah17.yasb.core.where
import com.github.mejiomah17.yasb.postgres.jdbc.PostgresJdbcDatabaseDialect
import java.util.UUID

class UserDao {
    context(JdbcTransaction, PostgresJdbcDatabaseDialect)
    fun create(user: UserRecord) {
        insertInto(UsersTable) {
            it[id] = user.id
            it[username] = user.username
            it[password] = user.password
        }.execute()
    }

    context(JdbcTransaction, PostgresJdbcDatabaseDialect)
    fun update(user: UserRecord) {
        update(
            UsersTable,
            set = {
                it[username] = user.username
                it[password] = user.password
            },
            where = {
                UsersTable.id.eq(user.id)
            }
        ).execute()
    }

    context(JdbcTransaction, PostgresJdbcDatabaseDialect)
    fun findById(id: UUID): UserRecord? {
        return select(UsersTable.allColumns())
            .from(UsersTable)
            .where { UsersTable.id.eq(id) }
            .execute()
            .singleOrNull()
            ?.toUser()
    }

    context(JdbcTransaction, PostgresJdbcDatabaseDialect)
    fun exist(username: String): Boolean {
        return select(UsersTable.id)
            .from(UsersTable)
            .where { UsersTable.username.eq(username) }
            .execute()
            .isNotEmpty()
    }

    context(JdbcTransaction, PostgresJdbcDatabaseDialect)
    fun exist(id: UUID): Boolean {
        return select(UsersTable.id)
            .from(UsersTable)
            .where { UsersTable.id.eq(id) }
            .execute()
            .isNotEmpty()
    }

    fun Row.toUser(): UserRecord {
        return UserRecord(
            id = this[UsersTable.id],
            username = this[UsersTable.username],
            password = this[UsersTable.password]
        )
    }
}
