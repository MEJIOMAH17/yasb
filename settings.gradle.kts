rootProject.name = "yasb"
include(
    "core",
    "core:postgres",
    "dsl",
    "dsl:postgres",
    "dsl:generator",
    "dsl:generator:flyway",
    "dsl:generator:postgres"
)

