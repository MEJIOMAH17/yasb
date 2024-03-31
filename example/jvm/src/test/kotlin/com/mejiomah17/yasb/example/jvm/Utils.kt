package com.mejiomah17.yasb.example.jvm

import kotlinx.coroutines.runBlocking
import org.awaitility.core.ConditionFactory

fun ConditionFactory.coUntil(conditionEvaluator: suspend () -> Boolean) {
    until {
        runBlocking {
            conditionEvaluator()
        }
    }
}
