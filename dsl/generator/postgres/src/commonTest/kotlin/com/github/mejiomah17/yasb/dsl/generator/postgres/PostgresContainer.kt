package com.github.mejiomah17.yasb.dsl.generator.postgres

import org.testcontainers.containers.PostgreSQLContainer
import org.testcontainers.utility.DockerImageName

class PostgresContainer : PostgreSQLContainer<PostgresContainer>(DockerImageName.parse("postgres").withTag("13.2")) {
    init {
        this.withDatabaseName("test")
            .withUsername(LOGIN)
            .withPassword(PASSWORD)
    }

    companion object {
        val LOGIN = "postgres"
        val PASSWORD = "test"
    }
}
