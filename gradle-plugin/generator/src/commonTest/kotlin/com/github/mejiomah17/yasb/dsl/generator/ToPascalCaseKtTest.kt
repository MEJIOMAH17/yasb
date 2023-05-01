package com.github.mejiomah17.yasb.dsl.generator

import io.kotest.matchers.shouldBe
import org.junit.Test

class ToPascalCaseKtTest {
    @Test
    fun `do_nothing_on_pascal_cased_source`() {
        "ToCamelCaseKtTest".toPascalCase() shouldBe "ToCamelCaseKtTest"
    }

    @Test
    fun `uppercase_first_letter`() {
        "toCamelCaseKtTest".toPascalCase() shouldBe "ToCamelCaseKtTest"
    }

    @Test
    fun `remove_space_and_uppercase_next_letter`() {
        "ToCamel caseKtTest".toPascalCase() shouldBe "ToCamelCaseKtTest"
    }

    @Test
    fun `remove_dash_and_uppercase_next_letter`() {
        "ToCamel-caseKtTest".toPascalCase() shouldBe "ToCamelCaseKtTest"
    }

    @Test
    fun `remove_underscore_and_uppercase_next_letter`() {
        "ToCamel_caseKtTest".toPascalCase() shouldBe "ToCamelCaseKtTest"
    }
}
