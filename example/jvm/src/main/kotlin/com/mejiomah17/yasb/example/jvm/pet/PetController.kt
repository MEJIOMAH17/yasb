package com.mejiomah17.yasb.example.jvm.pet

import com.github.mejiomah17.yasb.postgres.jdbc.PostgresJdbcTransactionFactory
import io.ktor.http.HttpStatusCode
import io.ktor.server.application.call
import io.ktor.server.response.respond
import io.ktor.server.routing.Route
import io.ktor.server.routing.get
import io.ktor.server.routing.put
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import java.util.UUID

class PetController(
    private val transactionFactory: PostgresJdbcTransactionFactory,
    private val petService: PetService
) {
    fun register(route: Route) = route {
        put {
            val queryParams = call.request.queryParameters
            val owner = queryParams["owner"]
            val name = queryParams["name"]
            if (owner == null || name == null) {
                call.respond(HttpStatusCode.BadRequest)
                return@put
            }
            val pet = Pet(
                id = UUID.randomUUID(),
                owner = UUID.fromString(owner),
                name = name
            )
            transactionFactory.repeatableRead {
                petService.create(
                    pet
                )
            }
            call.respond(HttpStatusCode.Created, pet.toJson())
        }
        get {
            val queryParams = call.request.queryParameters
            val ownerUsername = queryParams["owner_username"]
            if (ownerUsername == null) {
                call.respond(HttpStatusCode.BadRequest)
                return@get
            }
            val result = transactionFactory.readCommitted {
                petService.findByOwnerUsername(ownerUsername)
            }
            call.respond(HttpStatusCode.OK, result.toJson())
        }
    }

    private fun Pet.toJson(): String {
        return Json.encodeToString(this)
    }

    private fun List<Pet>.toJson(): String {
        return Json.encodeToString(this)
    }
}
