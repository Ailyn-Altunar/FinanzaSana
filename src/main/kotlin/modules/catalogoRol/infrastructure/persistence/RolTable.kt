package com.finanzasana.modules.catalogoRol.infrastructure.persistence
import org.jetbrains.exposed.sql.Table


object RolTable : Table("rol") {
    val id = integer("id_rol").autoIncrement()
    val nombre = varchar("nombre", 100)

    override val primaryKey = PrimaryKey(id)
}


