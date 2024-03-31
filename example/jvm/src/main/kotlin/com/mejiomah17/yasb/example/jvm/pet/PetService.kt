package com.mejiomah17.yasb.example.jvm.pet

import com.github.mejiomah17.yasb.core.jdbc.transaction.JdbcTransactionAtLeastReadCommitted
import com.github.mejiomah17.yasb.core.jdbc.transaction.JdbcTransactionAtLeastRepeatableRead
import com.github.mejiomah17.yasb.postgres.jdbc.PostgresJdbcDatabaseDialect

class PetService(
    private val petDao: PetDao
) {
    context(JdbcTransactionAtLeastRepeatableRead, PostgresJdbcDatabaseDialect)
    fun create(pet: Pet) {
        petDao.create(pet)
    }

    context(JdbcTransactionAtLeastReadCommitted, PostgresJdbcDatabaseDialect)
    fun findByOwnerUsername(username: String): List<Pet> {
        return petDao.findByOwnerUsername(username)
    }
}
