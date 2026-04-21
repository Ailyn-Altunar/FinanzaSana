package com.finanzasana.modules.estados.infrastructure.persistence

import org.jetbrains.exposed.sql.Table

object EstadoSolicitudTable : Table("estado_solicitud") {

    val id = integer("id").autoIncrement()
    val nombre = varchar("nombre", 50)

    override val primaryKey = PrimaryKey(id)
}
