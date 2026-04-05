package com.finanzasana.common.infrastructure.serialization

import com.finanzasana.modules.catalogoRol.infrastructure.persistence.RolTable
import com.finanzasana.modules.usuarios.infrastructure.persistence.UsuarioTable
import com.zaxxer.hikari.HikariConfig
import com.zaxxer.hikari.HikariDataSource
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.transactions.transaction

object DatabaseFactory {

    fun init() {
        val config = HikariConfig().apply {
            driverClassName = "org.postgresql.Driver"

            jdbcUrl = System.getenv("DB_URL")
                ?: "jdbc:postgresql://localhost:5432/finanzasana"

            username = System.getenv("DB_USER") ?: "postgres"
            password = System.getenv("DB_PASSWORD") ?: "lyn250319"

            maximumPoolSize = 10
            isAutoCommit = false
            transactionIsolation = "TRANSACTION_REPEATABLE_READ"
        }

        val dataSource = HikariDataSource(config)
        Database.connect(dataSource)

        transaction {
            SchemaUtils.create(
                UsuarioTable,
                RolTable,

            )
        }
    }
}