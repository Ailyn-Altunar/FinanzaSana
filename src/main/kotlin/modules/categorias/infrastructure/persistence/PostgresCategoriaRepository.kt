package com.finanzasana.modules.categorias.infrastructure.persistence

import com.finanzasana.modules.categorias.domain.model.Categoria
import com.finanzasana.modules.categorias.domain.repository.CategoriaRepository
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.transactions.experimental.newSuspendedTransaction

class PostgresCategoriaRepository : CategoriaRepository {


    private fun toDomain(row: ResultRow): Categoria = Categoria(
        id = row[CategoriaTable.id].value,
        nombre = row[CategoriaTable.nombre]
    )


    override suspend fun listar(): List<Categoria> = newSuspendedTransaction {
        CategoriaTable
            .selectAll()
            .orderBy(CategoriaTable.nombre, SortOrder.ASC)
            .map { toDomain(it) }
    }


    override suspend fun obtenerNombrePorId(id: Int): String = newSuspendedTransaction {
        CategoriaTable
            .select { CategoriaTable.id eq id }
            .map { it[CategoriaTable.nombre] }
            .singleOrNull() ?: "Sin categoría"
    }
}
