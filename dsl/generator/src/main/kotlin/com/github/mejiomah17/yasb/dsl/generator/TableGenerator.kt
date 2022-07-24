package com.github.mejiomah17.yasb.dsl.generator

class TableGenerator {

    fun generateTable(table: TableMetadata, classPackage: String): GeneratedCode {
        val camelCasedTableName = table.tableName.toPascalCase()
        val className = if (camelCasedTableName.endsWith("Table")) {
            camelCasedTableName
        } else {
            "${camelCasedTableName}Table"
        }
        return GeneratedCode(
            fileName = "$className.kt",
            content = buildString {
                appendLine("package $classPackage")
                appendLine()
                appendLine("object $className : com.github.mejiomah17.yasb.core.postgres.ddl.PostgresTable<$className> {")
                appendLine("    override val tableName = \"${table.tableName}\"")
                table.columns.forEach {
                    appendLine(
                        "    ${it.columnDefinition()}"
                    )
                }
                appendLine("}")
            }
        )
    }
}
