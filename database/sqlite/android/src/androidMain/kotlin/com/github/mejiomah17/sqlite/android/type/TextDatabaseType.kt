package com.github.mejiomah17.sqlite.android.type

import com.github.mejiomah17.sqlite.android.AndroidDatabaseType
import com.github.mejiomah17.sqlite.android.parameter.TextParameter
import com.github.mejiomah17.yasb.core.parameter.Parameter

object TextDatabaseType : AndroidDatabaseType<String> {
    override fun parameterFactory(): (String?) -> Parameter<String> = ::TextParameter
}
