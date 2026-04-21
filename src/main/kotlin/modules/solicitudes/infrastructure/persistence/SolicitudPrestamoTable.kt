package com.finanzasana.modules.solicitudes.infrastructure.persistence

import com.finanzasana.modules.categorias.infrastructure.persistence.CategoriaTable
import com.finanzasana.modules.empresas.infrastructure.persistence.EmpresaPrestamoTable
import com.finanzasana.modules.usuarios.infrastructure.persistence.UsuarioTable
import org.jetbrains.exposed.sql.Table
import org.jetbrains.exposed.sql.javatime.datetime

object SolicitudPrestamoTable : Table("solicitudes_prestamo") {

    val id = integer("id").autoIncrement()
    val idUsuario = integer("id_usuario")
        .references(UsuarioTable.id)
    val idEmpresa = integer("id_empresa")
        .references(EmpresaPrestamoTable.id)
    val montoSolicitado = double("monto_solicitado")
    val meses = integer("meses")
    val motivo = varchar("motivo", 200)
    val tasaInteres = double("tasa_interes")
    val idCategoria = integer("id_categoria")
        .references(CategoriaTable.id)
    val imagenBase64 = text("imagen_base64").nullable()
    val latitud = double("latitud").nullable()
    val longitud = double("longitud").nullable()
    val estado = integer("estado")
    val fechaSolicitud = datetime("fecha_solicitud")

    override val primaryKey = PrimaryKey(id)
}
