package com.finanzasana.modules.catalogoRol.infrastructure.persistence

import com.finanzasana.modules.catalogoRol.domain.model.Rol
import com.finanzasana.modules.catalogoRol.domain.repository.RolRepository
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.transactions.experimental.newSuspendedTransaction

class PostgresRolRepository : RolRepository {

    override suspend fun listar(): List<Rol> = newSuspendedTransaction {
        RolTable.selectAll().map { row ->
            Rol(
                id = row[RolTable.id],
                nombre = row[RolTable.nombre]
            )
        }
    }

    override suspend fun verPorId(id: Int): Rol? = newSuspendedTransaction {
        RolTable
            .select { RolTable.id eq id }
            .map { row ->
                Rol(
                    id = row[RolTable.id],
                    nombre = row[RolTable.nombre]
                )
            }
            .singleOrNull()
    }
}