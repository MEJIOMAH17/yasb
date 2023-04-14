package com.github.mejiomah17.sqlite.android.parameter

import com.github.mejiomah17.sqlite.android.AndroidDatabaseType
import com.github.mejiomah17.sqlite.android.type.TextDatabaseType

class TextParameter(
    override val value: String?
) : SqliteParameter<String>() {
    override val databaseType: AndroidDatabaseType<String> = TextDatabaseType
}
