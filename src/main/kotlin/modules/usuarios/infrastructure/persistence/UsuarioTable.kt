package com.finanzasana.modules.usuarios.infrastructure.persistence

import com.finanzasana.modules.catalogoRol.infrastructure.persistence.RolTable
import org.jetbrains.exposed.sql.Table

object UsuarioTable : Table("usuarios") {

    val id = integer("id_usuario").autoIncrement()
    val nombre = varchar("nombre", 100)
    val email = varchar("email", 200).uniqueIndex()
    val contrasena = varchar("contrasena", 255)
    val idRol = integer("id_rol").references(RolTable.idRol)

    override val primaryKey = PrimaryKey(id)
}

