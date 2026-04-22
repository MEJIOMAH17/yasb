package com.github.mejiomah17.yaksb.core.util

/**
 * remove after a stable release in stdlib
 */
internal inline fun <K, V> MutableMap<K, V>.getOrPutIfMissing(
    key: K,
    crossinline defaultValue: () -> V,
): V {
    val value = get(key)
    return if (value == null && !containsKey(key)) {
        val answer = defaultValue()
        put(key, answer)
        answer
    } else {
        @Suppress("UNCHECKED_CAST")
        value as V
    }
}
