package com.mejiomah17.yasb.example.jvm.user

import com.mejiomah17.yasb.example.jvm.IntegrationTest
import io.kotest.matchers.nulls.shouldNotBeNull
import io.kotest.matchers.shouldBe
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.http.*
import kotlinx.coroutines.runBlocking
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonPrimitive
import kotlinx.serialization.json.jsonObject
import kotlinx.serialization.json.jsonPrimitive
import org.junit.jupiter.api.Test
import java.net.URI
import java.net.http.HttpClient
import java.net.http.HttpRequest
import java.net.http.HttpResponse

class UserTest : IntegrationTest() {
    @Test
    fun register_user(): Unit = runBlocking {
        val username = randomString()

        val registerResponse = client.put {
            url {
                path("user")
                parameter("username", username)
                parameter("password", "security")
            }
            accept(ContentType.Application.Json)
        }

        registerResponse.status.shouldBe(HttpStatusCode.Created)
        val registerBody = Json.parseToJsonElement(registerResponse.bodyAsText()).jsonObject
        registerBody["username"]!!.jsonPrimitive.content.shouldBe(username)
        registerBody["id"].shouldNotBeNull()
    }

    @Test
    fun finds_user(): Unit = runBlocking {
        val username = randomString()
        val registerResponse = client.put {
            url {
                path("user")
                parameter("username", username)
                parameter("password", "security")
            }
            accept(ContentType.Application.Json)
        }
        val id = Json.parseToJsonElement(registerResponse.bodyAsText()).jsonObject["id"]!!.jsonPrimitive.content
        val getResponse  = client.get {
            url {
                path("user")
                parameter("id", id)
            }
            accept(ContentType.Application.Json)
        }

        getResponse.status.shouldBe(HttpStatusCode.OK)
        val body = Json.parseToJsonElement(getResponse.bodyAsText()).jsonObject
        body["username"]!!.jsonPrimitive.content.shouldBe(username)
        body["id"]!!.jsonPrimitive.content.shouldBe(id)
    }

    @Test
    fun updates_user(): Unit = runBlocking {
        val username = randomString()
        val registerResponse = client.put {
            url {
                path("user")
                parameter("username", username)
                parameter("password", "security")
            }
            accept(ContentType.Application.Json)
        }
        val id = Json.parseToJsonElement(registerResponse.bodyAsText()).jsonObject["id"]!!.jsonPrimitive.content
        val newUsername = randomString()
        val updateResponse = client.post{
            url {
                path("user")
                parameter("id", id)
                parameter("username", newUsername)
                parameter("password", "security")
            }
        }
        updateResponse.status.shouldBe(HttpStatusCode.OK)


        val getResponse  = client.get {
            url {
                path("user")
                parameter("id", id)
            }
            accept(ContentType.Application.Json)
        }

        val body = Json.parseToJsonElement(getResponse.bodyAsText()).jsonObject
        body["username"]!!.jsonPrimitive.content.shouldBe(newUsername)
        body["id"]!!.jsonPrimitive.content.shouldBe(id)
    }
}