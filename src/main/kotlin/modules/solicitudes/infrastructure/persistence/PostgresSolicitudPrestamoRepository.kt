package com.finanzasana.modules.solicitudes.infrastructure.persistence

import com.finanzasana.modules.solicitudes.domain.model.HistorialSolicitud
import com.finanzasana.modules.solicitudes.domain.model.PendienteSolicitud
import com.finanzasana.modules.solicitudes.domain.model.SolicitudPrestamo
import com.finanzasana.modules.solicitudes.domain.repository.SolicitudPrestamoRepository
import com.finanzasana.modules.empresas.infrastructure.persistence.EmpresaPrestamoTable
import com.finanzasana.modules.usuarios.infrastructure.persistence.UsuarioTable
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.transactions.experimental.newSuspendedTransaction

class PostgresSolicitudPrestamoRepository : SolicitudPrestamoRepository {

    private fun toDomain(row: ResultRow): SolicitudPrestamo = SolicitudPrestamo(
        id = row[SolicitudPrestamoTable.id],
        idUsuario = row[SolicitudPrestamoTable.idUsuario],
        idEmpresa = row[SolicitudPrestamoTable.idEmpresa],
        montoSolicitado = row[SolicitudPrestamoTable.montoSolicitado],
        meses = row[SolicitudPrestamoTable.meses],
        motivo = row[SolicitudPrestamoTable.motivo],
        tasaInteres = row[SolicitudPrestamoTable.tasaInteres],
        idCategoria = row[SolicitudPrestamoTable.idCategoria],
        imagenBase64 = row[SolicitudPrestamoTable.imagenBase64],
        latitud = row[SolicitudPrestamoTable.latitud],
        longitud = row[SolicitudPrestamoTable.longitud],
        estado = row[SolicitudPrestamoTable.estado],
        fechaSolicitud = row[SolicitudPrestamoTable.fechaSolicitud]
    )

    override suspend fun registrar(solicitud: SolicitudPrestamo): SolicitudPrestamo =
        newSuspendedTransaction {

            val nuevoId = SolicitudPrestamoTable.insert {
                it[idUsuario] = solicitud.idUsuario
                it[idEmpresa] = solicitud.idEmpresa
                it[montoSolicitado] = solicitud.montoSolicitado
                it[meses] = solicitud.meses
                it[motivo] = solicitud.motivo
                it[tasaInteres] = solicitud.tasaInteres
                it[idCategoria] = solicitud.idCategoria
                it[imagenBase64] = solicitud.imagenBase64
                it[latitud] = solicitud.latitud
                it[longitud] = solicitud.longitud
                it[estado] = solicitud.estado
                it[fechaSolicitud] = solicitud.fechaSolicitud
            }[SolicitudPrestamoTable.id]

            SolicitudPrestamoTable
                .select { SolicitudPrestamoTable.id eq nuevoId }
                .map { toDomain(it) }
                .single()
        }

    override suspend fun obtenerPorId(id: Int): SolicitudPrestamo? =
        newSuspendedTransaction {
            SolicitudPrestamoTable
                .select { SolicitudPrestamoTable.id eq id }
                .map { toDomain(it) }
                .singleOrNull()
        }

    override suspend fun listarPendientes(): List<SolicitudPrestamo> =
        newSuspendedTransaction {
            SolicitudPrestamoTable
                .select { SolicitudPrestamoTable.estado eq 1 } // 1 = PENDIENTE
                .orderBy(SolicitudPrestamoTable.fechaSolicitud, SortOrder.ASC)
                .map { toDomain(it) }
        }

    override suspend fun listarPendientesAdmin(): List<PendienteSolicitud> =
        newSuspendedTransaction {
            (SolicitudPrestamoTable innerJoin UsuarioTable innerJoin EmpresaPrestamoTable)
                .slice(
                    SolicitudPrestamoTable.id,
                    UsuarioTable.nombre,
                    EmpresaPrestamoTable.nombre,
                    SolicitudPrestamoTable.montoSolicitado,
                    SolicitudPrestamoTable.estado,
                    SolicitudPrestamoTable.fechaSolicitud
                )
                .select { SolicitudPrestamoTable.estado eq 1 }
                .orderBy(SolicitudPrestamoTable.fechaSolicitud, SortOrder.ASC)
                .map { row ->
                    PendienteSolicitud(
                        id = row[SolicitudPrestamoTable.id],
                        nombreUsuario = row[UsuarioTable.nombre],
                        nombreEmpresa = row[EmpresaPrestamoTable.nombre],
                        montoSolicitado = row[SolicitudPrestamoTable.montoSolicitado],
                        estado = row[SolicitudPrestamoTable.estado]
                    )
                }
        }

    override suspend fun listarHistorial(): List<HistorialSolicitud> =
        newSuspendedTransaction {
            (SolicitudPrestamoTable innerJoin UsuarioTable innerJoin EmpresaPrestamoTable)
                .slice(
                    SolicitudPrestamoTable.id,
                    UsuarioTable.nombre,
                    EmpresaPrestamoTable.nombre,
                    SolicitudPrestamoTable.montoSolicitado,
                    SolicitudPrestamoTable.estado,
                    SolicitudPrestamoTable.fechaSolicitud
                )
                .select {
                    (SolicitudPrestamoTable.estado eq 2) or
                        (SolicitudPrestamoTable.estado eq 3)
                }
                .orderBy(SolicitudPrestamoTable.fechaSolicitud, SortOrder.DESC)
                .map { row ->
                    HistorialSolicitud(
                        id = row[SolicitudPrestamoTable.id],
                        nombreUsuario = row[UsuarioTable.nombre],
                        nombreEmpresa = row[EmpresaPrestamoTable.nombre],
                        montoSolicitado = row[SolicitudPrestamoTable.montoSolicitado],
                        estado = row[SolicitudPrestamoTable.estado]
                    )
                }
        }

    override suspend fun actualizarEstado(id: Int, nuevoEstado: Int) =
        newSuspendedTransaction {
            SolicitudPrestamoTable.update({ SolicitudPrestamoTable.id eq id }) {
                it[estado] = nuevoEstado
            }
            Unit
        }
}
