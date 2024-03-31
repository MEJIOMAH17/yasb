package com.mejiomah17.yasb.example.jvm.pet

import com.mejiomah17.yasb.example.jvm.IntegrationTest
import com.mejiomah17.yasb.example.jvm.user.User
import io.kotest.matchers.shouldBe
import io.ktor.client.request.accept
import io.ktor.client.request.get
import io.ktor.client.request.parameter
import io.ktor.client.request.put
import io.ktor.client.statement.bodyAsText
import io.ktor.http.ContentType
import io.ktor.http.HttpStatusCode
import io.ktor.http.path
import kotlinx.coroutines.runBlocking
import kotlinx.serialization.json.Json
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class PetTest : IntegrationTest() {
    lateinit var user: User

    @BeforeEach
    fun init(): Unit = runBlocking {
        val username = randomString()
        val response = client.put {
            url {
                path("user")
                parameter("username", username)
                parameter("password", "security")
            }
            accept(ContentType.Application.Json)
        }.bodyAsText()
        user = Json.decodeFromString<User>(response)
    }

    @Test
    fun register_pet(): Unit = runBlocking {
        val petName = randomString()
        val registerResponse = client.put {
            url {
                path("pet")
                parameter("name", petName)
                parameter("owner", this@PetTest.user.id)
            }
            accept(ContentType.Application.Json)
        }

        registerResponse.status.shouldBe(HttpStatusCode.Created)
        val pet = Json.decodeFromString<Pet>(registerResponse.bodyAsText())
        pet.owner.shouldBe(user.id)
        pet.name shouldBe petName
    }

    @Test
    fun finds_pet_by_owner_username(): Unit = runBlocking {
        val petName = randomString()
        client.put {
            url {
                path("pet")
                parameter("name", petName)
                parameter("owner", this@PetTest.user.id)
            }
            accept(ContentType.Application.Json)
        }

        val response = client.get {
            url {
                path("pet")
                parameter("owner_username", this@PetTest.user.username)
            }
            accept(ContentType.Application.Json)
        }

        response.status.shouldBe(HttpStatusCode.OK)
        val pet = Json.decodeFromString<List<Pet>>(response.bodyAsText()).single()
        pet.owner.shouldBe(user.id)
        pet.name shouldBe petName
    }
}
