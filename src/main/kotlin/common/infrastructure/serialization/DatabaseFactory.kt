package com.finanzasana.common.infrastructure

import com.finanzasana.modules.usuarios.infrastructure.persistence.UsuarioTable
import com.finanzasana.modules.deudas.infrastructure.persistence.DeudaTable
import com.finanzasana.modules.categorias.infrastructure.persistence.CategoriaTable

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
                ?: "jdbc:postgresql://localhost:5432/finanza_sana"

            username = System.getenv("DB_USER") ?: "postgres"
            password = System.getenv("DB_PASSWORD") ?: "root"

            maximumPoolSize = 10
            isAutoCommit = false
            transactionIsolation = "TRANSACTION_REPEATABLE_READ"
        }

        val dataSource = HikariDataSource(config)
        Database.connect(dataSource)

        transaction {
            SchemaUtils.create(
                UsuarioTable,
                DeudaTable,
                CategoriaTable
            )
        }
    }
}