package com.github.mejiomah17.yasb.dsl.generator

import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.Test

class ToPascalCaseKtTest {
    @Test
    fun `do nothing on pascal cased source`() {
        "ToCamelCaseKtTest".toPascalCase() shouldBe "ToCamelCaseKtTest"
    }

    @Test
    fun `uppercase first letter`() {
        "toCamelCaseKtTest".toPascalCase() shouldBe "ToCamelCaseKtTest"
    }

    @Test
    fun `remove space and uppercase next letter`() {
        "ToCamel caseKtTest".toPascalCase() shouldBe "ToCamelCaseKtTest"
    }

    @Test
    fun `remove dash and uppercase next letter`() {
        "ToCamel-caseKtTest".toPascalCase() shouldBe "ToCamelCaseKtTest"
    }

    @Test
    fun `remove underscore and uppercase next letter`() {
        "ToCamel_caseKtTest".toPascalCase() shouldBe "ToCamelCaseKtTest"
    }
}
