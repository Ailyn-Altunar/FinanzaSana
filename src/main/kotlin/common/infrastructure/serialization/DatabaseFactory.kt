package com.finanzasana.common.infrastructure.serialization

import com.finanzasana.modules.catalogoRol.infrastructure.persistence.RolTable
import com.finanzasana.modules.categorias.infrastructure.persistence.CategoriaTable
import com.finanzasana.modules.deudas.infrastructure.persistence.AbonoTable
import com.finanzasana.modules.deudas.infrastructure.persistence.DeudaTable
import com.finanzasana.modules.empresas.infrastructure.persistence.EmpresaPrestamoTable
import com.finanzasana.modules.estados.infrastructure.persistence.EstadoSolicitudTable
import com.finanzasana.modules.solicitudes.infrastructure.persistence.SolicitudPrestamoTable
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
            SchemaUtils.createMissingTablesAndColumns(
                UsuarioTable,
                RolTable,
                CategoriaTable,
                DeudaTable,
                AbonoTable,
                EmpresaPrestamoTable,
                EstadoSolicitudTable,
                SolicitudPrestamoTable

            )
        }
    }
}
