package com.finanzasana.modules.empresas.infrastructure.persistence

import com.finanzasana.modules.empresas.domain.model.EmpresaPrestamo
import com.finanzasana.modules.empresas.domain.repository.EmpresaPrestamoRepository
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.transactions.transaction

class PostgresEmpresaPrestamoRepository : EmpresaPrestamoRepository {

    private fun toDomain(row: ResultRow) = EmpresaPrestamo(
        id = row[EmpresaPrestamoTable.id],
        nombre = row[EmpresaPrestamoTable.nombre],
        tasaInteres = row[EmpresaPrestamoTable.tasaInteres]
    )

    override suspend fun registrar(empresa: EmpresaPrestamo): EmpresaPrestamo {
        val idGenerado = transaction {
            EmpresaPrestamoTable.insert {
                it[nombre] = empresa.nombre
                it[tasaInteres] = empresa.tasaInteres
            }[EmpresaPrestamoTable.id]
        }

        return empresa.copy(id = idGenerado)
    }

    override suspend fun obtenerPorId(id: Int): EmpresaPrestamo? =
        transaction {
            EmpresaPrestamoTable
                .select { EmpresaPrestamoTable.id eq id }
                .map { toDomain(it) }
                .singleOrNull()
        }

    override suspend fun listar(): List<EmpresaPrestamo> =
        transaction {
            EmpresaPrestamoTable
                .selectAll()
                .map { toDomain(it) }
        }
}
