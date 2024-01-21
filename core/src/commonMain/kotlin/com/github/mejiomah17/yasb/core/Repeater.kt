package com.github.mejiomah17.yasb.core

fun interface Repeater<T> {
    /**
     * Runs the [block] of code at least once to achieve some successful result
     */
    fun repeat(block: () -> T): T

    companion object {
        inline fun <T, reified E : Exception> repeatOn(count: Int): Repeater<T> {
            check(count > 0) { "count must be greater than 0. Actual value is $count" }

            return Repeater { block ->
                for (counter in 0 until count - 1) {
                    try {
                        return@Repeater block()
                    } catch (ex: Exception) {
                        if (ex !is E) {
                            throw ex
                        }
                    }
                }
                block()
            }
        }
    }
}
