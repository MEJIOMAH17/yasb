package com.github.mejiomah17.sqlite.android

import com.github.mejiomah17.sqlite.android.type.BooleanDatabaseType
import com.github.mejiomah17.sqlite.android.type.LongDatabaseType
import com.github.mejiomah17.yasb.core.DatabaseDialect
import com.github.mejiomah17.yasb.core.DatabaseType
import com.github.mejiomah17.yasb.core.SupportsLimit

object SqliteAndroidDatabaseDialect : DatabaseDialect, SupportsLimit {
    override fun booleanType(): DatabaseType<Boolean> {
        return BooleanDatabaseType
    }

    override fun longType(): DatabaseType<Long> {
        return LongDatabaseType
    }
}
