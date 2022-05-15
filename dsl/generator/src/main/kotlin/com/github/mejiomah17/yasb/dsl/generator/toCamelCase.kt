package com.github.mejiomah17.yasb.dsl.generator

fun String.toCamelCase(): String {
    return buildString {
        var uppercaseNextLetter = false
        for ((i, ch) in this@toCamelCase.withIndex()) {
            if (i == 0) {
                append(ch.lowercaseChar())
            } else {
                if (wordDelimiters.contains(ch)) {
                    uppercaseNextLetter = true
                } else {
                    if (uppercaseNextLetter) {
                        append(ch.uppercaseChar())
                    } else {
                        append(ch)
                    }
                    uppercaseNextLetter = false
                }
            }
        }
    }
}

fun String.toPascalCase(): String {
    return buildString {
        var uppercaseNextLetter = false
        for ((i, ch) in this@toPascalCase.withIndex()) {
            if (i == 0) {
                append(ch.uppercaseChar())
            } else {
                if (wordDelimiters.contains(ch)) {
                    uppercaseNextLetter = true
                } else {
                    if (uppercaseNextLetter) {
                        append(ch.uppercaseChar())
                    } else {
                        append(ch)
                    }
                    uppercaseNextLetter = false
                }
            }
        }
    }
}

private val wordDelimiters = setOf(' ','-','_')