package com.finanzasana.modules.usuarios.infrastructure.persistence

import org.jetbrains.exposed.sql.Table

object UsuarioTable : Table("usuarios") {

    val id = integer("id_usuario").autoIncrement()
    val nombre = varchar("nombre", 100)
    val email = varchar("email", 200).uniqueIndex()
    val contrasena = varchar("contrasena", 255)
    val edad = integer("edad").nullable()
    val idRol = integer("id_rol")

    override val primaryKey = PrimaryKey(id)
}