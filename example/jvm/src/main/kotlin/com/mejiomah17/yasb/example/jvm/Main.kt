package com.mejiomah17.yasb.example.jvm

import com.github.mejiomah17.yakl.api.Logger
import com.github.mejiomah17.yakl.dsl.logging
import com.github.mejiomah17.yakl.stdout.stdout
import com.github.mejiomah17.yasb.postgres.jdbc.PostgresJdbcTransactionFactory
import com.mejiomah17.yasb.example.jvm.pet.PetController
import com.mejiomah17.yasb.example.jvm.pet.PetDao
import com.mejiomah17.yasb.example.jvm.pet.PetService
import com.mejiomah17.yasb.example.jvm.user.UserController
import com.mejiomah17.yasb.example.jvm.user.UserDao
import com.mejiomah17.yasb.example.jvm.user.UserService
import com.zaxxer.hikari.HikariConfig
import com.zaxxer.hikari.HikariDataSource
import io.ktor.http.HttpStatusCode
import io.ktor.server.application.call
import io.ktor.server.engine.embeddedServer
import io.ktor.server.netty.Netty
import io.ktor.server.netty.NettyApplicationEngine
import io.ktor.server.response.respond
import io.ktor.server.routing.get
import io.ktor.server.routing.route
import io.ktor.server.routing.routing
import org.flywaydb.core.Flyway
import org.postgresql.Driver
import org.testcontainers.utility.DockerImageName
import java.sql.DriverManager

fun main() {
    server().start(true)
}

fun server(): NettyApplicationEngine {
    val logFather = logging {
        stdout()
    }
    val logger = logFather.createLogger("main")
    val transactionFactory = PostgresJdbcTransactionFactory(datasource(logger))
    val userDao = UserDao()
    val petDao = PetDao()
    val userService = UserService(userDao, petDao)
    val userController = UserController(transactionFactory, userService)
    val petService = PetService(petDao)
    val petController = PetController(transactionFactory, petService)
    return embeddedServer(Netty, port = 8080) {
        routing {
            get("healthcheck") {
                call.respond(HttpStatusCode.OK)
            }
            route("user") {
                userController.register(this)
            }
            route("pet") {
                petController.register(this)
            }
        }
    }
}

private fun datasource(logger: Logger): HikariDataSource {
    val databaseContainer = PostgresContainer(DockerImageName.parse("postgres").withTag("16.1"))
        .withDatabaseName("yasb")
        .withUsername(PostgresContainer.LOGIN)
        .withPassword(PostgresContainer.PASSWORD)
    databaseContainer.start()
    logger.info { "database started" }
    DriverManager.registerDriver(Driver())
    val datasource = HikariDataSource(
        HikariConfig().also {
            it.jdbcUrl = databaseContainer.jdbcUrl
            it.username = PostgresContainer.LOGIN
            it.password = PostgresContainer.PASSWORD
        }
    )
    Flyway.configure()
        .dataSource(datasource)
        .load()
        .migrate()
    logger.info { "database migration applied" }
    return datasource
}
