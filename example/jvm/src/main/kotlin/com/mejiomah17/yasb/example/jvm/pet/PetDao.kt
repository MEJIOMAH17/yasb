package com.mejiomah17.yasb.example.jvm.pet

import com.github.mejiomah17.yasb.PetsTable
import com.github.mejiomah17.yasb.UsersTable
import com.github.mejiomah17.yasb.core.Row
import com.github.mejiomah17.yasb.core.dsl.eq
import com.github.mejiomah17.yasb.core.dsl.from
import com.github.mejiomah17.yasb.core.dsl.insertInto
import com.github.mejiomah17.yasb.core.dsl.join.innerJoin
import com.github.mejiomah17.yasb.core.dsl.select
import com.github.mejiomah17.yasb.core.jdbc.transaction.JdbcTransaction
import com.github.mejiomah17.yasb.core.where
import com.github.mejiomah17.yasb.postgres.jdbc.PostgresJdbcDatabaseDialect
import java.util.UUID

class PetDao {
    context(JdbcTransaction, PostgresJdbcDatabaseDialect)
    fun create(pet: Pet) {
        insertInto(PetsTable) {
            it[id] = pet.id
            it[owner] = pet.owner
            it[name] = pet.name
        }.execute()
    }

    context(JdbcTransaction, PostgresJdbcDatabaseDialect)
    fun findByOwnerUsername(username: String): List<Pet> {
        return select(PetsTable.allColumns())
            .from(PetsTable)
            .innerJoin(UsersTable, on = {
                PetsTable.owner.eq(UsersTable.id)
            })
            .where {
                UsersTable.username.eq(username)
            }.execute()
            .map {
                it.toPet()
            }
    }

    context(JdbcTransaction, PostgresJdbcDatabaseDialect)
    fun findByOwner(owner: UUID): List<Pet> {
        return select(PetsTable.allColumns())
            .from(PetsTable)
            .where {
                PetsTable.owner.eq(owner)
            }.execute()
            .map {
                it.toPet()
            }
    }

    private fun Row.toPet() = Pet(
        id = this[PetsTable.id],
        owner = this[PetsTable.owner],
        name = this[PetsTable.name]
    )
}
