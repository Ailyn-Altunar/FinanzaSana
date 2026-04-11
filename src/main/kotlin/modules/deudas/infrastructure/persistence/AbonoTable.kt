package com.finanzasana.modules.deudas.infrastructure.persistence

import com.finanzasana.modules.usuarios.infrastructure.persistence.UsuarioTable
import org.jetbrains.exposed.sql.Table
import org.jetbrains.exposed.sql.javatime.datetime

object AbonoTable : Table("abonos") {

    val id = integer("id").autoIncrement()

    // Relación con la deuda
    val idDeuda = integer("id_deuda")
        .references(DeudaTable.id)

    // Relación con el usuario que hizo el abono
    val idUsuario = integer("id_usuario")
        .references(UsuarioTable.id)

    val monto = double("monto")

    // Fecha del abono usando timestamp nativo
    val fecha = datetime("fecha")

    override val primaryKey = PrimaryKey(id)
}
