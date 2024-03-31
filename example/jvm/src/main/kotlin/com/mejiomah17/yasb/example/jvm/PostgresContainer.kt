package com.mejiomah17.yasb.example.jvm

import org.testcontainers.containers.PostgreSQLContainer
import org.testcontainers.utility.DockerImageName

class PostgresContainer(imageName: DockerImageName) : PostgreSQLContainer<PostgresContainer>(imageName) {
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
