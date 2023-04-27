package com.github.mejiomah17.sqlite.android.type

import android.database.Cursor
import com.github.mejiomah17.sqlite.android.AndroidDatabaseType
import com.github.mejiomah17.sqlite.android.parameter.BooleanParameter
import com.github.mejiomah17.yasb.core.parameter.Parameter

object BooleanDatabaseType : AndroidDatabaseType<Boolean> {

    override fun parameterFactory(): (Boolean?) -> Parameter<Boolean, Cursor, String> = ::BooleanParameter
    override fun extractFromSource(source: Cursor, index: Int): Boolean? {
        TODO()
    }
}
