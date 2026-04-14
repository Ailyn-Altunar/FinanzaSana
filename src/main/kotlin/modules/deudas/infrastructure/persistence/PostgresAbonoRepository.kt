package com.finanzasana.modules.deudas.infrastructure.persistence

import com.finanzasana.modules.deudas.domain.model.Abono
import com.finanzasana.modules.deudas.domain.repository.AbonoRepository
import com.finanzasana.modules.usuarios.infrastructure.persistence.UsuarioTable
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.transactions.experimental.newSuspendedTransaction

class PostgresAbonoRepository : AbonoRepository {


    private fun AbonosConUsuario() = AbonoTable.join(
        UsuarioTable,
        joinType = JoinType.INNER,
        onColumn = AbonoTable.idUsuario,
        otherColumn = UsuarioTable.id
    )


    private fun toDomain(row: ResultRow): Abono = Abono(
        id = row[AbonoTable.id],
        idDeuda = row[AbonoTable.idDeuda],
        idUsuario = row[AbonoTable.idUsuario],
        monto = row[AbonoTable.monto],
        fecha = row[AbonoTable.fecha]
    )


    override suspend fun registrar(abono: Abono): Abono = newSuspendedTransaction {
        val nuevoId = AbonoTable.insert {
            it[idDeuda] = abono.idDeuda
            it[idUsuario] = abono.idUsuario
            it[monto] = abono.monto
            it[fecha] = abono.fecha
        }[AbonoTable.id]

        AbonosConUsuario()
            .select { AbonoTable.id eq nuevoId }
            .map { toDomain(it) }
            .singleOrNull()
            ?: throw IllegalStateException("El abono se insertó con ID $nuevoId pero no se pudo recuperar")
    }


    override suspend fun listarPorDeuda(idDeuda: Int, idUsuario: Int): List<Abono> = newSuspendedTransaction {
        AbonosConUsuario()
            .select {
                (AbonoTable.idDeuda eq idDeuda) and
                        (AbonoTable.idUsuario eq idUsuario)
            }
            .orderBy(AbonoTable.fecha, SortOrder.DESC)
            .map { toDomain(it) }
    }
}
