package com.finanzasana.modules.empresas.infrastructure.persistence

import org.jetbrains.exposed.sql.Table

object EmpresaPrestamoTable : Table("empresas_prestamistas") {

    val id = integer("id").autoIncrement()
    val nombre = varchar("nombre", 100)
    val tasaInteres = double("tasa_interes")

    override val primaryKey = PrimaryKey(id)
}
