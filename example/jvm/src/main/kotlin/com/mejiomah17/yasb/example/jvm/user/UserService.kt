package com.mejiomah17.yasb.example.jvm.user

import com.github.mejiomah17.yasb.core.jdbc.transaction.JdbcTransactionAtLeastReadCommitted
import com.github.mejiomah17.yasb.core.jdbc.transaction.JdbcTransactionAtLeastRepeatableRead
import com.github.mejiomah17.yasb.postgres.jdbc.PostgresJdbcDatabaseDialect
import com.mejiomah17.yasb.example.jvm.pet.PetDao
import java.util.*

class UserService(
    private val userDao: UserDao,
    private val petDao: PetDao
) {
    context(JdbcTransactionAtLeastRepeatableRead, PostgresJdbcDatabaseDialect)
    fun register(username: String, password: String): RegisterResult {
        if (userDao.exist(username)) {
            return RegisterResult.UserAlreadyExist
        }
        val user = UserRecord(
            id = UUID.randomUUID(),
            username = username,
            password = hash(password)
        )
        userDao.create(user)
        return RegisterResult.Registered(User(user, emptyList()))
    }

    context(JdbcTransactionAtLeastRepeatableRead, PostgresJdbcDatabaseDialect)
    fun update(id: UUID, username: String, password: String): UpdateResult {
        if (!userDao.exist(id)) {
            return UpdateResult.UserDoesNotExist
        }
        val user = UserRecord(
            id = id,
            username = username,
            password = hash(password)
        )
        userDao.update(user)
        return UpdateResult.Updated
    }

    context(JdbcTransactionAtLeastReadCommitted, PostgresJdbcDatabaseDialect)
    fun find(id: UUID): User? {
        val user = userDao.findById(id) ?: return null
        val pets = petDao.findByOwner(user.id)
        return User(
            id = user.id,
            username = user.username,
            password = user.password,
            pets = pets
        )
    }

    /**
     * The project is just example. No real crypto here.
     */
    private fun hash(password: String): String {
        return "plain text unencrypted $password"
    }

    sealed interface RegisterResult {
        data object UserAlreadyExist : RegisterResult
        data class Registered(val user: User) : RegisterResult
    }

    sealed interface UpdateResult {
        data object UserDoesNotExist : UpdateResult
        data object Updated : UpdateResult
    }
}
