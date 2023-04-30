package com.github.mejiomah17.yasb.sqlite.android.type

import android.database.Cursor
import com.github.mejiomah17.yasb.core.parameter.Parameter
import com.github.mejiomah17.yasb.sqlite.android.AndroidDatabaseType
import com.github.mejiomah17.yasb.sqlite.android.parameter.BooleanParameter

object BooleanDatabaseType : AndroidDatabaseType<Boolean> {

    override fun parameterFactory(): (Boolean?) -> Parameter<Boolean, Cursor, String> = ::BooleanParameter
    override fun extractFromSource(source: Cursor, index: Int): Boolean? {
        TODO()
    }
}
