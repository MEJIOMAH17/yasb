package com.github.mejiomah17.yasb.sqlite

// class SqliteCountTest<
//     TABLE : SqliteTestTable<TABLE, DRIVER_DATA_SOURCE, DRIVER_STATEMENT>,
//     DRIVER_DATA_SOURCE,
//     DRIVER_STATEMENT,
//     DIALECT : SqliteDatabaseDialect<DRIVER_DATA_SOURCE, DRIVER_STATEMENT>,
//     TRANSACTION : Transaction<DRIVER_DATA_SOURCE, DRIVER_STATEMENT>
//     > :
//     CountTest<TABLE, DRIVER_DATA_SOURCE, DRIVER_STATEMENT, DIALECT, TRANSACTION> {
//
//     fun initSqlScripts(): List<String> {
//         return listOf(
//             "DELETE FROM test",
//             """INSERT INTO test (a,b) values (
//                     |'the a',
//                     |'the b'
//                     | ),
//                     | (
//                     |'the a',
//                     |'the asd'
//                     | )
//             """.trimMargin()
//         )
//     }
//
//     override fun columnA(): Column<TABLE, String, DRIVER_DATA_SOURCE, DRIVER_STATEMENT> {
//         return tableTest().a
//     }
//
//     override fun columnB(): Column<TABLE, String, DRIVER_DATA_SOURCE, DRIVER_STATEMENT> {
//         return tableTest().b
//     }
//
//     override fun parameter(): Parameter<String, ResultSet, PreparedStatement> {
//         return TextParameter("param")
//     }
//
//     override fun transactionFactory(): SqliteJdbcTransactionFactory {
//         return SqliteJdbcTransactionFactory(dataSource)
//     }
// }
