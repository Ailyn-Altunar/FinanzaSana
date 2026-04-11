package com.finanzasana.modules.usuarios.infrastructure.persistence

import com.finanzasana.modules.usuarios.domain.model.Usuario
import com.finanzasana.modules.usuarios.domain.repository.UsuarioRepository
import com.finanzasana.modules.catalogoRol.infrastructure.persistence.RolTable
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.transactions.experimental.newSuspendedTransaction

class PostgresUsuarioRepository : UsuarioRepository {

    private fun UsuariosConRoles() = UsuarioTable.join(
        RolTable,
        joinType = JoinType.INNER,
        onColumn = UsuarioTable.idRol,
        otherColumn = RolTable.idRol
    )

    private fun toDomainConRol(row: ResultRow): Usuario = Usuario(
        id = row[UsuarioTable.id],
        nombre = row[UsuarioTable.nombre],
        email = row[UsuarioTable.email],
        contrasena = row[UsuarioTable.contrasena],
        idRol = row[UsuarioTable.idRol],
        telefono = row[UsuarioTable.telefono]
    )

    override suspend fun verPorId(id: Int): Usuario? = newSuspendedTransaction {
        UsuariosConRoles()
            .selectAll()
            .where { UsuarioTable.id eq id }
            .map { toDomainConRol(it) }
            .singleOrNull()
    }

    override suspend fun verPorEmail(email: String): Usuario? = newSuspendedTransaction {
        UsuariosConRoles()
            .selectAll()
            .where { UsuarioTable.email eq email }
            .map { toDomainConRol(it) }
            .singleOrNull()
    }

    override suspend fun guardar(usuario: Usuario): Usuario = newSuspendedTransaction {
        val nuevoId = UsuarioTable.insert {
            it[nombre] = usuario.nombre
            it[email] = usuario.email
            it[contrasena] = usuario.contrasena
            it[idRol] = usuario.idRol
            it[telefono] = usuario.telefono
        }[UsuarioTable.id]

        UsuariosConRoles()
            .select { UsuarioTable.id eq nuevoId }
            .map { toDomainConRol(it) }
            .singleOrNull()
            ?: throw IllegalStateException("El usuario se insertó con ID $nuevoId pero no se pudo recuperar")
    }

    override suspend fun actualizar(id: Int, usuario: Usuario): Usuario? = newSuspendedTransaction {
        val filasAfectadas = UsuarioTable.update({ UsuarioTable.id eq id }) {
            it[nombre] = usuario.nombre
            it[email] = usuario.email
            it[idRol] = usuario.idRol
            it[telefono] = usuario.telefono
        }
        if (filasAfectadas > 0) verPorId(id) else null
    }

    override suspend fun eliminar(id: Int): Boolean = newSuspendedTransaction {
        UsuarioTable.deleteWhere { UsuarioTable.id eq id } > 0
    }

    override suspend fun listar(): List<Usuario> = newSuspendedTransaction {
        UsuariosConRoles()
            .selectAll()
            .map { toDomainConRol(it) }
    }
}
