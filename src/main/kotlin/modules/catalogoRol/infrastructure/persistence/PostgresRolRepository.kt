package com.finanzasana.modules.catalogoRol.infrastructure.persistence

import com.finanzasana.modules.catalogoRol.domain.model.Rol
import com.finanzasana.modules.catalogoRol.domain.repository.RolRepository
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.transactions.experimental.newSuspendedTransaction

class PostgresRolRepository : RolRepository {

    override suspend fun listar(): List<Rol> = newSuspendedTransaction {
        RolTable.selectAll().map { row ->
            Rol(
                idRol = row[RolTable.idRol],
                nombre = row[RolTable.nombre]
            )
        }
    }

    override suspend fun verPorId(idRol: Int): Rol? = newSuspendedTransaction {
        RolTable
            .select { RolTable.idRol eq idRol }
            .map { row ->
                Rol(
                    idRol = row[RolTable.idRol],
                    nombre = row[RolTable.nombre]
                )
            }
            .singleOrNull()
    }
}