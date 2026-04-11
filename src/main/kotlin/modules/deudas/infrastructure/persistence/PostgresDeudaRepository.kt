package com.finanzasana.modules.deudas.infrastructure.persistence

import com.finanzasana.modules.deudas.domain.model.Deuda
import com.finanzasana.modules.deudas.domain.repository.DeudaRepository
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.transactions.experimental.newSuspendedTransaction

class PostgresDeudaRepository : DeudaRepository {

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
    //   REGISTRAR DEUDA
    // ---------------------------
    override suspend fun registrar(deuda: Deuda): Deuda = newSuspendedTransaction {

        val nuevoId = DeudaTable.insert {
            it[concepto] = deuda.concepto
            it[montoOriginal] = deuda.montoOriginal
            it[saldoActual] = deuda.saldoActual
            it[tasaInteres] = deuda.tasaInteres
            it[fechaVencimiento] = deuda.fechaVencimiento
            it[idCategoria] = deuda.idCategoria
            it[idUsuario] = deuda.idUsuario
        }[DeudaTable.id]

        DeudaTable
            .select { DeudaTable.id eq nuevoId }
            .map { toDomain(it) }
            .single()
    }

    // ---------------------------
    //   LISTAR DEUDAS POR USUARIO
    // ---------------------------
    override suspend fun listarPorUsuario(idUsuario: Int): List<Deuda> = newSuspendedTransaction {
        DeudaTable
            .select { DeudaTable.idUsuario eq idUsuario }
            .orderBy(DeudaTable.fechaVencimiento, SortOrder.ASC)
            .map { toDomain(it) }
    }

    // ---------------------------
    //   VER DETALLE DE UNA DEUDA
    // ---------------------------
    override suspend fun obtenerPorId(idDeuda: Int, idUsuario: Int): Deuda? = newSuspendedTransaction {
        DeudaTable
            .select {
                (DeudaTable.id eq idDeuda) and
                        (DeudaTable.idUsuario eq idUsuario)
            }
            .map { toDomain(it) }
            .singleOrNull()
    }

    // ---------------------------
    //   ACTUALIZAR SALDO
    // ---------------------------
    override suspend fun actualizarSaldo(idDeuda: Int, nuevoSaldo: Double) = newSuspendedTransaction {
        DeudaTable.update({ DeudaTable.id eq idDeuda }) {
            it[saldoActual] = nuevoSaldo
        }
        Unit
    }

    // ---------------------------
    //   LIQUIDAR DEUDA
    // ---------------------------
    override suspend fun liquidar(idDeuda: Int) = newSuspendedTransaction {
        DeudaTable.update({ DeudaTable.id eq idDeuda }) {
            it[saldoActual] = 0.0
        }
        Unit
    }
}
