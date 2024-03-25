package com.mejiomah17.yasb.example.jvm

import io.ktor.client.*
import io.ktor.client.engine.*
import io.ktor.client.engine.cio.*
import io.ktor.client.plugins.*
import io.ktor.client.request.*
import io.ktor.http.*
import kotlinx.coroutines.runBlocking
import org.awaitility.Awaitility.await
import org.junit.jupiter.api.BeforeAll
import java.util.UUID
import java.util.concurrent.TimeUnit

open class IntegrationTest {
    companion object {
        val client = HttpClient(CIO) {
             defaultRequest {
                host = "localhost"
                port = 8080
            }
        }

        init {
            val server = server()
            Runtime.getRuntime().addShutdownHook(object : Thread() {
                override fun run() {
                    server.stop(0, 0)
                }
            })
            server.start(wait = false)
        }

        @BeforeAll
        @JvmStatic
        fun init() = runBlocking {
            await().atMost(120, TimeUnit.SECONDS).coUntil { client.get("healthcheck").status == HttpStatusCode.OK }
        }
        fun randomString():String = UUID.randomUUID().toString()
    }
}

