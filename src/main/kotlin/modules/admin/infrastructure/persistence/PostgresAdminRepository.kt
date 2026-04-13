package com.finanzasana.modules.admin.infrastructure.persistence

import com.finanzasana.modules.admin.domain.model.AdminMetrics
import com.finanzasana.modules.admin.domain.model.ActividadAdmin
import com.finanzasana.modules.admin.domain.model.UserAdmin
import com.finanzasana.modules.admin.domain.repository.AdminRepository
import com.finanzasana.modules.deudas.infrastructure.persistence.DeudaTable
import com.finanzasana.modules.usuarios.infrastructure.persistence.UsuarioTable
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.transactions.experimental.newSuspendedTransaction
import java.time.LocalDateTime

class PostgresAdminRepository : AdminRepository {


    override suspend fun obtenerMetricas(): AdminMetrics = newSuspendedTransaction {

        val usuariosTotales = UsuarioTable
            .selectAll()
            .count()
            .toInt()

        val montoGlobal = DeudaTable
            .slice(DeudaTable.saldoActual)
            .selectAll()
            .sumOf { it[DeudaTable.saldoActual] }

        val deudasVencidas = DeudaTable
            .select { DeudaTable.fechaVencimiento lessEq LocalDateTime.now().toLocalDate() }
            .count()
            .toInt()

        AdminMetrics(
            usuariosTotales = usuariosTotales,
            montoGlobal = montoGlobal,
            deudasVencidas = deudasVencidas
        )
    }



    override suspend fun obtenerActividadReciente(): List<ActividadAdmin> = newSuspendedTransaction {

        listOf(
            ActividadAdmin(
                usuario = "Alexis",
                accion = "registró una nueva deuda",
                fecha = LocalDateTime.now().minusMinutes(2)
            ),
            ActividadAdmin(
                usuario = "Ailyn",
                accion = "liquidó una deuda",
                fecha = LocalDateTime.now().minusMinutes(10)
            )
        )
    }


    override suspend fun obtenerUsuariosAdmin(): List<UserAdmin> = newSuspendedTransaction {

        (UsuarioTable leftJoin DeudaTable)
            .slice(
                UsuarioTable.id,
                UsuarioTable.nombre,
                UsuarioTable.email,
                DeudaTable.id.count()
            )
            .selectAll()
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
