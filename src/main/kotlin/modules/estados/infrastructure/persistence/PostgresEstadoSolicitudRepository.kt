package com.finanzasana.modules.estados.infrastructure.persistence

import com.finanzasana.modules.estados.domain.model.EstadoSolicitud
import com.finanzasana.modules.estados.domain.repository.EstadoSolicitudRepository
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.transactions.transaction

class PostgresEstadoSolicitudRepository : EstadoSolicitudRepository {

    private fun toDomain(row: ResultRow) = EstadoSolicitud(
        id = row[EstadoSolicitudTable.id],
        nombre = row[EstadoSolicitudTable.nombre]
    )

    override suspend fun obtenerPorId(id: Int): EstadoSolicitud? =
        transaction {
            EstadoSolicitudTable
                .select { EstadoSolicitudTable.id eq id }
                .map { toDomain(it) }
                .singleOrNull()
        }

    override suspend fun listar(): List<EstadoSolicitud> =
        transaction {
            EstadoSolicitudTable
                .selectAll()
                .map { toDomain(it) }
        }
}
