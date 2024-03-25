package com.mejiomah17.yasb.example.jvm.user

import com.github.mejiomah17.yasb.core.transaction.TransactionFactory
import com.github.mejiomah17.yasb.postgres.jdbc.PostgresJdbcTransactionFactory
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import kotlinx.serialization.json.JsonObject
import kotlinx.serialization.json.JsonPrimitive
import kotlinx.serialization.json.buildJsonObject
import java.util.UUID

class UserController(
    private val transactionFactory: PostgresJdbcTransactionFactory,
    private val userService: UserService
) {
    fun register(route: Route) = route {
        put {
            val queryParams = call.request.queryParameters
            val username = queryParams["username"]
            val password = queryParams["password"]
            if (username == null || password == null) {
                call.respond(HttpStatusCode.BadRequest)
                return@put
            }
            val result = transactionFactory.repeatableRead {
                userService.register(username, password)
            }
            when (result) {
                is UserService.RegisterResult.Registered -> call.respond(HttpStatusCode.Created, result.user.toJson())
                is UserService.RegisterResult.UserAlreadyExist -> call.respond(HttpStatusCode.Conflict)
            }
        }
        post {
            val queryParams = call.request.queryParameters
            val id = runCatching { UUID.fromString(queryParams["id"]) }.getOrNull()
            val username = queryParams["username"]
            val password = queryParams["password"]
            if (username == null || password == null || id == null) {
                call.respond(HttpStatusCode.BadRequest)
                return@post
            }

            val result = transactionFactory.repeatableRead {
                userService.update(id, username, password)
            }
            when (result) {
                is UserService.UpdateResult.Updated -> call.respond(HttpStatusCode.OK)
                is UserService.UpdateResult.UserDoesNotExist -> call.respond(HttpStatusCode.NotFound)
            }
        }
        get {
            val queryParams = call.request.queryParameters
            val id = runCatching { UUID.fromString(queryParams["id"]) }.getOrNull()
            if (id == null) {
                call.respond(HttpStatusCode.BadRequest)
                return@get
            }
            val result = transactionFactory.readCommitted {
                userService.find(id)
            }
            if (result == null) {
                call.respond(HttpStatusCode.NotFound)
            } else {
                call.respond(HttpStatusCode.OK, result.toJson())
            }
        }
    }

    private fun User.toJson(): String {
        return buildJsonObject {
            put("id", JsonPrimitive(id.toString()))
            put("username", JsonPrimitive(username))
        }.toString()
    }
}