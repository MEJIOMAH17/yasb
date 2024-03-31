# Yet Another SQL Builder

[![Maven Central](https://maven-badges.herokuapp.com/maven-central/io.github.mejiomah17.yasb/core/badge.svg)](https://maven-badges.herokuapp.com/maven-central/io.github.mejiomah17.yasb/core)
[![GitHub License](https://img.shields.io/badge/license-Apache%20License%202.0-blue.svg?style=flat)](https://www.apache.org/licenses/LICENSE-2.0)
[![Telegram](https://raw.githubusercontent.com/Patrolavia/telegram-badge/master/ask.svg)](https://t.me/MEJIOMAH17)

**YASB** is a Kotlin-friendly DSL for building SQL queries, providing an intuitive and expressive way to interact with databases. It offers a range of features tailored for Kotlin developers, enabling seamless integration with various databases and explicit control over transactions and queries.

### Key Features

1. Kotlin-friendly DSL
   1. <details>
       <summary>Insert</summary>

       ```kotlin
       insertInto(UsersTable) {
           it[id] = user.id
           it[username] = user.username
           it[password] = user.password
       }.execute()
       ```
       </details>
   2. <details>
       <summary>Update</summary>

       ```kotlin
       update(
           UsersTable,
           set = {
               it[username] = user.username
               it[password] = user.password
           },
           where = {
               UsersTable.id.eq(user.id)
           }
       )
       .execute()
       ```
       </details>
   3. <details>
       <summary>Delete</summary>

       ```kotlin
       delete()
           .from(UsersTable)
           .where { UsersTable.id.eq(id) }
           .execute()
       ```
       </details>
   4. <details>
       <summary>Select</summary>

       ```kotlin
       select(UsersTable.allColumns())
           .from(UsersTable)
           .where { UsersTable.id.eq(id) }
           .execute()
           .singleOrNull()
           ?.let {
               UserRecord(
                   id = it[UsersTable.id],
                   username = it[UsersTable.username],
                   password = it[UsersTable.password]
               )
           }
       ```
       </details>
   5. <details>
       <summary>Select with Inner Join</summary>

       ```kotlin
       select(PetsTable.allColumns())
           .from(PetsTable)
           .innerJoin(UsersTable, on = {
               PetsTable.owner.eq(UsersTable.id)
           })
           .where {
               UsersTable.username.eq(username)
           }.execute()
           .map {
               it.toPet()
           }
       ```
       </details>
2. <details>
    <summary>Gradle Plugin for Generating Kotlin Declarations from Migration Scripts</summary>

   Plugin
    ```kotlin
    plugins{
        id("io.github.mejiomah17.yasb")
    }
    tasks.withType<GenerateTablesTask> {
        database = Database.Postgres(DockerImageName.parse("postgres").withTag("16.1"))
        packageName = "com.github.mejiomah17.yasb"
        flywayMigrationDirs.add(projectDir.resolve("src/main/resources/db/migration"))
    }
    ```
   Generates
    ```kotlin
    package com.github.mejiomah17.yasb

    object UsersTable : com.github.mejiomah17.yasb.postgres.jdbc.PostgresJdbcTable<UsersTable> {
        override val tableName = "users"
        val id = uuid("id")
        val password = text("password")
        val username = text("username")
    }
    ```

   From migration script
    ```SQL
    CREATE TABLE public.users
    (
        id       uuid NOT NULL PRIMARY KEY,
        password text NOT NULL,
        username text NOT NULL
    );
    ```
    </details>
3. <details>
    <summary>Explicit Transaction Management</summary>

    ```kotlin
    context(TransactionAtLeastRepeatableRead)
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
        return RegisterResult.Registered(user)
    }

    fun callRegister(){
        // compiler error: register can't be invoked outside of transaction
        register("John", "john_pass")
        transactionFactory.repeatableRead{
            // ok 
            register("John", "john_pass")
        }
        transactionFactory.serializable{
            // ok Serializable > RepeatableRead
            register("John", "john_pass")
        }
        transactionFactory.readCommited{
            // compiler error: ReadCommited < RepeatebleRead
            register("John", "john_pass")
        }

    }
    ```
    </details>
4. <details>
    <summary>Explicit Queries</summary>

    ```kotlin
    val query = select(UsersTable.allColumns())
        .from(UsersTable)
        .where { UsersTable.id.eq(id) }
    query.sql() == "SELECT users.id, users.password, users.username FROM users WHERE users.id = ?"
    query.parameters() == listOf(UuidParameter(id))
    ```
    </details>
5. <details>
    <summary>Extensible</summary>

   Everything can be extended. Here is an example of how Returning is implemented:

    ``` kotlin
    class Returning<DRIVER_DATA_SOURCE, DRIVER_STATEMENT>(
        private val insert: InsertQuery<*, DRIVER_DATA_SOURCE, DRIVER_STATEMENT>,
        private val expressions: List<Expression<*, DRIVER_DATA_SOURCE, DRIVER_STATEMENT>>
    ) : ReturningQuery<DRIVER_DATA_SOURCE, DRIVER_STATEMENT> {

        override fun returnExpressions(): List<Expression<*, DRIVER_DATA_SOURCE, DRIVER_STATEMENT>> {
            return expressions
        }

        override fun sql(): String {
            return insert.sql() + " RETURNING ${expressions.joinToString(", ") { it.sql() }}"
        }

        override fun parameters(): List<Parameter<*, DRIVER_DATA_SOURCE, DRIVER_STATEMENT>> {
            return insert.parameters() + expressions.flatMap { it.parameters() }
        }
    }

    fun <DRIVER_DATA_SOURCE, DRIVER_STATEMENT> InsertQuery<*, DRIVER_DATA_SOURCE, DRIVER_STATEMENT>.returning(
        expressions: List<Expression<*, DRIVER_DATA_SOURCE, DRIVER_STATEMENT>>
    ): Returning<DRIVER_DATA_SOURCE, DRIVER_STATEMENT> {
        return Returning(this, expressions)
    }

    // usage 
    insertInto(Table) {
        it[Table.a] = "abc"
    }.returning(Table.id)
    ``` 
    </details>
6. <details>
    <summary>Explicit Dialect Support</summary>

    ```kotlin
    // ok. SqliteJdbcDatabaseDialect supports insert with returning 
    context(SqliteJdbcDatabaseDialect)
    fun jdbcInsert(){
        insertInto(Table) {
                it[Table.a] = "abc"
            }.returning(Table.id)
        }.execute().single()
    }

    // compiler error: SqliteAndroidDatabaseDialect does not support insert with returning 
    context(SqliteAndroidDatabaseDialect)
    fun jdbcInsert(){
        insertInto(Table) {
                it[Table.a] = "abc"
            }.returning(Table.id)
        }.execute().single()
    }
    ```
    </details>
### Example
[Example](example/jvm)

### Supported Dialects

|         | Postgres           | Sqlite             |
|---------|--------------------|--------------------|
| JVM     | :white_check_mark: | :white_check_mark: |
| Android | :x:                | :white_check_mark: |

### Versioning
In general YASB uses [semver](https://github.com/semver/semver/blob/master/semver.md).
YASB version consist from four parts:
1) Major - incompatible API changes
2) Minor - new functionality in a backward compatible manner
3) Patch - backward compatible bug fixes
4) Kotlin compiler - YASB relies on experimental kotlin feature [Context receivers](https://github.com/Kotlin/KEEP/issues/259).
Each YASB version ends with kotlin compiler version until Context receivers release. 

### Contributions
Any contribution is highly welcome. Feel free to raise PR or issue.

