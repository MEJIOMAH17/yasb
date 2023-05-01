package com.github.mejiomah17.yasb.dsl.generator

import io.kotest.matchers.shouldBe
import org.junit.Test

class ToCamelCaseKtTest {
    @Test
    fun `do nothing on camel cased source`() {
        "toCamelCaseKtTest".toCamelCase() shouldBe "toCamelCaseKtTest"
    }

    @Test
    fun `lowercase first letter`() {
        "ToCamelCaseKtTest".toCamelCase() shouldBe "toCamelCaseKtTest"
    }

    @Test
    fun `remove space and uppercase next letter`() {
        "toCamel caseKtTest".toCamelCase() shouldBe "toCamelCaseKtTest"
    }

    @Test
    fun `remove dash and uppercase next letter`() {
        "toCamel-caseKtTest".toCamelCase() shouldBe "toCamelCaseKtTest"
    }

    @Test
    fun `remove underscore and uppercase next letter`() {
        "toCamel_caseKtTest".toCamelCase() shouldBe "toCamelCaseKtTest"
    }
}
