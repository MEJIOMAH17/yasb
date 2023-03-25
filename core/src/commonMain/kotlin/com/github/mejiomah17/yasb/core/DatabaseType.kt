package com.github.mejiomah17.yasb.core

import com.github.mejiomah17.yasb.core.parameter.Parameter

interface DatabaseType<T> {
    fun parameterFactory(): (T?) -> Parameter<T>
}
