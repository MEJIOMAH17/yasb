package com.github.mejiomah17.yaksb.dsl.generator

private val delimiters = setOf('-', '_', ' ', '\t')

fun String.toCamelCase(): String {
    val str =
        if (uppercase() == this) {
            lowercase()
        } else {
            this
        }

    return buildString {
        str.forEachIndexed { index, c ->
            if (index == 0) {
                append(c.lowercase())
            } else if (c in delimiters) {
                // ignore
            } else {
                if (str[index - 1] in delimiters) {
                    append(c.uppercaseChar())
                } else {
                    append(c)
                }
            }
        }
    }
}

fun String.toPascalCase(): String = toCamelCase().replaceFirstChar { if (it.isLowerCase()) it.titlecase() else it.toString() }
