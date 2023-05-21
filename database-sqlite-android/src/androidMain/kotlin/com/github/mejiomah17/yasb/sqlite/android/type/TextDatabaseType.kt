package com.github.mejiomah17.yasb.sqlite.android.type

import android.database.Cursor
import com.github.mejiomah17.yasb.core.parameter.Parameter
import com.github.mejiomah17.yasb.sqlite.android.AndroidDatabaseType
import com.github.mejiomah17.yasb.sqlite.android.parameter.TextParameter

object TextDatabaseType : AndroidDatabaseType<String> {
    override fun parameterFactory(): (String?) -> Parameter<String, Cursor, (String) -> Unit> = ::TextParameter
    override fun extractFromSource(source: Cursor, index: Int): String? {
        return source.getString(index)
    }
}
