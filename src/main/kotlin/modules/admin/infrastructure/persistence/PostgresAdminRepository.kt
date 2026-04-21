package com.finanzasana.modules.admin.infrastructure.persistence

import com.finanzasana.modules.admin.domain.model.ActividadAdmin
import com.finanzasana.modules.admin.domain.model.AdminMetrics
import com.finanzasana.modules.admin.domain.model.UserAdmin
import com.finanzasana.modules.admin.domain.repository.AdminRepository
import com.finanzasana.modules.deudas.infrastructure.persistence.AbonoTable
import com.finanzasana.modules.deudas.infrastructure.persistence.DeudaTable
import com.finanzasana.modules.usuarios.infrastructure.persistence.UsuarioTable
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.transactions.experimental.newSuspendedTransaction
import java.time.LocalDateTime

class PostgresAdminRepository : AdminRepository {

    override suspend fun obtenerMetricas(): AdminMetrics = newSuspendedTransaction {

        val usuariosTotales = UsuarioTable
            .select { UsuarioTable.idRol neq 1 and (UsuarioTable.activo eq true) }
            .count()
            .toInt()

        val montoGlobal = DeudaTable
            .join(
                UsuarioTable,
                JoinType.INNER,
                onColumn = DeudaTable.idUsuario,
                otherColumn = UsuarioTable.id
            )
            .slice(DeudaTable.saldoActual)
            .select { UsuarioTable.activo eq true }
            .sumOf { it[DeudaTable.saldoActual] }

        val hoy = LocalDateTime.now().toLocalDate()

        val deudasVencidas = DeudaTable
            .select {
                (DeudaTable.saldoActual greater 0.0) and
                    (DeudaTable.fechaVencimiento less hoy)
            }
            .count()
            .toInt()

        AdminMetrics(
            usuariosTotales = usuariosTotales,
            montoGlobal = montoGlobal,
            deudasVencidas = deudasVencidas
        )
    }

    override suspend fun obtenerActividadReciente(): List<ActividadAdmin> = newSuspendedTransaction {
        (AbonoTable innerJoin UsuarioTable)
            .slice(
                UsuarioTable.nombre,
                AbonoTable.monto,
                AbonoTable.idDeuda,
                AbonoTable.fecha
            )
            .selectAll()
            .orderBy(AbonoTable.fecha, SortOrder.DESC)
            .limit(20)
            .map { row ->
                ActividadAdmin(
                    nombreUsuario = row[UsuarioTable.nombre],
                    montoAbono = row[AbonoTable.monto],
                    idDeuda = row[AbonoTable.idDeuda],
                    fecha = row[AbonoTable.fecha]
                )
            }
    }

    override suspend fun obtenerUsuariosAdmin(): List<UserAdmin> = newSuspendedTransaction {

        (UsuarioTable leftJoin DeudaTable)
            .slice(
                UsuarioTable.id,
                UsuarioTable.nombre,
                UsuarioTable.email,
                DeudaTable.id.count()
            )
            .select {
                (UsuarioTable.idRol neq 1) and
                    (UsuarioTable.activo eq true)
            }
            .groupBy(UsuarioTable.id)
            .map { row ->
                UserAdmin(
                    id = row[UsuarioTable.id],
                    nombre = row[UsuarioTable.nombre],
                    email = row[UsuarioTable.email],
                    totalDeudas = row[DeudaTable.id.count()].toInt()
                )
            }
    }
}
