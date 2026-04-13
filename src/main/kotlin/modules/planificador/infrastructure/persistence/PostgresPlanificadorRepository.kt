package com.finanzasana.modules.planificador.infrastructure.persistence

import com.finanzasana.modules.deudas.infrastructure.persistence.DeudaTable
import com.finanzasana.modules.deudas.domain.model.Deuda
import com.finanzasana.modules.planificador.domain.model.PlanificadorResultado
import com.finanzasana.modules.planificador.domain.repository.PlanificadorRepository
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.transactions.experimental.newSuspendedTransaction

class PostgresPlanificadorRepository : PlanificadorRepository {

    // ---------------------------
    //   MAPPER
    // ---------------------------
    private fun toDomain(row: ResultRow): Deuda = Deuda(
        id = row[DeudaTable.id],
        concepto = row[DeudaTable.concepto],
        montoOriginal = row[DeudaTable.montoOriginal],
        saldoActual = row[DeudaTable.saldoActual],
        tasaInteres = row[DeudaTable.tasaInteres],
        fechaVencimiento = row[DeudaTable.fechaVencimiento],
        idCategoria = row[DeudaTable.idCategoria],
        idUsuario = row[DeudaTable.idUsuario]
    )

    // ---------------------------
    //   GENERAR PLAN
    // ---------------------------
    override suspend fun generarPlan(idUsuario: Int, metodo: String): PlanificadorResultado =
        newSuspendedTransaction {

            val deudas = DeudaTable
                .select { DeudaTable.idUsuario eq idUsuario }
                .map { toDomain(it) }

            val deudasOrdenadas = when (metodo) {
                "Avalancha" -> deudas.sortedByDescending { it.tasaInteres ?: 0.0 }
                else -> deudas.sortedBy { it.saldoActual }
            }

            val total = deudas.sumOf { it.saldoActual }
            val tasaPromedio = deudas.mapNotNull { it.tasaInteres }.average()
                .takeIf { !it.isNaN() } ?: 0.0

            PlanificadorResultado(
                metodo = metodo,
                totalDeuda = total,
                tasaPromedio = tasaPromedio,
                deudasOrdenadas = deudasOrdenadas
            )
        }
}
