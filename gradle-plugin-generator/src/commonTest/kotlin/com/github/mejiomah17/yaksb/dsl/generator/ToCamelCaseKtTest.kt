package com.github.mejiomah17.yaksb.dsl.generator

import io.kotest.matchers.shouldBe
import org.junit.Test

class ToCamelCaseKtTest {
    @Test
    fun `do_nothing_on_camel_cased_source`() {
        "toCamelCaseKtTest".toCamelCase() shouldBe "toCamelCaseKtTest"
    }

    @Test
    fun `lowercase_first_letter`() {
        "ToCamelCaseKtTest".toCamelCase() shouldBe "toCamelCaseKtTest"
    }

    @Test
    fun `remove_space_and_uppercase_next_letter`() {
        "toCamel caseKtTest".toCamelCase() shouldBe "toCamelCaseKtTest"
    }

    @Test
    fun `remove_dash_and_uppercase_next_letter`() {
        "toCamel-caseKtTest".toCamelCase() shouldBe "toCamelCaseKtTest"
    }

    @Test
    fun `remove_underscore_and_uppercase_next_letter`() {
        "toCamel_caseKtTest".toCamelCase() shouldBe "toCamelCaseKtTest"
    }

    @Test
    fun `transforms_screaming_upper_case`() {
        "TO_CAMEL_CASE_KT_TEST".toCamelCase() shouldBe "toCamelCaseKtTest"
    }
}
