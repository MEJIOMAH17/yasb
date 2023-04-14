package com.github.mejiomah17.sqlite.android.type

import com.github.mejiomah17.sqlite.android.AndroidDatabaseType
import com.github.mejiomah17.sqlite.android.parameter.LongParameter
import com.github.mejiomah17.yasb.core.parameter.Parameter

object LongDatabaseType : AndroidDatabaseType<Long> {
    override fun parameterFactory(): (Long?) -> Parameter<Long> = ::LongParameter
}
