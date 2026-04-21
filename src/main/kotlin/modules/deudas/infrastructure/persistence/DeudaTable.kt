package com.finanzasana.modules.deudas.infrastructure.persistence

import com.finanzasana.modules.categorias.infrastructure.persistence.CategoriaTable
import com.finanzasana.modules.usuarios.infrastructure.persistence.UsuarioTable
import org.jetbrains.exposed.sql.Table
import org.jetbrains.exposed.sql.javatime.date

object DeudaTable : Table("deudas") {

    val id = integer("id").autoIncrement()
    val concepto = varchar("concepto", 100)
    val montoOriginal = double("monto_original")
    val saldoActual = double("saldo_actual")
    val fechaVencimiento = date("fecha_vencimiento")
    val tasaInteres = double("tasa_interes")
    val idCategoria = integer("id_categoria")
        .references(CategoriaTable.id)
    val idUsuario = integer("id_usuario")
        .references(UsuarioTable.id)
    val imagenBase64 = text("imagen_base64").nullable()
    val latitud = double("latitud").nullable()
    val longitud = double("longitud").nullable()

    override val primaryKey = PrimaryKey(id)
}
